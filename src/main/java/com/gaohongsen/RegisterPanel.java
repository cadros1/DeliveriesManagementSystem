package com.gaohongsen;

import javax.swing.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class RegisterPanel extends JPanel {

    public RegisterPanel() {
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

        final String[] permissions = {"-请选择-", "客户", "员工", "管理员"};
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
        ImageIcon icon = null; // 创建背景图片对象
        if (resource != null)
            icon = new ImageIcon(resource);
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        if (icon != null)
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
            else if (permissionComboBox.getSelectedIndex() == 0)
                JOptionPane.showMessageDialog(null, "请选择账户权限！", "错误", JOptionPane.ERROR_MESSAGE);
            else {
                try {
                    user = registerCheck(accountTextField.getText(),
                            String.valueOf(passwordField.getPassword()),
                            nameTextField.getText(),
                            permissionComboBox.getSelectedIndex());
                    JOptionPane.showMessageDialog(null, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(contentPane, "login");
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // 添加返回按钮的点击事件处理逻辑
        backToLoginButton.addActionListener(e -> cardLayout.show(contentPane, "login"));
    }

    public User registerCheck(String account, String password, String name, int permission) throws Exception {
        Reply reply = (Reply) Client.sendRequest(new Request(2, new User(account, password, name, permission)));
        if (reply.hasSucceed()) {
            return (User) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }


    public static boolean passwordMatches(char[] password1, char[] password2) {
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
}
