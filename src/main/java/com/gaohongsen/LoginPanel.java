package com.gaohongsen;

import javax.swing.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class LoginPanel extends JPanel {
    public static String verifyCode;
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

        JLabel verifyLabel = new JLabel("验证码");
        verifyLabel.setBounds(240, 140, 80, 25);
        add(verifyLabel);

        JTextField verifyTextField = new JTextField();
        verifyTextField.setBounds(320, 140, 160, 25);
        add(verifyTextField);

        JButton changeVerifyButton = new JButton("切换验证码");
        changeVerifyButton.setBounds(380, 170, 100, 25);
        add(changeVerifyButton);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(260, 230, 80, 25);
        add(loginButton);

        JButton registerButton = new JButton("注册");
        registerButton.setBounds(370, 230, 80, 25);
        add(registerButton);

        // 设置图片验证码
        JLabel imgVerifyLabel = new JLabel(); // 创建一个标签组件对象
        ImgVerifyCode imgVerifyCode = new ImgVerifyCode();
        ImageIcon img = new ImageIcon(imgVerifyCode.getImage()); // 创建验证码图片对象
        imgVerifyLabel.setIcon(img); // 设置标签组件要显示的图标
        imgVerifyLabel.setBounds(250, 170, img.getIconWidth(), img.getIconHeight()); // 设置组件的显示位置及大小
        add(imgVerifyLabel); // 将组件添加到面板中
        verifyCode = imgVerifyCode.getText();

        // 设置背景
        JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
        URL resource = this.getClass().getResource("/Harmony.jpg"); // 获取背景图片路径
        ImageIcon icon = null; // 创建背景图片对象
        if (resource != null)
            icon = new ImageIcon(resource);
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        if (icon != null)
            lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
        add(lblBackground); // 将组件添加到面板中

        // 添加登录按钮的点击事件处理逻辑
        loginButton.addActionListener(e -> {
            //核验用户名和密码的长度
            if (accountTextField.getText().length() > 12 || accountTextField.getText().length() < 2)
                JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (passwordField.getPassword().length > 12 || passwordField.getPassword().length < 2)
                JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (!verifyTextField.getText().equalsIgnoreCase(verifyCode)) {
                JOptionPane.showMessageDialog(null, "验证码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                changeVerify(imgVerifyLabel);
            }
            else {
                try {
                    //将登录时的用户名和密码，发送至数据库进行核验
                    user = loginCheck(accountTextField.getText(), String.valueOf(passwordField.getPassword()));
                    onlineState=true;
                    JOptionPane.showMessageDialog(null, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    //重置验证码
                    changeVerify(imgVerifyLabel);
                    //清空输入框的数据
                    accountTextField.setText(null);
                    passwordField.setText(null);
                    verifyTextField.setText(null);
                    // 登录成功后显示应用主界面
                    WelcomePanel welcomePanel = new WelcomePanel(mainWindow);
                    contentPane.add(welcomePanel, "welcome");
                    cardLayout.show(contentPane, "welcome");
                } catch (Exception exception) {
                    //登陆失败时显示失败原因
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    //重置验证码
                    changeVerify(imgVerifyLabel);
                }
            }
        });
        // 添加注册按钮的点击事件处理逻辑
        registerButton.addActionListener(e -> {
            //清空登陆时的输入框数据
            accountTextField.setText(null);
            passwordField.setText(null);
            verifyTextField.setText(null);
            //显示注册页面
            cardLayout.show(contentPane, "register");
        });
        // 添加切换验证码按钮的点击事件处理逻辑
        changeVerifyButton.addActionListener(e -> changeVerify(imgVerifyLabel));
    }

    public User loginCheck(String account, String password) throws Exception {
        //将登录时的用户名和密码，发送至数据库进行核验
        Reply reply = (Reply) Client.sendRequest(new Request(0, new User(account, password)));
        if (reply.hasSucceed()) {
            return (User) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }

    public void changeVerify(JLabel L){
        ImgVerifyCode i = new ImgVerifyCode();
        ImageIcon img = new ImageIcon(i.getImage()); // 创建验证码图片对象
        L.setIcon(img); // 设置标签组件要显示的图标
        verifyCode = i.getText();
    }
}



