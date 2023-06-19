package Design.dbutils.Jform.GLY;

import Design.dbutils.Service.SuperuserService;
import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.SuperuserDaolmp1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class UserInfoGUI extends JFrame {

    private JLabel idLabel, nameLabel, emailLabel, passwordLabel;
    private JTextField idField, nameField, emailField;
    private JPasswordField passwordField;
    private JButton modifyButton, saveButton, changePasswordButton, resignButton;
    private JCheckBox showPasswordCheckBox;
    private  User user;
    public UserInfoGUI(User user1) {
        this.user = user1;
        setTitle("管理员信息");

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel(" 管理员信息修改 ");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        topPanel.add(titleLabel);

        JPanel basicInfoPanel = createBasicInfoPanel();
        JPanel accountPanel = createAccountPanel();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(basicInfoPanel);
        mainPanel.add(accountPanel);

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createBasicInfoPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 2));

        panel.setBorder(BorderFactory.createTitledBorder("基本信息"));

        idLabel = new JLabel("工号:");
        panel.add(idLabel);

        idField = new JTextField();
        idField.setText(user.getId());
        idField.setEditable(false);
        panel.add(idField);

        nameLabel = new JLabel("姓名:");

        panel.add(nameLabel);

        nameField = new JTextField();
        nameField.setText(user.getName());
        panel.add(nameField);

        emailLabel = new JLabel("邮箱:");
        panel.add(emailLabel);


        emailField = new JTextField();
        emailField.setText(user.getEmail());
        panel.add(emailField);

        modifyButton = new JButton("修改");
        modifyButton.setPreferredSize(new Dimension(80, 30));
        modifyButton.setBackground(new Color(59, 89, 182));
        modifyButton.setForeground(Color.WHITE);
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setEditable(true);
                emailField.setEditable(true);
                saveButton.setEnabled(true);
            }
        });
        panel.add(modifyButton);

        saveButton = new JButton("保存");
        saveButton.setPreferredSize(new Dimension(80, 30));
        saveButton.setBackground(new Color(46, 204, 113 ));
        saveButton.setForeground(Color.WHITE);
        saveButton.setEnabled(false);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user.setName(nameField.getName());
                user.setEmail(emailField.getText());
                new SuperuserDaolmp1().updataUser(user);
                JOptionPane.showMessageDialog(UserInfoGUI.this, "信息保存成功");
            }
        });
        panel.add(saveButton);

        return panel;
    }

    private JPanel createAccountPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createTitledBorder("账号管理"));

        passwordLabel = new JLabel("密码:");
        GridBagConstraints passwordLabelConstraints = new GridBagConstraints();
        passwordLabelConstraints.gridx = 0;
        passwordLabelConstraints.gridy = 0;
        passwordLabelConstraints.insets = new Insets(10, 0, 0, 10);
        panel.add(passwordLabel, passwordLabelConstraints);

        passwordField = new JPasswordField();
        passwordField.setText(user.getPassword());
        GridBagConstraints passwordFieldConstraints = new GridBagConstraints();
        passwordFieldConstraints.gridx = 1;
        passwordFieldConstraints.gridy = 0;
        passwordFieldConstraints.insets = new Insets(10, 0, 0, 10);
        passwordField.setPreferredSize(new Dimension(180,30)); // 对密码文本框进行宽度设定
        panel.add(passwordField, passwordFieldConstraints);

        showPasswordCheckBox = new JCheckBox("显示密码");
        showPasswordCheckBox.setBackground(new Color(238, 238, 238));
        GridBagConstraints showPasswordCheckBoxConstraints = new GridBagConstraints();
        showPasswordCheckBoxConstraints.gridx = 2;
        showPasswordCheckBoxConstraints.gridy = 0;
        showPasswordCheckBoxConstraints.insets = new Insets(10, 0, 0, 10);
        showPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    //删除密码中的覆盖，使其可见
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar('*');
                }
            }
        });
        panel.add(showPasswordCheckBox, showPasswordCheckBoxConstraints);

        changePasswordButton = new JButton("修改密码");
        changePasswordButton.setPreferredSize(new Dimension(200, 40)); // 对“修改密码”按钮进行宽度设定
        changePasswordButton.setBackground(new Color(59, 89, 182));
        changePasswordButton.setForeground(Color.WHITE);
        GridBagConstraints changePasswordButtonConstraints = new GridBagConstraints();
        changePasswordButtonConstraints.gridx = 0;
        changePasswordButtonConstraints.gridy = 1;
        changePasswordButtonConstraints.insets = new Insets(10, 0, 0, 10);
        panel.add(changePasswordButton, changePasswordButtonConstraints);
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] p = passwordField.getPassword();
                String password = String.valueOf(p);
                user.setPassword(password);
                new SuperuserDaolmp1().updataUser(user);
                JOptionPane.showMessageDialog(null,"密码修改成功！");
            }
        });

        resignButton = new JButton("离职注销");
        resignButton.setPreferredSize(new Dimension(200, 40)); // 对“离职注销”按钮进行宽度设定
        resignButton.setBackground(new Color(231, 76, 60));
        resignButton.setForeground(Color.WHITE);
        GridBagConstraints resignButtonConstraints = new GridBagConstraints();
        resignButtonConstraints.gridx = 1;
        resignButtonConstraints.gridy = 1;
        resignButtonConstraints.insets = new Insets(10, 0, 0, 10);
        panel.add(resignButton, resignButtonConstraints);
        resignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = JOptionPane.showConfirmDialog(null, "真的确定离职吗？", "提示", JOptionPane.YES_NO_OPTION);
                if(i == JOptionPane.YES_NO_OPTION){
                    new SuperuserDaolmp1().delect(user.getId());
                    JOptionPane.showMessageDialog(null,"山海有情，江湖再见，期待未来某一天再次相遇！");
                    dispose();
                }

            }
        });

        return panel;
    }
}
