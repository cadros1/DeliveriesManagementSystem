package com.gaohongsen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.util.Vector;

import static com.gaohongsen.MainWindow.*;
import static com.gaohongsen.MainWindow.user;

public class LogPanel extends JPanel {

    public LogPanel(MainWindow mainWindow) {
        setLayout(null);

        JButton exportExcelButton = new JButton("导出EXCEL");
        exportExcelButton.setBounds(600,50,120,25);
        add(exportExcelButton);
        exportExcelButton.addActionListener(e -> {
                    try {
                        exportExcel(0);
                        JOptionPane.showMessageDialog(null, "导出成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
        );

        // 表格上的title
        Vector<String> vh = new Vector<>();
        vh.add("序号");
        vh.add("日志号");
        vh.add("用户名");
        vh.add("姓名");
        vh.add("时间");
        vh.add("请求类型");

        Vector<Log> logs;
        try {
            logs = displayLog(0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Vector<Vector<Object>> vd = new Vector<>();
        for (int i = 0; i < logs.size(); i++) {
            addLine(i, logs, vd);
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

        JButton logButton = new JButton("日志");
        logButton.setBounds(220, 20, 90, 25);
        add(logButton);
        logButton.addActionListener(e -> {
                    if (user.getPermission() == 1||user.getPermission()==2)
                        JOptionPane.showMessageDialog(null, "权限不足！", "错误", JOptionPane.ERROR_MESSAGE);
                    else cardLayout.show(contentPane, "log");
                }
        );

        JButton alterButton = new JButton("删改物流");
        alterButton.setBounds(320, 20, 90, 25);
        add(alterButton);
        alterButton.addActionListener(e -> {
                    if (user.getPermission() == 1)
                        JOptionPane.showMessageDialog(null, "权限不足！", "错误", JOptionPane.ERROR_MESSAGE);
                    else cardLayout.show(contentPane, "alter");
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


    public Vector<Log> displayLog(int id) throws Exception {
        Reply reply = (Reply) Client.sendRequest(new Request(9, new Log(id)));
        if (reply.hasSucceed()) {
            return (Vector<Log>) reply.getItem();
        } else {
            throw (Exception) reply.getItem();
        }
    }

    public void exportExcel(int id) throws Exception{
        Reply reply = (Reply) Client.sendRequest(new Request(10,new Log(id)));
        if (!reply.hasSucceed())
            throw (Exception) reply.getItem();
    }

    public void addLine(int line, Vector<Log> logs, Vector<Vector<Object>> vd) {
        Vector<Object> v = new Vector<>();
        Log d = logs.get(logs.size() - line - 1);
        v.add(String.valueOf(line + 1));
        v.add(String.valueOf(d.getId()));
        v.add(d.getAccount());
        v.add(d.getName());
        v.add(d.getDatetime());
        v.add(d.getTypeString());
        vd.add(v);
    }
}