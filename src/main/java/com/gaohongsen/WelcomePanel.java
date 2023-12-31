package com.gaohongsen;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel(MainWindow mainWindow) {
        setLayout(null);

        DeliveryListPanel deliveryListPanel = new DeliveryListPanel();
        contentPane.add(deliveryListPanel, "search");
        NewPanel newPanel = new NewPanel(mainWindow);
        contentPane.add(newPanel, "new");
        LogPanel logPanel = new LogPanel();
        contentPane.add(logPanel, "log");
        AlterPanel alterPanel = new AlterPanel(mainWindow);
        contentPane.add(alterPanel, "alter");

        Font font = new Font("宋体", Font.PLAIN, 80);//创建1个字体实例
        JLabel WelcomeLabel1 = new JLabel("Welcome!");
        WelcomeLabel1.setBounds(100, 100, 750, 125);
        add(WelcomeLabel1);
        WelcomeLabel1.setFont(font);
        JLabel WelcomeLabel2 = new JLabel("欢迎回来，" + user.getName());
        WelcomeLabel2.setBounds(100, 200, 750, 125);
        add(WelcomeLabel2);
        WelcomeLabel2.setFont(font);


        JMenuBar JMB = new JMenuBar();//创建菜单栏
        mainWindow.setJMenuBar(JMB);
        //设置菜单栏(添加)

        JMenu JM1 = new JMenu("账户");//创建一个菜单
        JMB.add(JM1);//添加到菜单栏

        JMenuItem JMI1 = new JMenuItem("账户信息");//创建一个菜单项
        JM1.add(JMI1);//添加到菜单
        JMI1.addActionListener(e -> JOptionPane.showMessageDialog(
                null,
                "账户名:  " + user.getAccount() + "\n姓名    :  " + user.getName() + "\n用户ID:  " + user.getId() + "\n权限    :  " + user.getPermissionString(),
                "账户信息",
                JOptionPane.INFORMATION_MESSAGE));
        JMenuItem JMI2 = new JMenuItem("登出账户");//创建一个菜单项
        JM1.add(JMI2);//添加到菜单
        JMI2.addActionListener(e -> {
            try {
                user = exitCheck(user.getAccount());
                onlineState = false;
                JOptionPane.showMessageDialog(null, "登出成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(contentPane, "login");
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenuItem JMI3 = new JMenuItem("修改密码");//创建一个菜单项
        JM1.add(JMI3);//添加到菜单
        JMI3.addActionListener(e -> new PasswordChange());

        JMenuItem JMI4 = new JMenuItem("注销账户");//创建一个菜单项
        JM1.add(JMI4);//添加到菜单
        JMI4.addActionListener(e -> {
            try {
                logOut(user.getAccount());
                onlineState = false;
                JOptionPane.showMessageDialog(null, "注销成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                cardLayout.show(contentPane, "login");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });

        JMenu JM2 = new JMenu("帮助");//创建一个子菜单
        JMB.add(JM2);//添加到菜单栏

        JMenuItem JMI5 = new JMenuItem("版本信息");//创建一个菜单项
        JM2.add(JMI5);//添加到菜单
        JMI5.addActionListener(e -> JOptionPane.showMessageDialog(null, "version1.0", "版本信息", JOptionPane.INFORMATION_MESSAGE));
        JMenuItem JMI6 = new JMenuItem("关于我们");//创建一个菜单项
        JM2.add(JMI6);//添加到菜单.
        JMI6.addActionListener(e -> JOptionPane.showMessageDialog(null, "BY高洪森&李祖怡（SCU）\n联系方式：lizuyi@scu.edu.cn", "关于我们", JOptionPane.INFORMATION_MESSAGE));


        JButton searchButton = new JButton("物流清单");
        searchButton.setBounds(20, 20, 90, 25);
        add(searchButton);
        searchButton.addActionListener(e -> cardLayout.show(contentPane, "search"));

        JButton newButton = new JButton("新建物流");
        newButton.setBounds(120, 20, 90, 25);
        add(newButton);
        newButton.addActionListener(e -> {
                    if (user.getPermission() == 1)
                        JOptionPane.showMessageDialog(null, "权限不足！", "错误", JOptionPane.ERROR_MESSAGE);
                    else cardLayout.show(contentPane, "new");
                }
        );

        JButton alterButton = new JButton("查找物流");
        alterButton.setBounds(220, 20, 90, 25);
        add(alterButton);
        alterButton.addActionListener(e -> cardLayout.show(contentPane, "alter"));

        JButton logButton = new JButton("日志");
        logButton.setBounds(320, 20, 90, 25);
        add(logButton);
        logButton.addActionListener(e -> {
                    if (user.getPermission() == 1||user.getPermission()==2)
                        JOptionPane.showMessageDialog(null, "权限不足！", "错误", JOptionPane.ERROR_MESSAGE);
                    else cardLayout.show(contentPane, "log");
                }
        );

        //设置背景
        JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
        URL resource = this.getClass().getResource("/WhiteLine.jpg"); // 获取背景图片路径
        ImageIcon icon = null; // 创建背景图片对象
        if (resource != null)
            icon = new ImageIcon(resource);
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        if (icon != null)
            lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
        add(lblBackground); // 将组件添加到面板中

    }

    public User exitCheck(String account) throws Exception {
        //将登出时的用户名，发送至数据库进行核验
        Reply reply = (Reply) Client.sendRequest(new Request(1, new User(account)));
        if (reply.hasSucceed()) {
            return (User) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }

    public void logOut(String account) throws Exception {
        //将注销时的用户名，发送至数据库进行核验
        Reply reply = (Reply) Client.sendRequest(new Request(12, new User(account)));
        if (!reply.hasSucceed())
            throw (Exception) reply.getItem();
    }
}
