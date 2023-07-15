package com.gaohongsen;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

import static com.gaohongsen.MainWindow.*;

public class LogPanel extends JPanel {

    public LogPanel(MainWindow mainWindow) {
        setLayout(null);







        // 这是一个示例表，待开发。。。。。。。
        String[] columnNames = new String[]{"序号", "用户名", "姓名", "时间", "请求类型"};
        String[][] logistics = new String[][]{
                {"1", "Sun", "张三", "2022/12/31 23:59:59", "0"},
                {"2", "Moon", "李四", "2022/12/31 23:59:59", "1"},
                {"3", "Earth", "王五", "2022/12/31 23:59:59", "3"}};
        JTable table = new JTable(logistics, columnNames);
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 100, 720, 300);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // 设置垂直滚动条一直显示
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // 设置水平滚动条从不显示
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane);

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