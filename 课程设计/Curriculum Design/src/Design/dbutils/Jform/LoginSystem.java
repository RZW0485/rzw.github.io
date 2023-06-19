package Design.dbutils.Jform;

import Design.dbutils.Jform.GLY.MainFrame;
import Design.dbutils.UDNI.User;
import Design.dbutils.UserDao.SuperuserDaolmp1;
import Design.dbutils.UserDao.UserDaoImp1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/*
主界面，包含登录、注册、验证码功能
 */
public class LoginSystem extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField codeField;
    private JLabel codeLabel;
    private JButton loginButton;
    private JButton registerButton;
    private JCheckBox adminCheckBox;
    private JCheckBox userCheckBox;
    private JLabel powerByLabel;
    //获取的随机验证码
    private String randomCode = "";

    public LoginSystem() {
        setTitle("移动电源租赁系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setBounds(100, 100, 400, 350);

        contentPane = new JPanel();
        contentPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel titleLabel = new JLabel("移动电源租赁系统");
        titleLabel.setFont(new Font("宋体", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBounds(60, 20, 280, 40);
        contentPane.add(titleLabel);

        JLabel userLabel = new JLabel("账  号:");
        userLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        userLabel.setBounds(40, 80, 60, 20);
        contentPane.add(userLabel);

        userField = new JTextField();
        userField.setBounds(100, 80, 200, 20);
        contentPane.add(userField);

        JLabel passwordLabel = new JLabel("密  码:");
        passwordLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        passwordLabel.setBounds(40, 120, 60, 20);
        contentPane.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 120, 200, 20);
        contentPane.add(passwordField);

        JLabel codeTextLabel = new JLabel("验证码:");
        codeTextLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        codeTextLabel.setBounds(40, 160, 60, 20);
        contentPane.add(codeTextLabel);

        codeField = new JTextField();
        codeField.setBounds(100, 160, 80, 20);
        contentPane.add(codeField);

        codeLabel = new JLabel();
        codeLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        codeLabel.setPreferredSize(new Dimension(60, 20));
        codeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        codeLabel.setVerticalAlignment(SwingConstants.CENTER);
        codeLabel.setBounds(200, 160, 60, 20);
        generateRandomCode();
        codeLabel.setText(randomCode);
        contentPane.add(codeLabel);

        JButton refreshCodeButton = new JButton("刷新验证码");
        refreshCodeButton.setFont(new Font("宋体", Font.PLAIN, 12));
        refreshCodeButton.setBounds(270, 160, 100, 20);
        refreshCodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomCode();
                codeLabel.setText(randomCode);
            }
        });
        contentPane.add(refreshCodeButton);

        adminCheckBox = new JCheckBox("管理员登录");
        adminCheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
        adminCheckBox.setBounds(40, 200, 100, 20);
        contentPane.add(adminCheckBox);

        userCheckBox = new JCheckBox("用户登录");
        userCheckBox.setFont(new Font("宋体", Font.PLAIN, 12));
        userCheckBox.setBounds(150, 200, 100, 20);
        contentPane.add(userCheckBox);

        loginButton = new JButton("登录");
        loginButton.setFont(new Font("宋体", Font.PLAIN, 14));
        loginButton.setBounds(100, 240, 90, 30);
        //对登录按钮进行事件处理
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取数文本数据
                String userId = userField.getText();
                String password = new String(passwordField.getPassword());
                String code = codeField.getText();
                boolean isAdmin = adminCheckBox.isSelected();
                boolean isUser = userCheckBox.isSelected();
                // 检验用户名、密码和验证码是否正确，根据isAdmin和isUser的值判断是管理员登录还是用户登录
                //获取数据库连接得到user数据并进行比较
                // 然后跳转界到不同的界面
                //获取数据库连接得到user数据并进行包装
                if (randomCode.equals(code) ){
                    if (isUser) {
                        UserDaoImp1 udi = new UserDaoImp1();
                        User user = udi.findUserbyId(userId);
                        if (user != null && password.equals(user.getPassword())) {
                            //弹出普通用户界面
                            new PowerBankRentalSystemGUI(user);
                            dispose();

                        } else {
                            JOptionPane.showMessageDialog(null, "账号或密码错误，请重新输入！");
                        }
                    } else if (isAdmin) {
                        SuperuserDaolmp1 sudi = new SuperuserDaolmp1();
                        User user = sudi.findUserbyId(userId);
                        if (user != null && password.equals(user.getPassword())) {
                            //弹出管理员用户界面
                           new MainFrame(user);
                           dispose();

                        } else {
                            JOptionPane.showMessageDialog(null, "账号或密码错误，请重新输入！");
                        }
                    }
                }else if (userId.equals("")&&password.equals("")){
                    JOptionPane.showMessageDialog(null, "请输入账号和密码！");
                }
                else if((code.equals("")) &&(!userId.equals("")&&!password.equals("")))
                {
                    JOptionPane.showMessageDialog(null, "请输入验证码！");
                }
                else {
                    JOptionPane.showMessageDialog(null, "验证码错误，请重新输入！");
                }

            }
        });
        contentPane.add(loginButton);

        registerButton = new JButton("注册");
        registerButton.setFont(new Font("宋体", Font.PLAIN, 14));
        registerButton.setBounds(210, 240, 90, 30);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 跳转到注册界面
                new RegisterGUI();
                dispose();

            }
        });
        contentPane.add(registerButton);

        // 添加Power by标签
        powerByLabel = new JLabel("Power by rzw");
        powerByLabel.setFont(new Font("宋体", Font.PLAIN, 12));
        powerByLabel.setForeground(Color.GRAY);
        powerByLabel.setHorizontalAlignment(SwingConstants.CENTER);
        powerByLabel.setBounds(0, 270, 400, 20);
        contentPane.add(powerByLabel);
    }

    //生成随机数
    private void generateRandomCode() {
        StringBuilder sb = new StringBuilder("");
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int rand = random.nextInt(10);
            sb.append(rand);
        }
        randomCode = sb.toString();
    }
}