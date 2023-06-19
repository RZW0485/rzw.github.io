package Design.dbutils.Jform.GLY;

import Design.dbutils.Service.Orderservice;
import Design.dbutils.UDNI.PowerBank;
import Design.dbutils.UDNI.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RentalPanel extends JPanel {
    private JTextField idField;
    private JButton queryButton, addButton, modifyButton, deleteButton;
    private JTable table;

    public RentalPanel(User user) {
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

        addButton = new JButton("新增");
        operationPanel.add(addButton);

        modifyButton = new JButton("修改");
        operationPanel.add(modifyButton);

        deleteButton = new JButton("删除");
        operationPanel.add(deleteButton);
        JButton button = new JButton("保存");
        operationPanel.add(button);

        // 创建表格面板
        JPanel tablePanel = new JPanel();
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(tablePanel, BorderLayout.CENTER);
        tablePanel.setLayout(new BorderLayout(0, 0));

        // 创建表格
        String[] columnNames = {"编号", "电量", "状态", "单价（元 / 小时）"};
        Orderservice orderservice = new Orderservice();
        ArrayList<PowerBank> powerList = orderservice.getPowerBankList();
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
                int i = Integer.parseInt(text)  ;
                if (i < table.getRowCount() && i >= 0) {
                    table.setRowSelectionInterval(i, i);
                    table.scrollRectToVisible(table.getCellRect(i, 0, true));
                } else {
                    JOptionPane.showMessageDialog(null, "该充电宝不存在，请重新输入！");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 新增按钮处理
                model.addRow(new Object[model.getColumnCount()]);
            }
        });

        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //修改按钮
                String id = (String) model.getValueAt(table.getSelectedRow(), 0);
                String power = (String) model.getValueAt(table.getSelectedRow(), 1);
                String status = (String) model.getValueAt(table.getSelectedRow(), 2);
                String price = (String) model.getValueAt(table.getSelectedRow(), 3);
                orderservice.updatepower(id,power,status,price);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //删除处理操作
                String id = (String) model.getValueAt(table.getSelectedRow(), 0);
                if(id == null){
                    JOptionPane.showMessageDialog(null,"请点击电源编号进行删除");
                }else {
                    //表格删除该条数据
                    int in = table.getSelectedRow();
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.removeRow(in);
                    table.repaint();
                    //数据库删除
                    orderservice.delete(id);
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //点击保存按钮
                if(table.getSelectedRow()==0){
                    JOptionPane.showMessageDialog(null,"请先选择创建在保存！");
                }
                String id = (String) model.getValueAt(table.getSelectedRow(), 0);
                String power = (String) model.getValueAt(table.getSelectedRow(), 1);
                String status = (String) model.getValueAt(table.getSelectedRow(), 2);
                String price = (String) model.getValueAt(table.getSelectedRow(), 3);
                orderservice.updatepower_back(id,power,status,price);
            }
        });
    }
}