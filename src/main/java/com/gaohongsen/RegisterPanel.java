package com.gaohongsen;

import javax.swing.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class RegisterPanel extends JPanel {
    public static String verifyCode;
    public RegisterPanel() {
        // 初始化界面组件和布局
        setLayout(null);

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

        JLabel verifyLabel = new JLabel("验证码");
        verifyLabel.setBounds(240, 170, 80, 25);
        add(verifyLabel);

        JTextField verifyTextField = new JTextField();
        verifyTextField.setBounds(320, 170, 160, 25);
        add(verifyTextField);

        JButton changeVerifyButton = new JButton("切换验证码");
        changeVerifyButton.setBounds(380, 200, 100, 25);
        add(changeVerifyButton);

        JButton backToLoginButton = new JButton("返回");
        backToLoginButton.setBounds(260, 260, 80, 25);
        add(backToLoginButton);

        JButton confirmRegisterButton = new JButton("确认注册");
        confirmRegisterButton.setBounds(370, 260, 80, 25);
        add(confirmRegisterButton);

        // 设置图片验证码
        JLabel imgVerifyLabel = new JLabel(); // 创建一个标签组件对象
        ImgVerifyCode imgVerifyCode = new ImgVerifyCode();
        ImageIcon img = new ImageIcon(imgVerifyCode.getImage()); // 创建验证码图片对象
        imgVerifyLabel.setIcon(img); // 设置标签组件要显示的图标
        imgVerifyLabel.setBounds(250, 205, img.getIconWidth(), img.getIconHeight()); // 设置组件的显示位置及大小
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

        // 添加确认注册按钮的点击事件处理逻辑
        confirmRegisterButton.addActionListener(e -> {
            //核验用户名，密码和姓名的长度
            if (accountTextField.getText().length() > 12 || accountTextField.getText().length() < 2)
                JOptionPane.showMessageDialog(null, "用户名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (passwordField.getPassword().length > 12 || passwordField.getPassword().length < 2)
                JOptionPane.showMessageDialog(null, "密码长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (nameTextField.getText().length() > 12 || nameTextField.getText().length() < 2)
                JOptionPane.showMessageDialog(null, "姓名长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
                //核验密码与确认密码
            else if (!passwordMatches(passwordField.getPassword(), confirmPasswordField.getPassword()))
                JOptionPane.showMessageDialog(null, "前后密码不同！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (permissionComboBox.getSelectedIndex() == 0)
                JOptionPane.showMessageDialog(null, "请选择账户权限！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (!verifyTextField.getText().equalsIgnoreCase(verifyCode)) {
                JOptionPane.showMessageDialog(null, "验证码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                changeVerify(imgVerifyLabel);
            }
            else {
                try {
                    user = registerCheck(accountTextField.getText(),
                            String.valueOf(passwordField.getPassword()),
                            nameTextField.getText(),
                            permissionComboBox.getSelectedIndex());
                    JOptionPane.showMessageDialog(null, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    //重置验证码
                    changeVerify(imgVerifyLabel);
                    //清空输入框的数据
                    accountTextField.setText(null);
                    nameTextField.setText(null);
                    passwordField.setText(null);
                    confirmPasswordField.setText(null);
                    permissionComboBox.setSelectedIndex(0);
                    verifyTextField.setText(null);
                    // 注册成功后显示登录界面
                    cardLayout.show(contentPane, "login");
                } catch (Exception exception) {
                    //注册失败时显示失败原因
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                    //重置验证码
                    changeVerify(imgVerifyLabel);
                }
            }
        });
        // 添加返回按钮的点击事件处理逻辑
        backToLoginButton.addActionListener(e -> {
            //重置验证码
            changeVerify(imgVerifyLabel);
            //清空输入框的数据
            accountTextField.setText(null);
            nameTextField.setText(null);
            passwordField.setText(null);
            confirmPasswordField.setText(null);
            permissionComboBox.setSelectedIndex(0);
            verifyTextField.setText(null);
            // 返回登录界面
            cardLayout.show(contentPane, "login");
        });
        // 添加切换验证码按钮的点击事件处理逻辑
        changeVerifyButton.addActionListener(e -> changeVerify(imgVerifyLabel));
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

    public void changeVerify(JLabel L){
        ImgVerifyCode i = new ImgVerifyCode();
        ImageIcon img = new ImageIcon(i.getImage()); // 创建新验证码图片对象
        L.setIcon(img); // 设置标签组件要显示的图标
        verifyCode=i.getText();
    }
}
