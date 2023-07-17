package com.gaohongsen;

import javax.swing.*;

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

            //将修改密码时的用户名，原密码，密码发送至数据库进行核验
            if (originalPasswordField.getPassword().length > 12 || originalPasswordField.getPassword().length < 3)
                JOptionPane.showMessageDialog(null, "原密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (newPasswordField.getPassword().length > 12 || newPasswordField.getPassword().length < 3)
                JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (RegisterPanel.passwordMatches(originalPasswordField.getPassword(), newPasswordField.getPassword())) {
                JOptionPane.showMessageDialog(null, "前后密码相同！", "错误", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    user = passwordChangeCheck(
                            user.getAccount(),
                            String.valueOf(originalPasswordField.getPassword()),
                            String.valueOf(newPasswordField.getPassword()));
                    onlineState = false;
                    JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    cardLayout.show(contentPane, "login");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

    }


    public User passwordChangeCheck(String account, String originalPassword, String newPassword) throws Exception {
        //将登录时的用户名和密码，发送至数据库进行核验
        Reply reply = (Reply) Client.sendRequest(new Request(3, new User(account, originalPassword, newPassword)));
        if (reply.hasSucceed()) {
            return (User) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }
}
