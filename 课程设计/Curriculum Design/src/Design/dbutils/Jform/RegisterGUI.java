package Design.dbutils.Jform;

import Design.dbutils.Service.SuperuserService;
import Design.dbutils.Service.UserService;
import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.SuperuserDaolmp1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegisterGUI extends JFrame implements ActionListener {
    /*
    注册代码，包含先对应的数据库调用和对应的界面
     */
    private static final long serialVersionUID = 1L;
    private JLabel titleLabel, typeLabel, userLabel, passwordLabel, confirmPasswordLabel;
    private JTextField userField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton registerButton, resetButton, backButton;
    private JComboBox<String> typeComboBox;
    JFrame frame = new JFrame();

    public RegisterGUI() {
        frame.setTitle("移动电源租赁系统注册界面");
        frame.setSize(400, 350);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // 创建标题
        titleLabel = new JLabel("移动电源租赁系统注册");
        titleLabel.setBounds(70, 20, 260, 30);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 20));
        panel.add(titleLabel);

        // 创建注册类型标签和下拉框
        typeLabel = new JLabel("注册类型：");
        typeLabel.setBounds(60, 70, 80, 25);
        panel.add(typeLabel);
        typeComboBox = new JComboBox<String>();
        typeComboBox.addItem("管理员");
        typeComboBox.addItem("普通用户");
        typeComboBox.setBounds(150, 70, 170, 25);
        panel.add(typeComboBox);

        // 创建用户名标签和文本框
        userLabel = new JLabel("用户名：");
        userLabel.setBounds(60, 105, 60, 25);
        panel.add(userLabel);
        userField = new JTextField(20);
        userField.setBounds(120, 105, 200, 25);
        panel.add(userField);

        // 创建密码标签和密码框
        passwordLabel = new JLabel("密码：");
        passwordLabel.setBounds(60, 140, 60, 25);
        panel.add(passwordLabel);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(120, 140, 200, 25);
        panel.add(passwordField);

        // 创建确认密码标签和密码框
        confirmPasswordLabel = new JLabel("确认密码：");
        confirmPasswordLabel.setBounds(36, 175, 84, 25);
        panel.add(confirmPasswordLabel);
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setBounds(120, 175, 200, 25);
        panel.add(confirmPasswordField);

        // 创建注册按钮
        registerButton = new JButton("确定");
        registerButton.setBounds(70, 230, 100, 30);
        registerButton.addActionListener(this);
        panel.add(registerButton);

        // 创建重置按钮
        resetButton = new JButton("重置");
        resetButton.setBounds(200, 230, 100, 30);
        resetButton.addActionListener(this);
        panel.add(resetButton);

        // 创建返回按钮
        backButton = new JButton("返回");
        backButton.setBounds(145, 270, 100, 30);
        backButton.addActionListener(this);
        panel.add(backButton);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String userName = userField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String type = (String) typeComboBox.getSelectedItem();
            //比较两次密码是否相同
            if (password.equals(confirmPassword)) {
                // 插入用户数据到数据库中

                if (type.equals("管理员")) {
                     //插入普管理员数据进行注册
                    SuperuserService superuserService = new SuperuserService();
                    User user =   superuserService.singIn(userName,password);
                    JOptionPane.showMessageDialog(null, "注册成功！您的账户是："+user.getId());
                } else {
                    //插入普通用户数据进行注册
                    UserService userService = new UserService();
                    User user = userService.singIn(userName, password);

                    JOptionPane.showMessageDialog(null, "注册成功！您的账户是："+user.getId());
                }

            }else
                JOptionPane.showMessageDialog(null, "两次密码不一致，请重新输入！");
        } else if (e.getSource() == resetButton) {
            userField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");
        } else if (e.getSource() == backButton) {
            setVisible(false);
            frame.dispose();
            new LoginSystem().setVisible(true);

        }
    }

}
