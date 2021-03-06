package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by XQF on 2016/12/14.
 */



public class ProgressBar implements ActionListener, ChangeListener {

    JFrame frame = null;

    JProgressBar progressbar;

    public JLabel getLabel() {
        return label;
    }

    JLabel label;

    Timer timer;

    JButton b;
    JButton c;

    private boolean isStop;

    public void setValue(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }

    public boolean isStop() {
        return isStop;
    }


    public ProgressBar() {

        this.isStop = true;
        frame = new JFrame("耐心等候。。。。。。");

        frame.setBounds(100, 100, 400, 130);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setResizable(false);

        Container contentPanel = frame.getContentPane();

        label = new JLabel("点击运行按钮开始", JLabel.CENTER);

        progressbar = new JProgressBar();

        progressbar.setOrientation(JProgressBar.HORIZONTAL);

        progressbar.setMinimum(0);

        progressbar.setMaximum(100);

        progressbar.setValue(0);

        progressbar.setStringPainted(true);

        progressbar.addChangeListener(this);

        progressbar.setPreferredSize(new Dimension(300, 20));

        progressbar.setBorderPainted(true);

        progressbar.setBackground(Color.pink);


        JPanel panel = new JPanel();

        b = new JButton("运行");
        c = new JButton("暂停");

        b.setForeground(Color.blue);

        b.addActionListener(this);


        c.setForeground(Color.blue);
        c.addActionListener(this);

        panel.add(b);
        panel.add(c);


        timer = new Timer(1000, this);


        contentPanel.add(panel, BorderLayout.NORTH);

        contentPanel.add(label, BorderLayout.CENTER);

        contentPanel.add(progressbar, BorderLayout.SOUTH);

        // frame.pack();

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b) {

            timer.start();


        }
        if (e.getSource() == c) {
            timer.stop();
            isStop = true;
        }

        if (e.getSource() == timer) {

            int value = progressbar.getValue();
            if (value < 100) {

                progressbar.setValue(value += 5);
                progressbar.setValue(value);
            } else {

                timer.stop();

                frame.dispose();

            }

        }


    }


    public void stateChanged(ChangeEvent e1) {

        int value = progressbar.getValue();

        if (e1.getSource() == progressbar) {

            label.setText("目前已完成进度：" + Integer.toString(value) + "%");

            label.setForeground(Color.blue);

        }


    }


    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (Exception e) {

            Logger.getLogger(ProgressBar.class.getName()).log(Level.FINE,

                    e.getMessage());

            e.printStackTrace();

        }
        new ProgressBar();

    }


}