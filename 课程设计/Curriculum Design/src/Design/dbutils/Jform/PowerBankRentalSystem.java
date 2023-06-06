package Design.dbutils.Jform;

import Design.dbutils.JDBC.DBHelper;
import Design.dbutils.UDNI.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PowerBankRentalSystem extends JFrame implements ActionListener {

    private JLabel welcomeLabel = new JLabel("欢迎来到移动电源租赁系统！", JLabel.CENTER);
    private JLabel userLabel = new JLabel("欢迎xxx使用本租赁系统", JLabel.CENTER);
    private JLabel timeLabel = new JLabel("时间", JLabel.CENTER);
    private JMenuBar menuBar = new JMenuBar();
    private JMenu powerInfoMenu = new JMenu("电源信息");
    private JMenuItem powerInfoListMenuItem = new JMenuItem("电源信息列表");
    private JMenu rentalInfoMenu = new JMenu("租赁信息");
    private JMenuItem historyOrderMenuItem = new JMenuItem("个人历史订单信息查询");
    private JMenuItem returnOrderMenuItem = new JMenuItem("归还充电宝");
    private JMenu otherMenu = new JMenu("其他操作");
    private JMenuItem updateMenuItem = new JMenuItem("个人信息修改");
    private JMenuItem exitMenuItem = new JMenuItem("退出");

    private JTable powerTable;
    private JTable   historyOrderTable;
    private DefaultTableModel powerTableModel;
    public  DefaultTableModel  orderTableModel  ;
    public User user1;

    public static void main(String[] args) {
        new PowerBankRentalSystem(null);
    }


    public PowerBankRentalSystem(User user) {
        this.user1 = user;
        JFrame frame = new JFrame();
        frame.setTitle("移动电源租赁系统");
        frame.setSize(700, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 设置背景颜色和字体样式
        getContentPane().setBackground(Color.white);
        Font font1 = new Font("宋体", Font.BOLD, 24);
        welcomeLabel.setFont(font1);
        Font font2 = new Font("微软雅黑", Font.BOLD, 16);
        userLabel.setFont(font2);
        timeLabel.setFont(font2);

        // 添加菜单项到菜单上
        powerInfoMenu.add(powerInfoListMenuItem);
        rentalInfoMenu.add(historyOrderMenuItem);
        otherMenu.add(updateMenuItem);
        otherMenu.add(exitMenuItem);

        // 添加菜单到菜单栏上
        menuBar.add(powerInfoMenu);
        menuBar.add(rentalInfoMenu);
        menuBar.add(otherMenu);

        // 添加菜单栏到顶部
        frame.setJMenuBar(menuBar);

        // 创建电源信息列表
        String[] powerColumnNames = {"编号", "剩余电量", "状态", "租赁费用"};
        powerTableModel = new DefaultTableModel(null, powerColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        powerTable = new JTable(powerTableModel);
        // 从数据库加载电源信息
        loadDataFromDatabase();

        // 使用网格布局
        frame.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // 设置布局参数
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(10, 10, 10, 10);

        // 添加欢迎标签
        frame.add(welcomeLabel, constraints);

        // 添加用户标签
        frame.add(userLabel, constraints);

        // 添加时间标签
        frame.add(timeLabel, constraints);

        // 设置布局参数
        constraints.weighty = 1;
        constraints.gridy = 3;

        // 创建一个滚动面板，用于包含电源信息列表
        JScrollPane powerListScrollPane = new JScrollPane(powerTable);

        // 添加电源信息列表到界面
        frame.add(powerListScrollPane, constraints);

        // 添加按钮到界面
        JButton rentButton = new JButton("租赁");
        JButton returnButton = new JButton("归还");
        constraints.insets = new Insets(10, 10, 10, 0);
        constraints.weighty = 0;
        constraints.gridy = 4;
        frame.add(rentButton, constraints);
        constraints.weighty = 0;
        constraints.gridy = 5;
        frame.add(returnButton, constraints);

        // 设置菜单项的动作监听器
        powerInfoListMenuItem.addActionListener(this);
        historyOrderMenuItem.addActionListener(this);
        updateMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);

        // 设置租赁按钮的动作监听器

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的电源选中行信息
                int row = powerTable.getSelectedRow();
                //获取对应的数据
                if (row >= 0) {
                    String id = (String) powerTableModel.getValueAt(row, 0);
                    int battery = Integer.parseInt((String) powerTableModel.getValueAt(row, 1));
                    String status = (String) powerTableModel.getValueAt(row, 2);
                    // 判断电源是否可租
                    if (status.equals("租赁中")) {
                        JOptionPane.showMessageDialog(null, "该电源已被租赁。", "错误", JOptionPane.ERROR_MESSAGE);
                    } else if (battery < 50) {
                        JOptionPane.showMessageDialog(null, "电源电量低于50%，不可租赁。", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 弹出对话框，输入租赁时长，还不会，后续优化实时计费，获取时间差进行计费
                        String hours = JOptionPane.showInputDialog(null, "请输入租赁时长（单位：小时）：");
                        if (hours != null && !hours.trim().equals("")) {
                            try {

                                // 弹出对话框，显示租赁订单信息
                                String message = "编号：" + id + "是否确认租赁？";
                                int option = JOptionPane.showConfirmDialog(null, message, "确认租赁", JOptionPane.YES_NO_OPTION);
                                if (option == JOptionPane.YES_OPTION) {
                                    // 更新电源信息列表
                                    powerTableModel.setValueAt("租赁中", row, 2);
                                    JOptionPane.showMessageDialog(null, "租赁成功！", "提示", JOptionPane.INFORMATION_MESSAGE);

                                    // 添加订单信息到数据库


                                    // 刷新历史订单信息列表
                                    loadOrderDataFromDatabase(user);
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(null, "请输入正确的租赁时长。", "错误", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请先选择一个电源。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // 设置归还按钮的动作监听器
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的订单信息
                int row = historyOrderTable.getSelectedRow();
                if (row >= 0) {
                    String id = orderTableModel.getValueAt(row, 0).toString();
                    String status = orderTableModel.getValueAt(row, 3).toString();

                    if (status.equals("已完成")) {
                        JOptionPane.showMessageDialog(null, "该订单已完成，不可归还。", "错误", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 弹出对话框，确认归还操作
                        String message = "确认归还充电宝？";
                        int option = JOptionPane.showConfirmDialog(null, message, "确认归还", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            // 更新订单状态


                            // 弹出对话框，提示归还成功
                            JOptionPane.showMessageDialog(null, "归还成功！", "提示", JOptionPane.INFORMATION_MESSAGE);

                            // 刷新订单信息列表和电源信息列表
                            loadOrderDataFromDatabase(user);
                            loadDataFromDatabase();
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "请先选择一个订单。", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 在界面中显示当前时间，每秒更新一次
        updateTimeLabel();
        new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimeLabel();
            }
        }).start();

        frame.setVisible(true);
    }

    // 从数据库中加载电源信息的方法
    public void loadDataFromDatabase() {
        Connection conn = DBHelper.getConn();
        ResultSet rs = null;
        try {
            String sql = "select *from design.orders";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String id = rs.getString("id");
                String battery = rs.getString("batterty");
                String status = rs.getString("status");
                Object[] data = new Object[]{id, battery, status, "-"};
                powerTableModel.addRow(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 更新时间标签的方法
    private void updateTimeLabel() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String time = format.format(new Date());
        timeLabel.setText(time);
    }

    // 添加订单信息到数据库的方法
    private void addOrderToDatabase(String id, String hours, double cost) {
        Connection conn = DBHelper.getConn();
        String sql = "insert into design.orders(userid, id, batterty, status, coast, hour) VALUE (?,?,?,?,?,?)";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, 1); // 用户ID暂时为1
            statement.setString(2, id);
            statement.setDouble(3, Double.parseDouble(hours));
            statement.setDouble(4, cost);
            statement.setString(5, "已完成");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // 从数据库中加载历史订单信息的方法
    private void loadOrderDataFromDatabase(User user) {
        Connection conn = DBHelper.getConn();
        String sql = "select *from design.orders_copy1 where userid = ?";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, user.getId());
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String hours = rs.getDouble("hour") + "小时";
                double cost = rs.getDouble("coast");
                String status = rs.getString("status");
                String creatTime = rs.getString("creatTime");
                String endTime = rs.getString("endTime");
                Object[] data = {id, hours, cost + "元", status, creatTime, endTime};
                orderTableModel.addRow(data);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // 主界面菜单项的动作监听器
    @Override
    public void actionPerformed(ActionEvent e) {
        JMenuItem menuItem = (JMenuItem) e.getSource();
        if (menuItem == powerInfoListMenuItem) { // 打开电源信息列表界面
            showPowerInfoList();
        } else if (menuItem == historyOrderMenuItem) { // 打开个人历史订单信息查询界面
            showHistoryOrder();
        } else if (menuItem == returnOrderMenuItem) {
            // 归还充电宝
            JOptionPane.showMessageDialog(null, "请到个人历史订单信息查询界面进行操作。", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else if (menuItem == updateMenuItem) {
            // 修改个人信息
            JOptionPane.showMessageDialog(null, "该功能暂未实现。", "提示", JOptionPane.INFORMATION_MESSAGE);
        } else if (menuItem == exitMenuItem) {
            // 退出程序
            System.exit(0);
        }
    }


    // 打开电源信息列表界面的方法
    private void showPowerInfoList() {
        JFrame powerFrame = new JFrame("电源信息列表");
        powerFrame.setSize(500, 300);
        powerFrame.setLocationRelativeTo(null);
        powerFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 在这里添加电源信息列表界面的代码
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JScrollPane powerListScrollPane = new JScrollPane(powerTable);
        panel.add(powerListScrollPane, BorderLayout.CENTER);

        powerFrame.setContentPane(panel);

        powerFrame.setVisible(true);
    }

    // 打开个人历史订单信息查询界面的方法
    private void showHistoryOrder() {
        JFrame orderFrame = new JFrame("个人历史订单信息查询");
        orderFrame.setSize(800, 600);
        orderFrame.setLocationRelativeTo(null);
        orderFrame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // 创建历史订单信息列表
        String[] orderColumnNames = {"订单编号" ,"租赁时长", "订单金额", "订单状态","创建时间","归还时间"};
        orderTableModel = new DefaultTableModel(null, orderColumnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        historyOrderTable = new JTable(orderTableModel);

        // 从数据库加载历史订单信息
        loadOrderDataFromDatabase(user1);
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // 创建一个滚动面板，用于包含历史订单信息列表
        JScrollPane orderListScrollPane = new JScrollPane(historyOrderTable);
        panel.add(orderListScrollPane,BorderLayout.CENTER);
        // 添加历史订单信息列表到界面
        orderFrame.setContentPane(panel);

        orderFrame.setVisible(true);
    }
}