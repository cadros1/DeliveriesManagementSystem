package com.gaohongsen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.Vector;

import static com.gaohongsen.MainWindow.cardLayout;
import static com.gaohongsen.MainWindow.contentPane;

public class SearchPanel extends JPanel {

    public SearchPanel() {
        setLayout(null);

        // 表格上的title
        Vector<String> vh = new Vector<>();
        vh.add("序号");
        vh.add("单号");
        vh.add("发货地");
        vh.add("收货地");
        vh.add("发件人");
        vh.add("收件人");
        vh.add("物流状态");

        Vector<Delivery> deliveries;
        try {
            deliveries = displayDelivery(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Vector<Vector<Object>> vd = new Vector<>();
        for (int i = 0; i < deliveries.size(); i++) {
            addLine(i, deliveries, vd);
        }


        DefaultTableModel model = new DefaultTableModel(vd, vh);
        JTable table = new JTable(model) {
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        table.setBounds(10, 100, 720, 300);

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
        if (resource != null) icon = new ImageIcon(resource);
        lblBackground.setIcon(icon); // 设置标签组件要显示的图标
        if (icon != null) lblBackground.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight()); // 设置组件的显示位置及大小
        add(lblBackground); // 将组件添加到面板中
    }

    public Vector<Delivery> displayDelivery(int id) throws Exception {
        Reply reply = (Reply) Client.sendRequest(new Request(8, new Delivery(id)));
        if (reply.hasSucceed()) {
            return (Vector<Delivery>) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }

    public void addLine(int line, Vector<Delivery> deliveries, Vector<Vector<Object>> vd) {
        Vector<Object> v = new Vector<>();
        Delivery d = deliveries.get(deliveries.size() - line - 1);
        v.add(String.valueOf(line + 1));
        v.add(String.valueOf(d.getId()));
        v.add(d.getSendPlace());
        v.add(d.getReceivePlace());
        v.add(d.getSender());
        v.add(d.getReceiver());
        v.add(d.getSituationString());
        vd.add(v);
    }
}
