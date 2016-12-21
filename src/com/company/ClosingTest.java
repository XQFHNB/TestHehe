package com.company;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by XQF on 2016/12/21.
 */
public class ClosingTest {

    public ClosingTest() {
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setSize(200, 500);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.out.println("关闭之前");
            }
        });
    }

    public static void main(String[] args) {
        new ClosingTest();
    }
}
