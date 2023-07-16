package com.gaohongsen;

import javax.swing.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static com.gaohongsen.MainWindow.cardLayout;
import static com.gaohongsen.MainWindow.contentPane;

public class SearchPanel extends JPanel {

    public SearchPanel() {
        setLayout(null);

        JLabel searchLabel = new JLabel("查找内容:");
        searchLabel.setBounds(20, 50, 90, 25);
        add(searchLabel);

        final String[] permissions = {"-请选择-", "单号", "发货地", "收货地", "发件人", "收件人"};
        JComboBox<String> permissionComboBox = new JComboBox<>(permissions);
        permissionComboBox.setBounds(100, 50, 90, 25);
        add(permissionComboBox);

        JTextField searchTextField = new JTextField();
        searchTextField.setBounds(200, 50, 90, 25);
        add(searchTextField);

        JButton confirmSearchButton = new JButton("查找");
        confirmSearchButton.setBounds(300, 50, 90, 25);
        add(confirmSearchButton);


        AtomicReference<Iterator<Delivery>> deliveries=null;
        // 表格上的title
        String[] columnNames = new String[]{"序号", "单号", "发货地", "收货地", "发件人", "收件人", "物流状态"};
        // 表格中的内容，是一个二维数组
        String[][] logistics = new String[][]{
                {"1", "114", "北京", "成都", "张三", "李四", "0"},
                {"2", "115", "成都", "南京", "李四", "王五", "1"},
                {"3", "116", "上海", "深圳", "赵六", "孙七", "2"}};
        JTable table = new JTable(logistics, columnNames);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 100, 720, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 设置垂直滚动条一直显示
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 设置水平滚动条从不显示
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);

        confirmSearchButton.addActionListener(e -> {
            try {
                deliveries.set((Iterator<Delivery>) searchDelivery(0));
                JOptionPane.showMessageDialog(null, "查找成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }


            /**************************************************************************
             * 待完成
             * 通过选中项，查找对应物流信息，然后显示在表上
             *************************************************************************/
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

    public Delivery searchDelivery(int id) throws Exception {
        Reply reply = (Reply) Client.sendRequest(new Request(8, new Delivery(id)));
        if (reply.hasSucceed()) {
            return (Delivery) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }
}
