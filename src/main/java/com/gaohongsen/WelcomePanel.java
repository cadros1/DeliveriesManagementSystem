package com.gaohongsen;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class WelcomePanel extends JPanel {

    public WelcomePanel(MainWindow mainWindow) {
        setLayout(null);

        SearchPanel searchPanel = new SearchPanel();
        contentPane.add(searchPanel, "search");
        NewPanel newPanel = new NewPanel();
        contentPane.add(newPanel, "new");
        LogPanel logPanel = new LogPanel();
        contentPane.add(logPanel, "log");
        AlterPanel alterPanel = new AlterPanel();
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
        JMI1.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    null,
                    "账户名:  " + user.getAccount() + "\n姓名    :  " + user.getName() + "\n用户ID:  " + user.getId() + "\n权限    :  " + user.getPermissionString(),
                    "账户信息",
                    JOptionPane.INFORMATION_MESSAGE);
        });
        JMenuItem JMI2 = new JMenuItem("登出账户");//创建一个菜单项
        JM1.add(JMI2);//添加到菜单
        JMI2.addActionListener(e -> {
            String[] str = exitCheck(user.getAccount()).split("/");
            switch (str[0]) {
                case "0":
                    //服务端返回值0，代表登出失败,弹出一个错误框
                    JOptionPane.showMessageDialog(null, str[1], "错误", JOptionPane.ERROR_MESSAGE);
                    break;
                case "1":
                    //服务端返回值1，代表登出成功,弹出一个信息提示框
                    JOptionPane.showMessageDialog(null, "登录成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    // 登出成功后回到登录界面
                    cardLayout.show(contentPane, "login");
                    break;
            }
        });
        JMenuItem JMI3 = new JMenuItem("修改密码");//创建一个菜单项
        JM1.add(JMI3);//添加到菜单
        JMI3.addActionListener(e -> {
            new PasswordChange();
        });

        JMenu JM2 = new JMenu("帮助");//创建一个子菜单
        JMB.add(JM2);//添加到菜单栏

        JMenuItem JMI5 = new JMenuItem("版本信息");//创建一个菜单项
        JM2.add(JMI5);//添加到菜单
        JMI5.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "version1.0", "版本信息", JOptionPane.INFORMATION_MESSAGE);
        });
        JMenuItem JMI6 = new JMenuItem("关于我们");//创建一个菜单项
        JM2.add(JMI6);//添加到菜单
        JMI6.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "BY高洪森&李祖怡（SCU）", "关于我们", JOptionPane.INFORMATION_MESSAGE);
        });


        JButton searchButton = new JButton("查找物流");
        searchButton.setBounds(20, 20, 90, 25);
        add(searchButton);
        searchButton.addActionListener(e -> {
            cardLayout.show(contentPane, "search");
        });

        JButton newButton = new JButton("新建物流");
        newButton.setBounds(120, 20, 90, 25);
        add(newButton);
        newButton.addActionListener(e -> {
            cardLayout.show(contentPane, "new");
        });

        JButton logButton = new JButton("日志");
        logButton.setBounds(220, 20, 90, 25);
        add(logButton);
        logButton.addActionListener(e -> {
            cardLayout.show(contentPane, "log");
        });

        JButton alterButton = new JButton("删改物流");
        alterButton.setBounds(320, 20, 90, 25);
        add(alterButton);
        alterButton.addActionListener(e -> {
            cardLayout.show(contentPane, "alter");
        });

        //设置背景
        JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
        URL resource = this.getClass().getResource("/WhiteLine.jpg"); // 获取背景图片路径
        ImageIcon icon = new ImageIcon(resource); // 创建背景图片对象
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
        add(lblBackground); // 将组件添加到面板中

    }

    public String exitCheck(String account) {
        return "1";
//        try {
//            return Client.sendRequest("1/" + account);
//        } catch (IOException e) {
//            return "0/" + e.getMessage();
//        }
        //将登出时的用户名，发送至数据库进行核验
    }


}
