package Design.dbutils.Jform;

import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.UserDaoImp1;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInfoPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JTextField usernameField, passwordField, confirmPasswordField;
    private JTextField accountField, rechargePasswordField, rechargeAmountField;
    private User user;
    public UserInfoPanel(User user1) {
        this.user = user1;
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        // 创建用户信息修改界面
        JPanel updateUserPanel = createUserInfoUpdatePanel();

        // 创建充值界面
        JPanel rechargePanel = createRechargePanel();

        // 创建卡片布局
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(updateUserPanel, "updateUser");
        cardPanel.add(rechargePanel, "recharge");
        cardLayout.show(cardPanel, "updateUser");

        // 创建头部面板
        JPanel topPanel = createTopPanel();

        // 将所有组件添加到主界面
        add(topPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createUserInfoUpdatePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 10, 5, 0);
        JLabel usernameLabel = new JLabel("账户名:");
        panel.add(usernameLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        usernameField = new JTextField(20);
        panel.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        JLabel passwordLabel = new JLabel("密码:");
        panel.add(passwordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        passwordField = new JTextField(20);
        panel.add(passwordField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        JLabel confirmPasswordLabel = new JLabel("确认密码:");
        panel.add(confirmPasswordLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        confirmPasswordField = new JTextField(20);
        panel.add(confirmPasswordField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        JButton okButton = new JButton("确定");
        JButton Button = new JButton("重置");
        Button.setBackground(Color.WHITE);
        Button.setPreferredSize(new Dimension(100,30));
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.setText("");
                confirmPasswordField.setText("");
                usernameField.setText("");

            }
        });

        okButton.setPreferredSize(new Dimension(100, 30));
        okButton.setBackground(Color.WHITE);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = passwordField.getText();
                String confirmPassword = confirmPasswordField.getText();

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请输入完整的信息！", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "两次输入的密码不相同，请重新输入！", "", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                    confirmPasswordField.setText("");
                    return;
                }
                //调取数据库进行操作
                if(password.equals(confirmPassword)) {
                    UserDaoImp1 userDaoImp1 = new UserDaoImp1();
                    user.setName(username);
                    user.setPassword(password);
                    userDaoImp1.updataUser(user);
                   JOptionPane.showMessageDialog(null,"用户名和密码修改成功！");
                }
            }
        });
        buttonPanel.add(okButton, BorderLayout.CENTER);
        buttonPanel.add(Button, BorderLayout.SOUTH);
        panel.add(buttonPanel, c);

        return panel;
    }

    private JPanel createRechargePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 10, 5, 0);
        JLabel accountLabel = new JLabel("账号:");
        panel.add(accountLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        accountField = new JTextField(20);
        panel.add(accountField, c);

        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        JLabel rechargePasswordLabel = new JLabel("密码:");
        panel.add(rechargePasswordLabel, c);

        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        rechargePasswordField = new JTextField(20);
        panel.add(rechargePasswordField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0;
        JLabel rechargeAmountLabel = new JLabel("充值金额:");
        panel.add(rechargeAmountLabel, c);

        c.gridx = 1;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        rechargeAmountField = new JTextField(20);
        panel.add(rechargeAmountField, c);

        c.gridx = 0;
        c.gridy = 3;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        JButton okButton = new JButton("确定");
        JButton Button = new JButton("重置");
        Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passwordField.setText("");
                confirmPasswordField.setText("");
                usernameField.setText("");

            }
        });
        Button.setPreferredSize(new Dimension(100, 30));
        Button.setBackground(Color.WHITE);
        okButton.setPreferredSize(new Dimension(100, 30));
        okButton.setBackground(Color.WHITE);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String account = accountField.getText();
                String rechargePassword = rechargePasswordField.getText();
                String rechargeAmount = rechargeAmountField.getText();

                if (account.isEmpty() || rechargePassword.isEmpty() || rechargeAmount.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "请输入完整的信息！", "", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //调取数据库来进行修改
                if(account.equals(user.getId())){
                    if(rechargePassword.equals(user.getPassword())){
                        double money = Double.parseDouble(rechargeAmount);
                        double balance = user.getBalance();
                        user.setBalance(money+balance);
                        new UserDaoImp1().updataUser(user);
                    }
                }
                accountField.setText("");
                rechargePasswordField.setText("");
                rechargeAmountField.setText("");
                JOptionPane.showMessageDialog(null,"充值成功！");
            }
        });
        buttonPanel.add(okButton, BorderLayout.CENTER);
        buttonPanel.add(Button, BorderLayout.SOUTH);
        panel.add(buttonPanel, c);

        return panel;
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 15, 10, 15));

        JButton updateUserButton = new JButton("修改个人信息");
        updateUserButton.setPreferredSize(new Dimension(120, 40));
        updateUserButton.setBackground(new Color(56, 143, 225));
        updateUserButton.setForeground(Color.WHITE);
        updateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "updateUser");
            }
        });
        panel.add(updateUserButton, BorderLayout.WEST);

        JButton rechargeButton = new JButton("充值");
        rechargeButton.setPreferredSize(new Dimension(120, 40));
        rechargeButton.setBackground(new Color(56, 143, 225));
        rechargeButton.setForeground(Color.WHITE);
        rechargeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {   cardLayout.show(cardPanel, "recharge");
            }
        });
        panel.add(rechargeButton, BorderLayout.EAST);

        JLabel titleLabel = new JLabel("用户中心", SwingConstants.CENTER);
        titleLabel.setFont(titleLabel.getFont().deriveFont(24.0f));
        titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));
        panel.add(titleLabel, BorderLayout.CENTER);

        return panel;
    }


}