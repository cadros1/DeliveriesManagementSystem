package com.gaohongsen;

/*
 @file MainWindow
  * @brief 实现图形化界面和交互逻辑
 * @author 李祖怡
 * @date 2023/7/14
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class MainWindow extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel contentPane;

    public static User user=new User(0,"0","0",0);


    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setResizable(false);

        // 创建内容面板和CardLayout
        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        // 创建登录和注册面板
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);

        // 添加面板到内容面板
        contentPane.add(loginPanel, "login");
        contentPane.add(registerPanel, "register");

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

    public void showRegisterPanel() {cardLayout.show(contentPane, "register");}

    public void showMainAppPanel() {
        cardLayout.show(contentPane, "mainApp");
    }

    private class LoginPanel extends JPanel {
        public LoginPanel(MainWindow mainWindow) {
            setLayout(null);
            // 初始化界面组件和布局
            JLabel accountLabel = new JLabel("用户名:");
            accountLabel.setBounds(240, 20, 80, 25);
            add(accountLabel);

            JTextField accountTextField = new JTextField();
            accountTextField.setBounds(320, 20, 160, 25);
            add(accountTextField);

            JLabel passwordLabel = new JLabel("密码:");
            passwordLabel.setBounds(240, 80, 80, 25);
            add(passwordLabel);

            JPasswordField passwordField = new JPasswordField();
            passwordField.setBounds(320, 80, 160, 25);
            add(passwordField);

            JButton loginButton = new JButton("登录");
            loginButton.setBounds(260, 220, 80, 25);
            add(loginButton);

            JButton registerButton = new JButton("注册");
            registerButton.setBounds(370, 220, 80, 25);
            add(registerButton);

            // 设置背景
            JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
            URL resource = this.getClass().getResource("/Harmony.jpg"); // 获取背景图片路径
            ImageIcon icon = new ImageIcon(resource); // 创建背景图片对象
            lblBackground.setIcon(icon); // 设置标签组件要显示的图标
            lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
            add(lblBackground); // 将组件添加到面板中

            // 添加登录按钮的点击事件处理逻辑
            loginButton.addActionListener(e -> {

                //核验用户名和密码的长度
                if (accountTextField.getText().length() > 12 || accountTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (passwordField.getPassword().length > 12 || passwordField.getPassword().length < 3)
                    JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else {
                    //将登录时的用户名和密码，发送至数据库进行核验
                    String[] str = loginCheck(accountTextField.getText(), String.valueOf(passwordField.getPassword())).split("/");
                    switch (str[0]) {
                        case "0":
                            //服务端返回值0，代表用户名或密码错误,弹出一个错误框
                            JOptionPane.showMessageDialog(null, str[1], "错误", JOptionPane.ERROR_MESSAGE);
                            break;
                        case "1":
                            //服务端返回值1，代表登录成功,弹出一个信息提示框
                            JOptionPane.showMessageDialog(null, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            // 登录成功后显示应用主界面
                            user = new User(Integer.parseInt(str[1]),accountTextField.getText(),str[2],Integer.parseInt(str[3]));

                            MainAppPanel mainAppPanel = new MainAppPanel();
                            contentPane.add(mainAppPanel, "mainApp");
                            mainWindow.showMainAppPanel();
                            break;
                    }
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

            JLabel accountLabel = new JLabel("用户名:");
            accountLabel.setBounds(240, 20, 80, 25);
            add(accountLabel);

            JTextField accountTextField = new JTextField();
            accountTextField.setBounds(320, 20, 160, 25);
            add(accountTextField);

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

            final String[] permissions = {"-请选择-","客户", "员工", "管理员"};
            JComboBox<String> permissionComboBox = new JComboBox<>(permissions);
            permissionComboBox.setBounds(320, 140, 160, 25);
            add(permissionComboBox);

            JButton backToLoginButton = new JButton("返回");
            backToLoginButton.setBounds(260, 220, 90, 25);
            add(backToLoginButton);

            JButton confirmRegisterButton = new JButton("确认注册");
            confirmRegisterButton.setBounds(370, 220, 80, 25);
            add(confirmRegisterButton);

            // 设置背景
            JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
            URL resource = this.getClass().getResource("/Harmony.jpg"); // 获取背景图片路径
            ImageIcon icon = new ImageIcon(resource); // 创建背景图片对象
            lblBackground.setIcon(icon); // 设置标签组件要显示的图标
            lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
            add(lblBackground); // 将组件添加到面板中

            // 添加确认注册按钮的点击事件处理逻辑
            confirmRegisterButton.addActionListener(e -> {

                //核验用户名，密码和姓名的长度
                if (accountTextField.getText().length() > 12 || accountTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (passwordField.getPassword().length > 12 || passwordField.getPassword().length < 3)
                    JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (nameTextField.getText().length() > 12 || nameTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "姓名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                    //核验密码与确认密码
                else if (!passwordMatches(passwordField.getPassword(), confirmPasswordField.getPassword()))
                    JOptionPane.showMessageDialog(null, "前后密码不同！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (permissionComboBox.getSelectedIndex()==0)
                    JOptionPane.showMessageDialog(null, "请选择账户权限！", "错误", JOptionPane.ERROR_MESSAGE);
                else {
                    String[] str = registerCheck(accountTextField.getText(), String.valueOf(passwordField.getPassword()), nameTextField.getText(), permissionComboBox.getSelectedIndex()).split("/");
                    //将登录时的用户名，密码，姓名，权限，发送至数据库进行核验
                    switch (Integer.parseInt(str[0])) {
                        case 0:
                            //服务端返回值0，代表注册失败
                            JOptionPane.showMessageDialog(null, str[1], "错误", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 1:
                            //服务端返回值1，代表注册成功
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

    public static class MainAppPanel extends JPanel {

        public MainAppPanel() {
            setLayout(null);


            JLabel IDLabel = new JLabel("Welcome!"+user.getId());
            IDLabel.setBounds(260, 140, 80, 25);
            add(IDLabel);

            JLabel welcomeLabel = new JLabel("Welcome!"+user.getName());
            welcomeLabel.setBounds(260, 170, 80, 25);
            add(welcomeLabel);

            JLabel testLabel = new JLabel("Welcome!"+user.getPermission());
            testLabel.setBounds(260, 200, 80, 25);
            add(testLabel);





            // 设置背景
            JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
            URL resource = this.getClass().getResource("/WhiteLine.jpg"); // 获取背景图片路径
            ImageIcon icon = new ImageIcon(resource); // 创建背景图片对象
            lblBackground.setIcon(icon); // 设置标签组件要显示的图标
            lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
            add(lblBackground); // 将组件添加到面板中

        }
    }

    public String loginCheck(String account, String password) {
//        return "1/001/Jack/2";
        try {
            return Client.sendRequest("0/" + account + "/" + password);
        } catch (IOException e) {
            return "0/" + e.getMessage();
        }
        //将登录时的用户名和密码，发送至数据库进行核验
    }

    public String registerCheck(String username, String password, String name, int permission) {
        try {
            return Client.sendRequest("2/" + username + "/" + password + "/" + name + "/" + permission);
        } catch (IOException e) {
            return "0/" + e.getMessage();
        }
        //将注册时的用户名，发送至数据库进行核验
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