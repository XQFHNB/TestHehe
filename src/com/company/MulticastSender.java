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
public class MulticastSender extends JFrame implements ActionListener {
    int port = 2018;
    InetAddress group = null;
    MulticastSocket multiSocket = null;
    private JButton sendBtn;
    private JButton sendFileBtn;
    private JButton refreshBtn;
    private JTextField sendMsg;
    private JTextArea textArea;

    public MulticastSender() {
        this.setTitle("主管");
        this.setSize(600, 450);
        this.setVisible(true);
        Container container = this.getContentPane();

        JPanel topPanel = new JPanel(new GridLayout(2, 1));


        JLabel label = new JLabel("当前在线(点击刷新)");
        refreshBtn = new JButton("刷新");
        JPanel labelPanel = new JPanel(new GridLayout(1, 2));
        labelPanel.add(label);
        labelPanel.add(refreshBtn);
        refreshBtn.addActionListener(this);


        JTable table = UDPCollection.getTable();
        topPanel.add(labelPanel);
        topPanel.add(table);
        container.add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        JScrollPane centerPanel = new JScrollPane();//新建滑动面板对象
        centerPanel.setViewportView(textArea);//把文本编辑区放进滚动面板
        // JScrollPanel centerPanel=new JScrollPanel(receivedMsg)
        container.add(centerPanel, BorderLayout.CENTER);//把滚动面板放在窗口最中间
        JPanel bottomPanel = new JPanel();
        JLabel label1 = new JLabel("系统消息");//创建一个标签提示栏对象
        sendMsg = new JTextField(25);//创建一个文本编辑框用于编辑消息，大小为20为列数，。大概也是指的宽度了。
        sendBtn = new JButton("发送消息");
        sendFileBtn = new JButton("发送文件");
        sendBtn.addActionListener(this);
        sendFileBtn.addActionListener(this);
//        receiveFileBtn = new JButton("接收文件");
        bottomPanel.add(label1);
        bottomPanel.add(sendMsg);
        //  bottomPanel.add(receiveFileBtn);
        bottomPanel.add(sendBtn);
        bottomPanel.add(sendFileBtn);
        container.add(bottomPanel, BorderLayout.SOUTH);

        try {
            group = InetAddress.getByName("230.198.112.0");
            multiSocket = new MulticastSocket(port);
            multiSocket.setTimeToLive(1);

            multiSocket.joinGroup(group);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == refreshBtn) {
            this.setVisible(false);
            new MulticastSender();
        }
        if (e.getSource() == sendBtn) {

            try {

                byte buff[] = sendMsg.getText().trim().getBytes();
                textArea.append("系统消息：" + sendMsg.getText().trim() + "\n");
                DatagramPacket packet = new DatagramPacket(buff, buff.length, group, port);
                //   System.out.println(new String(buff));
                multiSocket.send(packet);
                sendMsg.setText("");
                //  sleep(2000);

            } catch (Exception e1) {
                System.out.println("Error: " + e1);
            }
        }
    }

    public static void main(String args[]) {
        new MulticastSender();
    }
}
