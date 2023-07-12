package com.gaohongsen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLData;
import javax.swing.JOptionPane;


public class MainWindow {
    private JFrame frame;
    private JLabel usernameLabel, passwordLabel, nameLabel, confirmPasswordLabel, permissionLabel;
    private JTextField usernameTextField, nameTextField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> permissionComboBox;
    private JButton loginButton, registerButton, confirmregisterButton;

    public MainWindow() {
        // 创建窗体和组件
        frame = new JFrame("登录注册窗体");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(null);
        frame.setResizable(false);

        usernameLabel = new JLabel("用户名:");
        usernameLabel.setBounds(20, 20, 80, 25);
        frame.add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setBounds(100, 20, 160, 25);
        frame.add(usernameTextField);

        passwordLabel = new JLabel("密码:");
        passwordLabel.setBounds(20, 50, 80, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(100, 50, 160, 25);
        frame.add(passwordField);

        nameLabel = new JLabel("姓名:");
        nameLabel.setBounds(20, 110, 80, 25);
        frame.add(nameLabel);

        nameTextField = new JTextField();
        nameTextField.setBounds(100, 80, 160, 25);
        frame.add(nameTextField);

        confirmPasswordLabel = new JLabel("确认密码:");
        confirmPasswordLabel.setBounds(20, 80, 80, 25);
        frame.add(confirmPasswordLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setBounds(100, 110, 160, 25);
        frame.add(confirmPasswordField);

        permissionLabel = new JLabel("权限:");
        permissionLabel.setBounds(20, 140, 80, 25);
        frame.add(permissionLabel);

        final String[] permissions = {"客户", "员工", "管理员"};
        permissionComboBox = new JComboBox<>(permissions);
        permissionComboBox.setBounds(100, 140, 160, 25);
        frame.add(permissionComboBox);

        loginButton = new JButton("登录");
        loginButton.setBounds(40, 170, 80, 25);
        frame.add(loginButton);

        registerButton = new JButton("注册");
        registerButton.setBounds(150, 170, 80, 25);
        frame.add(registerButton);

        confirmregisterButton = new JButton("确认注册");
        confirmregisterButton.setBounds(110, 170, 90, 25);
        frame.add(confirmregisterButton);

        // 隐藏注册时的相关组件
        nameLabel.setVisible(false);
        nameTextField.setVisible(false);
        confirmPasswordLabel.setVisible(false);
        confirmPasswordField.setVisible(false);
        permissionLabel.setVisible(false);
        permissionComboBox.setVisible(false);
        confirmregisterButton.setVisible(false);

        //登录按钮点击事件
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //核验用户名和密码的长度
                if (usernameTextField.getText().length() > 12 || usernameTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (passwordField.getText().length() > 12 || passwordField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else
                    //将登录时的用户名和密码，发送至数据库进行核验
                    switch (loginCheck(usernameTextField.getText(), passwordField.getText())) {
                        case 0:
                            //服务端返回值0，代表用户名或密码错误,弹出一个错误框
                            JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                            break;
                        case 1:
                            //服务端返回值1，代表登录成功,弹出一个信息提示框
                            JOptionPane.showMessageDialog(null, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                            //////////////////////////////////////////////////////
                            //进入主页，同时根据当前用户名向数据库询问权限
                            //username.getPermission()
                            //repaint()
                            //////////////////////////////////////////////////////
                            break;
                    }
            }
        });
        //注册按钮点击事件
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // 显示注册时的相关组件
                loginButton.setVisible(false);
                registerButton.setVisible(false);
                confirmregisterButton.setVisible(true);
                nameLabel.setVisible(true);
                nameTextField.setVisible(true);
                confirmPasswordLabel.setVisible(true);
                confirmPasswordField.setVisible(true);
                permissionLabel.setVisible(true);
                permissionComboBox.setVisible(true);
            }
        });
        //确认注册按钮点击事件
        confirmregisterButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {


                //核验用户名，密码和姓名的长度
                if (usernameTextField.getText().length() > 12 || usernameTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (passwordField.getText().length() > 12 || passwordField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                else if (nameTextField.getText().length() > 12 || usernameTextField.getText().length() < 3)
                    JOptionPane.showMessageDialog(null, "姓名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);

                    //核验密码与确认密码
                else if (!passwordField.getText().equals(confirmPasswordField.getText()))
                    JOptionPane.showMessageDialog(null, "前后密码不同！", "错误", JOptionPane.ERROR_MESSAGE);
                else {
                    //将登录时的用户名，密码，姓名，权限，发送至数据库进行核验
                    switch (registerCheck(usernameTextField.getText(), passwordField.getText(), nameTextField.getText(), permissionLabel.getText())) {
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
                            // 返回登录界面，显示登录时的相关组件
                            loginButton.setVisible(true);
                            registerButton.setVisible(true);
                            nameLabel.setVisible(false);
                            nameTextField.setVisible(false);
                            confirmPasswordLabel.setVisible(false);
                            confirmPasswordField.setVisible(false);
                            permissionLabel.setVisible(false);
                            permissionComboBox.setVisible(false);
                            confirmregisterButton.setVisible(false);
                            break;
                    }
                }
            }
        });

        // 显示窗体
        frame.setVisible(true);
    }

    public int loginCheck(String username, String password) {
        return 0;
        //////////////////////////////////////////////////////
        //将登录时的用户名和密码，发送至数据库进行核验
        //错误则return 0，成功则return 1
        //////////////////////////////////////////////////////
    }

    public int registerCheck(String username, String password, String name, String permission) {
        return 0;
        //////////////////////////////////////////////////////
        //将注册时的用户名，发送至数据库进行核验
        //用户名重复则return 0，姓名重复则return 1，成功则return 2，同时将该用户信息存入数据库
        //////////////////////////////////////////////////////
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
