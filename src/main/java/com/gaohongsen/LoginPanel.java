package com.gaohongsen;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class LoginPanel extends JPanel {
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

                        MainAppPanel mainAppPanel = new MainAppPanel(mainWindow);
                        contentPane.add(mainAppPanel, "mainApp");
                        cardLayout.show(contentPane, "mainApp");
                        break;
                }
            }
        });

        // 添加注册按钮的点击事件处理逻辑
        registerButton.addActionListener(e -> cardLayout.show(contentPane, "register"));

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


}
