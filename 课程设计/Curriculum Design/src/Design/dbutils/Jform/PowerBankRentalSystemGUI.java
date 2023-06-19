package Design.dbutils.Jform;

import Design.dbutils.Service.Orderservice;
import Design.dbutils.UDNI.PowerBank;
import Design.dbutils.UDNI.RentalRecord;
import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.UserDaoImp1;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class PowerBankRentalSystemGUI extends JFrame {
    // 历史订单列表
    private DefaultTableModel rentalHistoryTableModel;
    // 个人订单列表
    private DefaultTableModel personalRentalTableModel;
    private String uid;
    private Timer timer1;
    private  String formattedHours ;
    private double   money1;
    private double Balance;


    // 构造函数
    public PowerBankRentalSystemGUI(User user) {
       Balance= user.getBalance() ;
        // 设置窗口标题和大小
        setTitle("移动电源租赁系统");
        setSize(800, 700);

        // 创建菜单
        JMenuBar menuBar = new JMenuBar();
        JMenu powerInfo = new JMenu("电源信息");
        JMenu rentalInfo = new JMenu("租赁信息");
        JMenu otherOps = new JMenu("其他操作");
        menuBar.add(powerInfo);
        menuBar.add(rentalInfo);
        menuBar.add(otherOps);
        setJMenuBar(menuBar);

        // 创建标签和时间显示
        JPanel labelPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("欢迎" + user.getName() + "使用本租赁系统！您的余额为" + user.getBalance() + "元");
        welcomeLabel.setHorizontalTextPosition(JLabel.CENTER);
        welcomeLabel.setVerticalTextPosition(JLabel.CENTER);
        labelPanel.add(welcomeLabel, BorderLayout.CENTER);
        //调用实时时间方法
        JPanel timePanel = new JPanel();
        JLabel timeLabel = new JLabel(getCurrentTime());
        timePanel.add(timeLabel);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLabel.setText(getCurrentTime());
            }
        });
        timer.start();

        // 创建电源信息列表
        String[] powerColumns = {"编号", "剩余电量", "状态", "单价"};
        Orderservice orderservice = new Orderservice();
        //数据库中获取电源信息
        ArrayList<PowerBank> powerList = orderservice.getPowerBankList();
        Object[][] powerData = new Object[powerList.size()][4];
        for (int i = 0; i < powerList.size(); i++) {
            PowerBank power = powerList.get(i);
            powerData[i][0] = power.getId();
            powerData[i][1] = power.getPower();
            powerData[i][2] = power.getStatus();
            powerData[i][3] = power.getPrice();
        }
        DefaultTableModel powerTableModel = new DefaultTableModel(powerData, powerColumns);
        JTable powerTable = new JTable(powerTableModel);

        // 创建电源信息列表下面的按钮和个人订单列表
        personalRentalTableModel = new DefaultTableModel();
        personalRentalTableModel.addColumn("当前用户操作电源编号");
        personalRentalTableModel.addColumn("状态");
        personalRentalTableModel.addColumn("实时费用(元)");

        JPanel powerButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton rentButton = new JButton("租赁");
        JButton returnButton = new JButton("归还");
        powerButtonPanel.add(rentButton);
        powerButtonPanel.add(returnButton);
        JScrollPane jScrollPane = new JScrollPane(new JTable(personalRentalTableModel));
        powerButtonPanel.add(jScrollPane);

        // 创建历史订单列表和查询按钮
        String[] rentalColumns = {"订单编号", "租赁时长", "订单金额", "订单状态", "创建时间", "归还时间"};
        ArrayList<RentalRecord> rentalList = orderservice.getRentalRecordList(user);
        Object[][] rentalData = new Object[rentalList.size()][6];
        for (int i = 0; i < rentalList.size(); i++) {
            RentalRecord rentalRecord = rentalList.get(i);
            rentalData[i][0] = rentalRecord.getId();
            rentalData[i][1] = rentalRecord.getDuration();
            rentalData[i][2] = rentalRecord.getAmount();
            rentalData[i][3] = rentalRecord.getStatus();
            rentalData[i][4] = rentalRecord.getCreateTime();
            rentalData[i][5] = rentalRecord.getReturnTime();
        }
        rentalHistoryTableModel = new DefaultTableModel(rentalData, rentalColumns);
        JTable rentalTable = new JTable(rentalHistoryTableModel);

        JPanel rentalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton queryButton = new JButton("查询历史订单");
        rentalButtonPanel.add(queryButton);

        // 创建面板，并添加组件
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(labelPanel, BorderLayout.NORTH);
        mainPanel.add(timePanel, BorderLayout.SOUTH);

        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel powerPanel = new JPanel(new BorderLayout());
        powerPanel.add(new JScrollPane(powerTable), BorderLayout.CENTER);
        powerPanel.add(powerButtonPanel, BorderLayout.SOUTH);

        JPanel rentalPanel = new JPanel(new BorderLayout());
        rentalPanel.add(new JScrollPane(rentalTable), BorderLayout.CENTER);
        rentalPanel.add(rentalButtonPanel, BorderLayout.SOUTH);

        tabbedPane.add("电源信息", powerPanel);
        tabbedPane.add("租赁信息", rentalPanel);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // 添加电源事件监听器，后续可优化实时计费，还不会,目前是选择计费(已实现)
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Balance >= 10) {
                    int row = powerTable.getSelectedRow();
                    if (row >= 0 && powerTableModel.getValueAt(row, 2).equals("可租赁")) {

                        // 弹出对话框，显示租赁订单信息
                        int row1 = powerTable.getSelectedRow();
                        String id = (String) powerTableModel.getValueAt(row1, 0);
                        //形成订单编号
                        uid = getCurrentTime1() + (String) powerTableModel.getValueAt(row1, 0);
                        String message = "电源编号：" + id + "是否确认租赁？";
                        int option = JOptionPane.showConfirmDialog(null, message, "确认租赁", JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            // 更新电源信息列表
                            Orderservice orderservice = new Orderservice();
                            String powerId = (String) powerTableModel.getValueAt(row, 0);
                            String amount = (String) powerTableModel.getValueAt(row, 1);
                            orderservice.updatePowerStatus(powerId, "租赁中");
                            powerTable.setValueAt("租赁中", row, 2);
                            // 记录开始时间
                            Instant start = Instant.now();
                            personalRentalTableModel.addRow(new Object[]{powerId, "租赁中", 0.0});

                            //后期优化实时计费区域、

                            timer1 = new Timer(1000, new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    Instant end = Instant.now();
                                    Duration duration = Duration.between(start, end); // 计算时间差
                                    double hours = duration.toNanos() / (60.0 * 60 * 1e9); // 转换为小时

                                    DecimalFormat df = new DecimalFormat("#0.00"); // 保留两位小数
                                    formattedHours = df.format(hours); // 格式化小数
                                    double forHour = Double.parseDouble(formattedHours);
                                    Object price = powerTable.getValueAt(row, 3);
                                    double price1 = Double.parseDouble((String) price);
                                    double money = price1 * forHour;
                                    money1 = Double.parseDouble(df.format(money));
                                    for (int i = 0; i < personalRentalTableModel.getRowCount(); i++) {
                                        if (personalRentalTableModel.getValueAt(i, 0).equals(powerId)) {
                                            personalRentalTableModel.setValueAt(money1, i, 2);
                                            break;
                                        }
                                    }

                                }
                            });
                            timer1.start();


                            JOptionPane.showMessageDialog(null, "租赁成功！开始实时计费", "提示", JOptionPane.INFORMATION_MESSAGE);

                            // 添加订单信息到数据库
                            orderservice.createRentalRecord(new RentalRecord(uid, powerId, getCurrentTime(), null, "0小时", "0.0", "租赁中"), user);

                        }
                    } else if (row >= 0 && powerTableModel.getValueAt(row, 2).equals("租赁中")) {
                        JOptionPane.showMessageDialog(null, "该电源已租赁！");
                    } else {
                        JOptionPane.showMessageDialog(null, "请选择可租赁的电源！");
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"抱歉，你的余额已经低于10元的保证金，请前往其他操作-个人信息处进行充值！");
                }
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = powerTable.getSelectedRow();
                if (row >= 0 && powerTableModel.getValueAt(row, 2).equals("租赁中")) {
                    //结束计时,计算使用的费用
                    timer1.stop();


                    // 更新电源信息列表和个人订单列表
                    String powerId = (String) powerTableModel.getValueAt(row, 0);
                    Orderservice orderservice = new Orderservice();
                    orderservice.updatePowerStatus(powerId, "可租赁");
                    for (int i = 0; i < personalRentalTableModel.getRowCount(); i++) {
                        if (personalRentalTableModel.getValueAt(i, 0).equals(powerId)) {

                            personalRentalTableModel.removeRow(i);
                            break;
                        }
                    }
                    powerTable.setValueAt("可租赁", row, 2);
                    // 在数据库中更新租赁记录
                    orderservice.updateRentalRecord(uid, getCurrentTime(),money1,formattedHours);
                    JOptionPane.showMessageDialog(null, "归还成功！");
                    timer1.stop();
                   double balance;
                    balance = Balance - money1;
                    user.setBalance(balance);
                    new UserDaoImp1().updataUser(user);
                } else {
                    JOptionPane.showMessageDialog(null, "请选择租赁中的电源！");
                }
                uid = "";
            }
        });
        //历史订单监听器（已经实现）
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentalHistoryTableModel.setRowCount(0);
                ArrayList<RentalRecord> rentalList = orderservice.getRentalRecordList(user);
                for (int i = 0; i < rentalList.size(); i++) {
                    RentalRecord rentalRecord = rentalList.get(i);
                    rentalHistoryTableModel.addRow(new Object[]{
                            rentalRecord.getId(),
                            rentalRecord.getDuration(),
                            rentalRecord.getAmount(),
                            rentalRecord.getStatus(),
                            rentalRecord.getCreateTime(),
                            rentalRecord.getReturnTime()
                    });
                }
                JOptionPane.showMessageDialog(null, "查询历史订单！");
            }
        });

        // 添加个人信息修改和退出选项
        JMenuItem personalInfoMenuItem = new JMenuItem("个人信息修改");
        personalInfoMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserInfoPanel panel = new UserInfoPanel(user);
                JFrame frame = new JFrame();
                frame.setSize(600, 400);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(panel);
                frame.setVisible(true);
            }
        });
        JMenuItem MenuItem = new JMenuItem("注销");
        JMenuItem exitMenuItem = new JMenuItem("退出");
        //对退出进行事件处理，返回主界面
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "是否确定退出？", "退出", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    new LoginSystem().setVisible(true);
                    dispose();
                }
            }
        });

        otherOps.add(personalInfoMenuItem);
        otherOps.add(MenuItem);
        otherOps.add(exitMenuItem);
        MenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "是否确定注销？","警告",JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    new UserDaoImp1().delect(user.getId());
                    JOptionPane.showMessageDialog(null,"注销成功，期待下次相遇！");
                    new LoginSystem().setVisible(true);
                    dispose();
                }
            }
        });

        // 将面板添加到窗口中
        add(mainPanel);

        // 设置窗口显示在屏幕中央
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // 获取当前时间
    private String getCurrentTime() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date());
    }

    // 获取当前时间用于生成订单号
    private String getCurrentTime1() {
        return new java.text.SimpleDateFormat("yyyyMMddssSS").format(new java.util.Date());
    }

}