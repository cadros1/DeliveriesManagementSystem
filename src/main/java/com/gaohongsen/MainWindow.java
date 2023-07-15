package com.gaohongsen;

/*
 @file MainWindow
  * @brief 实现图形化界面和交互逻辑
 * @author 李祖怡
 * @date 2023/7/15
 */

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;


public class MainWindow extends JFrame {
    public static CardLayout cardLayout;
    public static JPanel contentPane;

    public static User user=new User(0,"0","0",0);


    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setResizable(false);

        // 创建内容面板和CardLayout
        contentPane = new JPanel();
        cardLayout = new CardLayout();
        contentPane.setLayout(cardLayout);
        // 创建登录和注册面板
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);

        // 添加面板到内容面板
        contentPane.add(loginPanel, "login");
        contentPane.add(registerPanel, "register");

        // 设置初始显示的面板为登录面板
        cardLayout.show(contentPane, "login");

        // 设置内容面板为窗体的内容面板
        setContentPane(contentPane);

        // 显示窗体
        setVisible(true);
    }

    public void showLoginPanel() {

    }

    public void showRegisterPanel() {cardLayout.show(contentPane, "register");}

    public void showMainAppPanel() {
        cardLayout.show(contentPane, "mainApp");
    }



    public static void main(String[] args) {
        MainWindow mainWindow= new MainWindow();
    }
}