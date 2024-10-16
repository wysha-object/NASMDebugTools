package cn.com.wysha;

import cn.com.wysha.GUI.MainGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        MainGUI mainGUI=new MainGUI();
        mainGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainGUI.setVisible(true);
    }
}