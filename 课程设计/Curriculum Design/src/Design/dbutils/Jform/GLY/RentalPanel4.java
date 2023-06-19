package Design.dbutils.Jform.GLY;

import Design.dbutils.Service.Orderservice;
import Design.dbutils.UDNI.PowerBank;
import Design.dbutils.UDNI.RentalRecord;
import Design.dbutils.UDNI.RentalRecord1;
import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.UserDaoImp1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentalPanel4 extends JPanel {
    private JTextField idField;
    private JButton queryButton;
    private JTable table;
    private DefaultTableModel rentalHistoryTableModel;

    public RentalPanel4(User user ) {
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
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                rentalHistoryTableModel.setRowCount(0);
                Orderservice orderservice = new Orderservice();
                User user = new UserDaoImp1().findUserbyId(id);
                if (user == null) {
                    JOptionPane.showMessageDialog(null, "该账号不存在！或已经注销");
                } else {
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
                }
            }

        });

        // 创建操作面板
        JPanel operationPanel = new JPanel();
        add(operationPanel, BorderLayout.SOUTH);

        JButton queryButton = new JButton("查询历史订单");
        operationPanel.add(queryButton);

        // 创建表格面板
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(tablePanel, BorderLayout.CENTER);
        tablePanel.setLayout(new BorderLayout(0, 0));
        // 创建历史订单列表和查询按钮
        String[] rentalColumns = {"订单编号", "租赁时长", "订单金额", "订单状态", "创建时间", "归还时间","使用用户id"};
        Orderservice orderservice = new Orderservice();
        ArrayList<RentalRecord1> rentalList = orderservice.getRentalRecordList1();
        Object[][] rentalData = new Object[rentalList.size()][7];
        for (int i = 0; i < rentalList.size(); i++) {
            RentalRecord1 rentalRecord = rentalList.get(i);
            rentalData[i][0] = rentalRecord.getId();
            rentalData[i][1] = rentalRecord.getDuration();
            rentalData[i][2] = rentalRecord.getAmount();
            rentalData[i][3] = rentalRecord.getStatus();
            rentalData[i][4] = rentalRecord.getCreateTime();
            rentalData[i][5] = rentalRecord.getReturnTime();
            rentalData[i][6] = rentalRecord.getUserId();
        }
        rentalHistoryTableModel = new DefaultTableModel(rentalData, rentalColumns);
        JTable rentalTable = new JTable(rentalHistoryTableModel);
        tablePanel.add(new JScrollPane(rentalTable), BorderLayout.CENTER);

        //历史订单监听器（已经实现）
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rentalHistoryTableModel.setRowCount(0);
                Orderservice orderservice = new Orderservice();
                ArrayList<RentalRecord1> rentalList = orderservice.getRentalRecordList1();
                for (int i = 0; i < rentalList.size(); i++) {
                    RentalRecord1 rentalRecord = rentalList.get(i);
                    rentalHistoryTableModel.addRow(new Object[]{
                            rentalRecord.getId(),
                            rentalRecord.getDuration(),
                            rentalRecord.getAmount(),
                            rentalRecord.getStatus(),
                            rentalRecord.getCreateTime(),
                            rentalRecord.getReturnTime(),rentalRecord.getUserId()

                    });
                }
                JOptionPane.showMessageDialog(null, "查询历史订单！");
            }
        });
    }
}