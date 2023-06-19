package Design.dbutils.Jform.GLY;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminManagementPanel extends JPanel implements ActionListener {
    private JLabel id, name, email, username, password;
    private JTextField idText, nameText, emailText, usernameText;
    private JPasswordField passwordText;
    private JButton basicInfoUpdate, accountUpdate, confirm, cancel;
    private JPanel basicInfoPanel, accountPanel, buttonPanel;
    private boolean isBasicInfo = true; // 当前显示的面板

    public AdminManagementPanel() {
        setLayout(new BorderLayout());

        id = new JLabel("工号:");
        name = new JLabel("姓名:");
        email = new JLabel("邮箱:");
        username = new JLabel("用户名:");
        password = new JLabel("密码:");

        idText = new JTextField(20);
        nameText = new JTextField(20);
        emailText = new JTextField(20);
        usernameText = new JTextField(20);
        passwordText = new JPasswordField(20);

        basicInfoUpdate = new JButton("基本信息更新");
        accountUpdate = new JButton("账号管理");
        basicInfoUpdate.addActionListener(this);
        accountUpdate.addActionListener(this);

        confirm = new JButton("修改");
        cancel = new JButton("取消");
        confirm.addActionListener(this);
        cancel.addActionListener(this);

        basicInfoPanel = new JPanel(new GridLayout(3, 2));
        basicInfoPanel.add(id);
        basicInfoPanel.add(idText);
        basicInfoPanel.add(name);
        basicInfoPanel.add(nameText);
        basicInfoPanel.add(email);
        basicInfoPanel.add(emailText);

        accountPanel = new JPanel(new GridLayout(2, 2));
        accountPanel.add(username);
        accountPanel.add(usernameText);
        accountPanel.add(password);
        accountPanel.add(passwordText);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        buttonPanel.add(basicInfoUpdate);
        buttonPanel.add(accountUpdate);
        buttonPanel.add(confirm);
        buttonPanel.add(cancel);

        add(basicInfoPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == basicInfoUpdate) {
            if (!isBasicInfo) {
                // 如果当前显示的不是基本信息面板，则切换到基本信息面板
                remove(accountPanel);
                add(basicInfoPanel, BorderLayout.CENTER);
                isBasicInfo = true;
                validate();
                repaint();
            }
            usernameText.setEditable(false);
            passwordText.setEditable(false);
            confirm.setText("确认修改");
        } else if (e.getSource() == accountUpdate) {
            if (isBasicInfo) {
                // 如果当前显示的是基本信息面板，则切换到账号管理面板
                remove(basicInfoPanel);
                add(accountPanel, BorderLayout.CENTER);
                isBasicInfo = false;
                validate();
                repaint();
            }
            idText.setEditable(false);
            nameText.setEditable(false);
            emailText.setEditable(false);
            usernameText.setEditable(true);
            passwordText.setEditable(true);
            confirm.setText("确认修改");
        } else if (e.getSource() == confirm) {
            // 处理管理员信息修改操作
            if (isBasicInfo) {
                // 处理基本信息更新操作

                // 在此处获取管理员基本信息并更新输入框

            } else {
                // 处理账号管理操作

                // 在此处获取管理员修改的账号信息

            }
            JOptionPane.showMessageDialog(this, "修改成功！");
        } else if (e.getSource() == cancel) {
            // 关闭界面
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window != null) {
                window.dispose();
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new AdminManagementPanel());
        frame.pack();
        frame.setVisible(true);
    }
}