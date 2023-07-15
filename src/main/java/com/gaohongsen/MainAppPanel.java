package com.gaohongsen;

import javax.swing.*;

import static com.gaohongsen.MainWindow.*;

public class MainAppPanel extends JPanel {

    public MainAppPanel(MainWindow mainWindow) {
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


        JMenuBar JMB = new JMenuBar();//创建菜单栏
        mainWindow.setJMenuBar(JMB);
        //设置菜单栏(添加)


        JMenu JM1 = new JMenu("账户");//创建一个菜单
        JMB.add(JM1);//添加到菜单栏

        JMenuItem JMI1 = new JMenuItem("账户信息");//创建一个菜单项
        JM1.add(JMI1);//添加到菜单
        JMenuItem JMI2 = new JMenuItem("登出账户");//创建一个菜单项
        JM1.add(JMI2);//添加到菜单
        JMenuItem JMI3 = new JMenuItem("修改密码");//创建一个菜单项
        JM1.add(JMI3);//添加到菜单

        JMenu JM2 = new JMenu("帮助");//创建一个子菜单
        JMB.add(JM2);//添加到菜单栏

        JMenuItem JMI5 = new JMenuItem("版本信息");//创建一个菜单项
        JM2.add(JMI5);//添加到菜单
        JMenuItem JMI6 = new JMenuItem("关于我们");//创建一个菜单项
        JM2.add(JMI6);//添加到菜单

        JButton searchButton = new JButton("查找物流");
        searchButton.setBounds(20, 20, 90, 25);
        add(searchButton);

        JButton newButton = new JButton("新建物流");
        newButton.setBounds(120, 20, 90, 25);
        add(newButton);

        JButton logButton = new JButton("日志");
        logButton.setBounds(220, 20, 90, 25);
        add(logButton);








        // 设置背景
//            JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
//            URL resource = this.getClass().getResource("/WhiteLine.jpg"); // 获取背景图片路径
//            ImageIcon icon = new ImageIcon(resource); // 创建背景图片对象
//            lblBackground.setIcon(icon); // 设置标签组件要显示的图标
//            lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
//            add(lblBackground); // 将组件添加到面板中

    }
}
