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
    int port; // �鲥�Ķ˿�
    InetAddress group = null; // �鲥������ַ.
    MulticastSocket socket = null; // �ಥ�׽���.
    JButton startButton; // ��ʼ���հ�ť
    JButton stopButton; // ֹͣ���ܰ�ť
    JButton clearButton; // ֹͣ���ܰ�ť
    JTextArea currentMsg; // ��ǰ���յĹ㲥��Ϣ
    JTextArea receivedMsg; // �Ѿ��յ��Ĺ㲥��Ϣ
    Thread thread; // ����������Ϣ���߳�
    boolean isStop = false; //ֹͣ���չ㲥��Ϣ��ʶ


    public MulticastReceiver() {
        setTitle("测试"); // ���ô��ڵı���
        Container container = this.getContentPane(); // ��ǰ���ڵ���������
        startButton = new JButton("开始"); // ������ʼ���հ�ť����
        stopButton = new JButton("ֹͣ停止"); // ����ֹͣ���հ�ť����
        clearButton = new JButton("清空"); // ����������Ϣ��ť����
        stopButton.addActionListener(this); // ע�Ὺʼ���հ�ť�ļ�����
        startButton.addActionListener(this); // ע��ֹͣ���հ�ť�ļ�����
        clearButton.addActionListener(this); // ע��������Ϣ��ť�ļ�����
        currentMsg = new JTextArea(3, 20); // ����һ��3��20�еĶ����ı���
        currentMsg.setForeground(Color.red); // �����ı�������ɫΪ��ɫ
        receivedMsg = new JTextArea(8, 20); // ����һ��8��20�еĶ����ı��򣬲�ʹ��Ĭ�ϵĺ�ɫ
        container.setLayout(new BorderLayout()); // ���ô��������Ĳ���ΪBorderLayout
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // ����һ��ˮƽ�ָ���������
        JScrollPane currScrollPanel = new JScrollPane(); // ����һ����������
        currScrollPanel.setViewportView(currentMsg); // �ڹ��������Ϸ���currentMsg����
        JScrollPane recvScrollPanel = new JScrollPane(); // ����һ����������
        recvScrollPanel.setViewportView(receivedMsg); // �ڹ��������Ϸ���receivedMsg����
        currentMsg.setEditable(false); // ����currentMsg���ɱ༭
        receivedMsg.setEditable(false); // ����receivedMsg���ɱ༭
        sp.add(currScrollPanel); // �ڷ�������������currScrollPanel����
        sp.add(recvScrollPanel); // �ڷ�������������recvScrollPanel����
        container.add(sp, BorderLayout.CENTER); // ��sp�����ŵ����ڵ�������
        JPanel bottomPanel = new JPanel(); // �����ײ�����
        bottomPanel.add(startButton); // ����ʼ���հ�ť�����ײ�����
        bottomPanel.add(stopButton); // ��ֹͣ���հ�ť�����ײ�����
        bottomPanel.add(clearButton); // ��������Ϣ��ť�����ײ�����
        container.add(bottomPanel, BorderLayout.SOUTH); // ���ײ��������봰�ڵײ���
        setSize(500, 400); // ���ô��ڴ�С
        setVisible(true); // ���ô��ڿ���
        thread = new Thread(this); // �����̶߳���
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���ô����˳���ʽ
        port = 2018; // �����鲥���ļ����˿�
        try {
            group = InetAddress.getByName("230.198.112.0"); // ���öಥ���ĵ�ַΪ230.198.112.0
            socket = new MulticastSocket(port); // �����ಥ�׽��֣���port�˿ڽ����鲥

            socket.joinGroup(group);
        } catch (Exception e) {
        }
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) { // ������ʼ��ť������
            startButton.setEnabled(false); // ����ʼ��ť��Ϊ������״̬
            stopButton.setEnabled(true); // ��ֹͣ��ťΪ����״̬
            if (!(thread.isAlive())) { // �����߳�����������ֹͣ���հ�ť������
                thread = new Thread(this); // ���´����̶߳���
            }
            try {
                thread.start(); // �����߳�
                isStop = false; // ��ֹͣ���ձ�־��Ϊ��
            } catch (Exception ee) {
            }
        }
        if (e.getSource() == stopButton) { // ����ֹͣ��ť������
            startButton.setEnabled(true); // ����ʼ��ť��Ϊ����״̬
            stopButton.setEnabled(false); // ��ֹͣ��ťΪ������״̬
            isStop = true; // ��ֹͣ���ձ�־��Ϊ��
        }
        if (e.getSource() == clearButton) { // ����ֹͣ��ť������
            receivedMsg.setText(""); // ��receivedMsg����������
        }

    }


    public void run() {
        while (true) {
            byte buff[] = new byte[8192]; // �������Է��Ͷ˵�����
            DatagramPacket packet = null; // �������ݱ�����
            packet = new DatagramPacket(buff, buff.length, group, port); // ׼�����ݰ���
            try {
                socket.receive(packet); // �������ݰ�
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

