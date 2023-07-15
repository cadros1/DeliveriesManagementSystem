package com.gaohongsen;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class AlterPanel extends JPanel {

    public AlterPanel(MainWindow mainWindow) {
        setLayout(null);

        JLabel titleLabel = new JLabel("请输入单号：");
        titleLabel.setBounds(10, 50, 80, 25);
        add(titleLabel);
        JTextField titleTextField = new JTextField();
        titleTextField.setBounds(90, 50, 80, 25);
        add(titleTextField);

        JLabel sendPlaceLabel = new JLabel("发货地：");
        sendPlaceLabel.setBounds(10, 80, 80, 25);
        add(sendPlaceLabel);
        JTextField sendPlaceTextField = new JTextField();
        sendPlaceTextField.setBounds(90, 80, 80, 25);
        add(sendPlaceTextField);

        JLabel receivePlaceLabel = new JLabel("收货地：");
        receivePlaceLabel.setBounds(10, 110, 80, 25);
        add(receivePlaceLabel);
        JTextField receivePlaceTextField = new JTextField();
        receivePlaceTextField.setBounds(90, 110, 80, 25);
        add(receivePlaceTextField);

        JLabel senderLabel = new JLabel("发件人：");
        senderLabel.setBounds(300, 80, 80, 25);
        add(senderLabel);
        JTextField senderTextField = new JTextField();
        senderTextField.setBounds(380, 80, 80, 25);
        add(senderTextField);

        JLabel receiverLabel = new JLabel("收件人：");
        receiverLabel.setBounds(300, 110, 80, 25);
        add(receiverLabel);
        JTextField receiverTextField = new JTextField();
        receiverTextField.setBounds(380, 110, 80, 25);
        add(receiverTextField);

        JLabel logisticStatusLabel = new JLabel("物流状态");
        logisticStatusLabel.setBounds(10, 140, 80, 25);
        add(logisticStatusLabel);
        JTextField logisticStatusTextField = new JTextField();
        logisticStatusTextField.setBounds(90, 140, 80, 25);
        add(logisticStatusTextField);


        JButton confirmTitleButton = new JButton("重置");
        confirmTitleButton.setBounds(10, 170, 80, 25);
        add(confirmTitleButton);
        confirmTitleButton.addActionListener(e -> {
            /**************************************************************************
             * 待完成
             * 查找titleTextField对应的物流信息,错误则弹出提示框，成功则通过setText更改下方输入框的值
             *************************************************************************/

        });

        JButton confirmButton = new JButton("更改");
        confirmButton.setBounds(300, 170, 80, 25);
        add(confirmButton);
        confirmButton.addActionListener(e -> {
            /**************************************************************************
             * 待完成
             * 将物流信息上传，弹出提示框
             *************************************************************************/
        });

        JButton deleteButton = new JButton("删除");
        deleteButton.setBounds(590, 170, 80, 25);
        add(deleteButton);
        deleteButton.addActionListener(e -> {
            /**************************************************************************
             * 待完成
             * 将删除请求上传，弹出提示框
             *************************************************************************/
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
}