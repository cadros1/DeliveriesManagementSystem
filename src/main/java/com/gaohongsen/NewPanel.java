package com.gaohongsen;

import javax.swing.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

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
        JLabel situationLabel = new JLabel("物流状态");
        situationLabel.setBounds(10, 140, 80, 25);
        add(situationLabel);

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

        final String[] situation = {"-请选择-", "未发货","已揽收","已发货", "已到货","已签收"};
        JComboBox<String> situationComboBox = new JComboBox<>(situation);
        situationComboBox.setBounds(90, 140, 160, 25);
        add(situationComboBox);

        JButton confirmNewButton = new JButton("创建");
        confirmNewButton.setBounds(300, 140, 80, 25);
        add(confirmNewButton);
        confirmNewButton.addActionListener(e -> {
            if (sendPlaceTextField .getText().length() > 12 ||sendPlaceTextField.getText().length()<2)
                JOptionPane.showMessageDialog(null, "发货地长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (receivePlaceTextField.getText().length() > 12 ||receivePlaceTextField.getText().length()<2)
                JOptionPane.showMessageDialog(null, "收货地长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (senderTextField.getText().length() > 12 ||senderTextField.getText().length()<2)
                JOptionPane.showMessageDialog(null, "发件人长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else if (receiverTextField .getText().length() > 12 ||receiverTextField.getText().length()<2)
                JOptionPane.showMessageDialog(null, "收件人长度错误！", "错误", JOptionPane.ERROR_MESSAGE);
            else {
                try {
                    Delivery delivery= newDelivery(
                            sendPlaceTextField.getText(),
                            receivePlaceTextField.getText(),
                            senderTextField.getText(),
                            receiverTextField.getText(),
                            situationComboBox.getSelectedIndex());
                    JOptionPane.showMessageDialog(null, "新建成功！\n单号："+delivery.getId(),"提示", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }

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

    public Delivery newDelivery(String sendPlace, String receivePlace, String sender, String receiver, int situation)throws Exception {
        Reply reply = (Reply) Client.sendRequest(new Request(4, new Delivery(sendPlace,receivePlace, sender, receiver,situation)));
        if (reply.hasSucceed()) {
            return (Delivery) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }
}