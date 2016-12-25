package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by XQF on 2016/12/20.
 */
public class MulticastReceiver extends JFrame implements Runnable, ActionListener {
    private static final long serialVersionUID = -5923790809266120014L;
    int port;
    InetAddress group = null;
    MulticastSocket socket = null;
    JButton startButton;
    JButton stopButton;
    JButton clearButton;
    JTextArea currentMsg;
    JTextArea receivedMsg;
    Thread thread;
    boolean isStop = false;


    public MulticastReceiver() {
        setTitle("测试");
        Container container = this.getContentPane();
        startButton = new JButton("开始");
        stopButton = new JButton("ֹͣ停止");
        clearButton = new JButton("清空");
        stopButton.addActionListener(this);
        startButton.addActionListener(this);
        clearButton.addActionListener(this);
        currentMsg = new JTextArea(3, 20);
        currentMsg.setForeground(Color.red);
        receivedMsg = new JTextArea(8, 20);
        container.setLayout(new BorderLayout());
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        JScrollPane currScrollPanel = new JScrollPane();
        currScrollPanel.setViewportView(currentMsg);
        JScrollPane recvScrollPanel = new JScrollPane();
        recvScrollPanel.setViewportView(receivedMsg);
        currentMsg.setEditable(false);
        receivedMsg.setEditable(false);
        sp.add(currScrollPanel);
        sp.add(recvScrollPanel);
        container.add(sp, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(startButton);
        bottomPanel.add(stopButton);
        bottomPanel.add(clearButton);
        container.add(bottomPanel, BorderLayout.SOUTH);
        setSize(500, 400);
        setVisible(true);
        thread = new Thread(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        port = 2018;
        try {
            group = InetAddress.getByName("230.198.112.0");
            socket = new MulticastSocket(port);

            socket.joinGroup(group);
        } catch (Exception e) {
        }
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            if (!(thread.isAlive())) {
                thread = new Thread(this);
            }
            try {
                thread.start();
                isStop = false;
            } catch (Exception ee) {
            }
        }
        if (e.getSource() == stopButton) {
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            isStop = true;
        }
        if (e.getSource() == clearButton) {
            receivedMsg.setText("");

        }
    }


    public void run() {
        while (true) {
            byte buff[] = new byte[8192];
            DatagramPacket packet = null;
            packet = new DatagramPacket(buff, buff.length, group, port);
            try {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet
                        .getLength());
                currentMsg.setText("广播消息" + message);
                receivedMsg.append(message + "\n");
            } catch (Exception e) {
            }
            if (isStop == true) {
                break;
            }
        }
    }


    public static void main(String args[]) {
        new MulticastReceiver();
    }
}

