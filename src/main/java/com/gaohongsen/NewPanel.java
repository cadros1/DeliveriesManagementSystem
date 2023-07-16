package com.gaohongsen;

import javax.swing.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.cardLayout;
import static com.gaohongsen.MainWindow.contentPane;

public class NewPanel extends JPanel {

    public NewPanel() {
        setLayout(null);

        JLabel titleLabel = new JLabel("新建物流信息");
        titleLabel.setBounds(10, 50, 80, 25);
        add(titleLabel);
        JLabel sendPlaceLabel = new JLabel("发货地：");
        sendPlaceLabel.setBounds(10, 80, 80, 25);
        add(sendPlaceLabel);
        JLabel receivePlaceLabel = new JLabel("收货地：");
        receivePlaceLabel.setBounds(10, 110, 80, 25);
        add(receivePlaceLabel);
        JLabel senderLabel = new JLabel("发件人：");
        senderLabel.setBounds(300, 80, 80, 25);
        add(senderLabel);
        JLabel receiverLabel = new JLabel("收件人：");
        receiverLabel.setBounds(300, 110, 80, 25);
        add(receiverLabel);

        JTextField sendPlaceTextField = new JTextField();
        sendPlaceTextField.setBounds(90, 80, 80, 25);
        add(sendPlaceTextField);
        JTextField receivePlaceTextField = new JTextField();
        receivePlaceTextField.setBounds(90, 110, 80, 25);
        add(receivePlaceTextField);
        JTextField senderTextField = new JTextField();
        senderTextField.setBounds(380, 80, 80, 25);
        add(senderTextField);
        JTextField receiverTextField = new JTextField();
        receiverTextField.setBounds(380, 110, 80, 25);
        add(receiverTextField);

        JButton confirmNewButton = new JButton("创建");
        confirmNewButton.setBounds(10, 140, 80, 25);
        add(confirmNewButton);
        confirmNewButton.addActionListener(e -> {


        });

        JButton searchButton = new JButton("查找物流");
        searchButton.setBounds(20, 20, 90, 25);
        add(searchButton);
        searchButton.addActionListener(e -> cardLayout.show(contentPane, "search"));

        JButton newButton = new JButton("新建物流");
        newButton.setBounds(120, 20, 90, 25);
        add(newButton);
        newButton.addActionListener(e -> cardLayout.show(contentPane, "new"));

        JButton logButton = new JButton("日志");
        logButton.setBounds(220, 20, 90, 25);
        add(logButton);
        logButton.addActionListener(e -> cardLayout.show(contentPane, "log"));

        JButton alterButton = new JButton("删改物流");
        alterButton.setBounds(320, 20, 90, 25);
        add(alterButton);
        alterButton.addActionListener(e -> cardLayout.show(contentPane, "alter"));

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
}