package Design.dbutils.Jform.GLY;

import Design.dbutils.Service.Orderservice;
import Design.dbutils.UDNI.PowerBank;
import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.UserDaoImp1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentalPanel3 extends JPanel {
    private JTextField idField;
    private JButton queryButton,Button,  deleteButton;
    private JTable table;

    public RentalPanel3(User user) {
        setLayout(new BorderLayout(0, 0));

        // 创建查询面板
        JPanel queryPanel = new JPanel();

        add(queryPanel, BorderLayout.NORTH);

        JLabel idLabel = new JLabel("编号：");
        JLabel welcomeLabel = new JLabel("欢迎管理员"+user.getName()+"进入本系统                                                                                                                 ");
        queryPanel.add(welcomeLabel);
        queryPanel.add(idLabel);

        idField = new JTextField();
        queryPanel.add(idField);
        idField.setColumns(10);

        queryButton = new JButton("查询");
        queryPanel.add(queryButton);

        // 创建操作面板
        JPanel operationPanel = new JPanel();
        add(operationPanel, BorderLayout.SOUTH);

        deleteButton = new JButton("删除");
        operationPanel.add(deleteButton);
        Button = new JButton("修改");
        operationPanel.add(Button);

        // 创建表格面板
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(tablePanel, BorderLayout.CENTER);
        tablePanel.setLayout(new BorderLayout(0, 0));

        // 创建表格
        String[] columnNames = {"编号", "姓名", "密码", "余额（RMB）"};
        UserDaoImp1 userDaoImp1 = new UserDaoImp1();
        ArrayList<PowerBank> powerList = userDaoImp1.findUser();
        Object[][] powerData = new Object[powerList.size()][4];
        for (int i = 0; i < powerList.size(); i++) {
            PowerBank power = powerList.get(i);
            powerData[i][0] = power.getId();
            powerData[i][1] = power.getPower();
            powerData[i][2] = power.getStatus();
            powerData[i][3] = power.getPrice();
        }
        DefaultTableModel model = new DefaultTableModel(powerData, columnNames);
        table = new JTable(model);
        table.getColumnModel().getColumn(0).setPreferredWidth(100);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // 添加按钮的点击事件
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查询按钮处理
                String text = idField.getText();
                int j = 0;
                int k = 0;
                for (int i = 0; i < table.getRowCount(); i++) {
                    Object cellValue = table.getValueAt(i, j);
                    if (cellValue != null && cellValue.toString().equals(text)) {
                        table.setRowSelectionInterval(i, i);
                        table.scrollRectToVisible(table.getCellRect(i, 0, true));
                        k++;
                    }
                }
                    if(k==0){
                        JOptionPane.showMessageDialog(null,"你输入的用户不存在，请重新输入！");
                    }



            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //删除处理操作
                String id = (String) model.getValueAt(table.getSelectedRow(), 0);
                if (id == null) {
                    JOptionPane.showMessageDialog(null, "请点击用户编号进行删除");
                } else {
                    //表格删除该条数据
                    int in = table.getSelectedRow();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(in);
                    table.repaint();
                    //数据库删除
                    userDaoImp1.delect(id);
                    JOptionPane.showMessageDialog(null,"删除成功！");
                }
            }
        });
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = (String) model.getValueAt(table.getSelectedRow(), 0);
                String name = (String) model.getValueAt(table.getSelectedRow(), 1);
                String password = (String) model.getValueAt(table.getSelectedRow(), 2);
                String price = (String) model.getValueAt(table.getSelectedRow(), 3);
                User user = new UserDaoImp1().findUserbyId(id);
                user.setName(name);
                user.setPassword(password);
                user.setBalance(Double.parseDouble(price));
                new UserDaoImp1().updataUser(user);
                JOptionPane.showMessageDialog(null,"修改成功！");
            }
        });
    }
}