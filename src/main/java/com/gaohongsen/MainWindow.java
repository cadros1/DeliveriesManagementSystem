package com.gaohongsen;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private final LoginPanel loginPanel;
    private final RegisterPanel registerPanel;
    private final MainAppPanel mainAppPanel;
    private final CardLayout cardLayout;
    private final JPanel contentPane;

    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setResizable(false);

        // 创建内容面板和CardLayout
        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        // 创建登录和注册面板
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        mainAppPanel = new MainAppPanel(this);

        // 添加面板到内容面板
        contentPane.add(loginPanel, "login");
        contentPane.add(registerPanel, "register");
        contentPane.add(mainAppPanel, "mainApp");

        // 设置初始显示的面板为登录面板
        showLoginPanel();

        // 设置内容面板为窗体的内容面板
        setContentPane(contentPane);

        // 显示窗体
        setVisible(true);
    }

    public void showLoginPanel() {
        cardLayout.show(contentPane, "login");
    }

    public void showRegisterPanel() {
        cardLayout.show(contentPane, "register");
    }

    public void showMainAppPanel() {
        cardLayout.show(contentPane, "mainApp");
    }

    private class LoginPanel extends JPanel {
        public LoginPanel(MainWindow mainWindow) {
            setLayout(null);
            // 初始化界面组件和布局
            JLabel usernameLabel = new JLabel("用户名:");
            usernameLabel.setBounds(240, 20, 80, 25);
            add(usernameLabel);

            JTextField usernameTextField = new JTextField();
            usernameTextField.setBounds(320, 20, 160, 25);
            add(usernameTextField);

            JLabel passwordLabel = new JLabel("密码:");
            passwordLabel.setBounds(240, 50, 80, 25);
            add(passwordLabel);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(320, 50, 160, 25);
            add(passwordField);

            JButton loginButton = new JButton("登录");
            loginButton.setBounds(260, 220, 80, 25);
            add(loginButton);

            JButton registerButton = new JButton("注册");
            registerButton.setBounds(370, 220, 80, 25);
            add(registerButton);

            // 添加登录按钮的点击事件处理逻辑
            loginButton.addActionListener(e -> {

                //核验用户名和密码的长度
                if (usernameTextField.getText().length() > 12 || usernameTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (passwordField.getPassword().length > 12 || passwordField.getPassword().length < 3)
                    JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else
                    //将登录时的用户名和密码，发送至数据库进行核验
                    switch (loginCheck(usernameTextField.getText(), String.valueOf(passwordField.getPassword()))) {
                        case 0:
                            //服务端返回值0，代表用户名或密码错误,弹出一个错误框
                            JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 1:
                            //服务端返回值1，代表登录成功,弹出一个信息提示框
                            JOptionPane.showMessageDialog(null, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            // 登录成功后显示应用主界面
                            mainWindow.showMainAppPanel();

                            break;
                    }
            });

            // 添加注册按钮的点击事件处理逻辑
            registerButton.addActionListener(e -> mainWindow.showRegisterPanel());

        }
    }

    private class RegisterPanel extends JPanel {

        public RegisterPanel(MainWindow mainWindow) {
            setLayout(null);
            // 初始化界面组件和布局
            JLabel usernameLabel = new JLabel("用户名:");
            usernameLabel.setBounds(240, 20, 80, 25);
            add(usernameLabel);

            JTextField usernameTextField = new JTextField();
            usernameTextField.setBounds(320, 20, 160, 25);
            add(usernameTextField);

            JLabel passwordLabel = new JLabel("密码:");
            passwordLabel.setBounds(240, 50, 80, 25);
            add(passwordLabel);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(320, 50, 160, 25);
            add(passwordField);

            JLabel confirmPasswordLabel = new JLabel("确认密码:");
            confirmPasswordLabel.setBounds(240, 80, 80, 25);
            add(confirmPasswordLabel);

            JPasswordField confirmPasswordField = new JPasswordField();
            confirmPasswordField.setBounds(320, 80, 160, 25);
            add(confirmPasswordField);

            JLabel nameLabel = new JLabel("姓名:");
            nameLabel.setBounds(240, 110, 80, 25);
            add(nameLabel);

            JTextField nameTextField = new JTextField();
            nameTextField.setBounds(320, 110, 160, 25);
            add(nameTextField);

            JLabel permissionLabel = new JLabel("权限:");
            permissionLabel.setBounds(240, 140, 80, 25);
            add(permissionLabel);

            final String[] permissions = {"客户", "员工", "管理员"};
            JComboBox<String> permissionComboBox = new JComboBox<>(permissions);
            permissionComboBox.setBounds(320, 140, 160, 25);
            add(permissionComboBox);

            JButton backToLoginButton = new JButton("返回");
            backToLoginButton.setBounds(260, 220, 90, 25);
            add(backToLoginButton);

            JButton confirmRegisterButton = new JButton("确认注册");
            confirmRegisterButton.setBounds(370, 220, 80, 25);
            add(confirmRegisterButton);

            // 添加确认注册按钮的点击事件处理逻辑
            confirmRegisterButton.addActionListener(e -> {

                //核验用户名，密码和姓名的长度
                if (usernameTextField.getText().length() > 12 || usernameTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (passwordField.getPassword().length > 12 || passwordField.getPassword().length < 3)
                    JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (nameTextField.getText().length() > 12 || usernameTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "姓名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);

                    //核验密码与确认密码
                else if (!passwordMatches(passwordField.getPassword(),confirmPasswordField.getPassword()))
                    JOptionPane.showMessageDialog(null, "前后密码不同！", "错误", JOptionPane.ERROR_MESSAGE);
                else {
                    //将登录时的用户名，密码，姓名，权限，发送至数据库进行核验
                    switch (registerCheck(usernameTextField.getText(), String.valueOf(passwordField.getPassword()), nameTextField.getText(), permissionLabel.getText())) {
                        case 0:
                            //服务端返回值0，代表该用户名已被注册,弹出一个错误框
                            JOptionPane.showMessageDialog(null, "用户名已被注册！", "错误", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 1:
                            //服务端返回值1，代表该姓名已被注册,弹出一个错误框
                            JOptionPane.showMessageDialog(null, "同一人无法重复注册！", "错误", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 2:
                            //服务端返回值2，代表注册成功,弹出一个信息提示框
                            JOptionPane.showMessageDialog(null, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            // 注册成功后显示登录界面
                            mainWindow.showLoginPanel();
                            break;
                    }
                }
            });
            // 添加返回按钮的点击事件处理逻辑
            backToLoginButton.addActionListener(e -> mainWindow.showLoginPanel());
        }
    }

    private class MainAppPanel extends JPanel {
        public MainAppPanel(MainWindow mainWindow) {
            setLayout(null);
            JLabel testLabel = new JLabel("Welcome!");
            testLabel.setBounds(260, 140, 80, 25);
            add(testLabel);
            // 初始化界面组件和布局
        }
    }
    public int loginCheck(String username, String password) {
        return 1;
        //////////////////////////////////////////////////////
        //将登录时的用户名和密码，发送至数据库进行核验
        //错误则return 0，成功则return 1
        //////////////////////////////////////////////////////
    }

    public int registerCheck(String username, String password, String name, String permission) {
        return 2;
        //////////////////////////////////////////////////////
        //将注册时的用户名，发送至数据库进行核验
        //用户名重复则return 0，姓名重复则return 1，成功则return 2，同时将该用户信息存入数据库
        //////////////////////////////////////////////////////
    }
    public boolean passwordMatches(char[] password1, char[] password2) {
        if (password1.length != password2.length) {
            return false;
        }

        for (int i = 0; i < password1.length; i++) {
            if (password1[i] != password2[i]) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        new MainWindow();
    }
}



