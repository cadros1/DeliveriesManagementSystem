package com.gaohongsen;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class SearchPanel extends JPanel {

    public SearchPanel(MainWindow mainWindow) {
        setLayout(null);

        JLabel searchLabel = new JLabel("查找内容");
        searchLabel.setBounds(20, 50, 90, 25);
        add(searchLabel);

        final String[] permissions = {"-请选择-","单号", "发货地", "收货地","发件人","收件人"};
        JComboBox<String> permissionComboBox = new JComboBox<>(permissions);
        permissionComboBox.setBounds(100, 50, 90, 25);
        add(permissionComboBox);

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(200, 50, 90, 25);
        add(searchTextField);

        JButton confirmSearchButton = new JButton("查找");
        confirmSearchButton.setBounds(300, 50, 90, 25);
        add(confirmSearchButton);
        confirmSearchButton.addActionListener(e -> {

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

        //设置背景
        JLabel lblBackground = new JLabel(); // 创建一个标签组件对象
        URL resource = this.getClass().getResource("/WhiteLine.jpg"); // 获取背景图片路径
        ImageIcon icon = new ImageIcon(resource); // 创建背景图片对象
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
        add(lblBackground); // 将组件添加到面板中
    }
}
