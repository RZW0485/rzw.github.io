package Design.dbutils.Jform.GLY;

import Design.dbutils.Jform.LoginSystem;
import Design.dbutils.UDNI.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private JMenuBar menuBar;
    private JMenu homeMenu, personalMenu, rentalMenu, customerMenu, orderMenu,Menu;
    private JMenuItem homeItem, personalItem, addRentalItem, modifyRentalItem, deleteRentalItem,
            customerItem, orderItem, maintenanceItem,Item;
    private JTabbedPane tabbedPane;

    public MainFrame(User user) {
        // 添加欢迎标签
      JLabel  welcomeLabel = new JLabel();
        welcomeLabel.setFont(new Font("宋体", Font.BOLD, 16));
        welcomeLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(welcomeLabel, BorderLayout.NORTH);

        setTitle("移动电源租赁系统");
        setBounds(100, 100, 800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // 创建菜单栏
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // 创建菜单
        homeMenu = new JMenu("租赁中充电宝");
        personalMenu = new JMenu("个人信息");
        rentalMenu = new JMenu("充电宝投放管理");
        customerMenu = new JMenu("租界人信息");
        orderMenu = new JMenu("电宝租赁订单查询");
        Menu = new JMenu("退出");
        // 添加菜单到菜单栏
        menuBar.add(homeMenu);
        menuBar.add(personalMenu);
        menuBar.add(rentalMenu);
        menuBar.add(customerMenu);
        menuBar.add(orderMenu);
        menuBar.add(Menu);


        // 创建菜单项
        Item = new JMenuItem("退出");
        Item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "是否确定退出？", "退出", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    new LoginSystem().setVisible(true);
                    dispose();
                }
            }
        });
        homeItem = new JMenuItem("租赁中充电宝");
        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(0);
            }
        });
        personalItem = new JMenuItem("个人信息");
        personalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserInfoGUI(user);
            }
        });
        addRentalItem = new JMenuItem("新增充电宝");
        addRentalItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(1);
            }
        });
        modifyRentalItem = new JMenuItem("修改充电宝");
        deleteRentalItem = new JMenuItem("删除充电宝");
        customerItem = new JMenuItem("用户信息");
        customerItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(2);
            }
        });
        orderItem = new JMenuItem("电宝租赁订单查询");
        orderItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(3);
            }
        });
        maintenanceItem = new JMenuItem("电宝维护管理");

        // 添加菜单项到菜单
        homeMenu.add(homeItem);
        personalMenu.add(personalItem);
        rentalMenu.add(addRentalItem);
        rentalMenu.add(modifyRentalItem);
        rentalMenu.add(deleteRentalItem);
        customerMenu.add(customerItem);
        orderMenu.add(orderItem);
        Menu.add(Item);


        // 创建选项卡
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

        // 添加选项卡
        tabbedPane.addTab("租赁中充电宝", new Rental2Panel(user));
        tabbedPane.addTab("充电宝投放管理", new RentalPanel(user));
        tabbedPane.addTab("用户信息查询", new RentalPanel3(user));
        tabbedPane.addTab("电宝租赁订单查询", new RentalPanel4(user));

        // 添加菜单项的点击事件
        homeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedPane.setSelectedIndex(0);
            }
        });
        setVisible(true);
    }
}