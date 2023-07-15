package com.gaohongsen;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class PasswordChange extends JFrame {
    public PasswordChange() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("修改密码");
        setSize(300, 200);
        setResizable(false);
        setVisible(true);

        JLabel accountLabel = new JLabel("用户名:" + user.getAccount());
        accountLabel.setBounds(20, 20, 60, 25);
        add(accountLabel);

        JLabel originalPasswordLabel = new JLabel("原密码:");
        originalPasswordLabel.setBounds(20, 50, 80, 25);
        add(originalPasswordLabel);

        JPasswordField originalPasswordField = new JPasswordField();
        originalPasswordField.setBounds(70, 50, 160, 25);
        add(originalPasswordField);

        JLabel newPasswordLabel = new JLabel("新密码:");
        newPasswordLabel.setBounds(20, 80, 80, 25);
        add(newPasswordLabel);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setBounds(70, 80, 160, 25);
        add(newPasswordField);

        JButton backButton = new JButton("取消");
        backButton.setBounds(20, 110, 80, 25);
        add(backButton);

        JButton confirmChangeButton = new JButton("确认");
        confirmChangeButton.setBounds(150, 110, 80, 25);
        add(confirmChangeButton);

        setLayout(null);

        backButton.addActionListener(e -> dispose());
        confirmChangeButton.addActionListener(e -> {
            String[] str = passwordChangeCheck(
                    user.getAccount(),
                    String.valueOf(originalPasswordField.getPassword()),
                    String.valueOf(newPasswordField.getPassword())
            ).split("/");
            //将修改密码时的用户名，原密码，密码发送至数据库进行核验
            switch (Integer.parseInt(str[0])) {
                case 0:
                    //服务端返回值0，代表修改失败
                    JOptionPane.showMessageDialog(null, str[1], "错误", JOptionPane.ERROR_MESSAGE);
                    break;
                case 1:
                    //服务端返回值1，代表修改成功
                    JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    // 修改成功后关闭该界面
                    dispose();
                    break;
            }
        });

    }

    public String passwordChangeCheck(String account, String originalPassword, String newPassword) {
        return "1";
//        try {
//            return Client.sendRequest("3/" + account+"/"+originalPassword+"/"+newPassword);
//        } catch (IOException e) {
//            return "0/" + e.getMessage();
//        }
        //将登出时的用户名，发送至数据库进行核验
    }

//    public static void main(String[] args) {
//        new PasswordChange();
//    }
}
