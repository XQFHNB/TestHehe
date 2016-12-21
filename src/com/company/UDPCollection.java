package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by XQF on 2016/12/20.
 */


public class UDPCollection extends JFrame implements ActionListener {


    public static final String FILE = "D:\\logger.txt";

    private JTextField sendMsg;//信息内容文本框
    private JTextArea receivedMsg;//接收消息显示区
    private JButton sendBtn;
    private JButton sendFileBtn;
    //    private JButton receiveFileBtn;
    private Container container;
    private Model model;

    private JFileChooser jfc;
    private byte[] buffer;

    private Thread t;
    private Thread t1;

    private String titleString;
    private int inPort;
    private int outPort;
    private InetAddress group = null;
    private MulticastSocket socket = null;
    //private static OnLinePeople onLinePeople;
    private static ArrayList list = new ArrayList();

    public UDPCollection(Model model) {
        this.model = model;
        this.setVisible(true);
        this.setTitle(model.getTitleString());
        this.setSize(600, 450);
        list.add(model.getTitleString());
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        //  onLinePeople = OnLinePeople.getOnLine();
        //   addOnlineName(model.getTitleString());
        container = this.getContentPane();
        jfc = new JFileChooser();
        container.setLayout(new BorderLayout());//给顶层容器的默认布局换成BorderLayout布局,不过顶层容器的默认布局就是这个呀，简直多此一举


// 添加滑动面板，也就是中间的消息界面
        JScrollPane centerPanel = new JScrollPane();//新建滑动面板对象
        receivedMsg = new JTextArea();
        centerPanel.setViewportView(receivedMsg);//把文本编辑区放进滚动面板
        // JScrollPanel centerPanel=new JScrollPanel(receivedMsg)
        container.add(centerPanel, BorderLayout.CENTER);//把滚动面板放在窗口最中间

// 添加底部面板，也就是编辑消息和发送消息按钮的面板
        JPanel bottomPanel = new JPanel();
        JLabel label = new JLabel("编辑信息");//创建一个标签提示栏对象
        sendMsg = new JTextField(25);//创建一个文本编辑框用于编辑消息，大小为20为列数，。大概也是指的宽度了。
        sendBtn = new JButton("发送消息");
        sendFileBtn = new JButton("发送文件");
//        receiveFileBtn = new JButton("接收文件");
        bottomPanel.add(label);
        bottomPanel.add(sendMsg);
        //  bottomPanel.add(receiveFileBtn);
        bottomPanel.add(sendBtn);
        bottomPanel.add(sendFileBtn);
        container.add(bottomPanel, BorderLayout.SOUTH);

//下面对事件进行处理
        sendFileBtn.addActionListener(this);
        sendBtn.addActionListener(this);//注册点击按钮事件
        sendMsg.addActionListener(this);//注册聊天栏动作事件

// 新开一个线程用于接收数据
        new Mythread().start();
        new Mythread1().start();

//点击退出就退出
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            group = InetAddress.getByName("230.198.112.0");
            socket = new MulticastSocket(2018);
            socket.joinGroup(group);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static JTable getTable() {
        Vector allVector = new Vector();
        Vector headVector = new Vector();
        String content;
        String[] list = null;
        headVector.add("用户");
        headVector.add("状态");
        //    OnLinePeople onLinePeople = OnLinePeople.getOnLine();
        //ArrayList list = (ArrayList) onLinePeople.getNameOnline();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(UDPCollection.FILE));
            StringBuffer sb = new StringBuffer();
            String stringContent;
            while ((stringContent = br.readLine()) != null) {
                sb.append(stringContent);
            }
            br.close();
            content = sb.toString();
            list = content.split("a");
            for (int i = 0; i < list.length; i++) {
                System.out.println("list:" + list[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < list.length; i++) {

            if (list[i].trim().equals("")) {
                System.out.println("frnigjt5iubnyut");

                return null;
            }
            System.out.println("result:" + list[i]);
            Vector vector = new Vector();
            vector.add(list[i]);
            vector.add("在线");
            allVector.add(vector);


        }
        JTable table = new JTable(allVector, headVector);
        return table;
    }

    //点击事件处理
    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton) event.getSource();


        InetAddress destAddress = null;//先拿到一个主机地址，创建一个InetAddress对象，这个对象在后续创建DatagramPacket有用
        try {
            destAddress = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            System.out.println("找不到主机！" + e.getMessage());
        }
        //创建套接字对象，准备打开管子放水了,这个类的构造方法还是比价简单，此时是发送就不需要绑定端口，所以使用这种构造方法

        DatagramSocket sendSocket = null;
        try {
            sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("套接字创建错误！");
        }

        if (button == sendBtn) {

            buffer = ("a" + sendMsg.getText().trim()).getBytes();
            if (buffer.length == 0) {
                JOptionPane.showMessageDialog(null, "发送消息不能为空", "提示", JOptionPane.CLOSED_OPTION);
                return;
            }
            //构造数据包一直都在用这个方法，相对来说简单，把端口，大小什么的都说明了,
            //将buff.length()大小的数据buff发送到目的地址为destAddress的2012端口处


            DatagramPacket dataPacket = new DatagramPacket(buffer, buffer.length, destAddress, model.getOutPort());
            //发送数据报
            try {
                sendSocket.send(dataPacket);
            } catch (IOException e) {
                System.out.println("数据报发送错误！");
            }


            receivedMsg.append("我：" + sendMsg.getText().trim() + "\n");
            //将消息编辑界面置空，看上去是不见了
            sendMsg.setText("");
        } else {
            int select = jfc.showOpenDialog(this);
            if (select == JFileChooser.APPROVE_OPTION) {


                File file = jfc.getSelectedFile();
                String fileName = file.getName();

//                int temp=0;
                try {
//                 这样完美
                    StringBuffer sb = new StringBuffer();
                    String string;
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    while ((string = br.readLine()) != null) {
                        sb.append(string);
                    }


                    buffer = (sb.toString() + "end").getBytes();

                    ProgressBar proBar = new ProgressBar();
                    proBar.setValue(50);


                    for (int i = 0; i < buffer.length; i++) {


                        if (!proBar.isStop()) {

                            System.out.println("proBar:" + proBar.isStop());
                            //wait();
                        }
//                        } else {
//                            notify();
//                        }
                        byte[] newBuffer = {buffer[i]};
                        proBar.setValue((i / buffer.length) * 100);
                        DatagramPacket dataPacket = new DatagramPacket(newBuffer, newBuffer.length, destAddress, model.getOutPort());
                        //发送数据报
                        try {
                            sendSocket.send(dataPacket);
                        } catch (IOException e) {
                            System.out.println("数据报发送错误！");
                        }

                    }


                    receivedMsg.append("我：发送文件完成\n");
                    // TODO: 2016/12/20 对文件内容进行分析


//                    FileInputStream fis = new FileInputStream(file);
                    //又学到一招，厉害，好像这种方式不行，。有点复杂，，干脆先变成字符串来解决
//                    byte[] by = new byte[1024];
//                    while((temp=fis.read(by))!=-1){
//
//                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


    class Mythread extends Thread {
        //子线程要干的事情,接收数据
        public void run() {
            DatagramPacket receivedPacket = null;
            DatagramSocket receivedSocket = null;
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[8192];

            try {

                //发送的时候使用四个参数的构造方法，接收的时候使用两个参数的构造方法
                receivedPacket = new DatagramPacket(buffer, buffer.length);
                //接收的Socket与端口绑定，就是为了接收这个端口的数据
                receivedSocket = new DatagramSocket(model.getInPort());

                //多播相关


            } catch (SocketException e) {
                e.printStackTrace();
            }
            while (true) {
                //如果套接字为空就跳出死循环,这里很奇怪，既然套接字里面都不为空了为什么在后面才来获取数据包
                if (receivedSocket == null) {
                    break;
                } else {
                    try {
                        receivedSocket.receive(receivedPacket);//接收数据，这句过后receivedPacket就不是空的了
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int length = receivedPacket.getLength();//获取内容长度只能在数据包里获取，不能在套接字，。，。套接字只是一条路。
//                InetAddress address = receivedPacket.getAddress();//拿到此套接字连接的地址，即获取发送人的地址
//                int port = receivedSocket.getPort();//获取发送者发送数据的端口号

                    //将获取到的数据转换为字符串
                    String message = new String(receivedPacket.getData(), 0, length);//查了一下API文档，里面的String真的有这个构造方法

                    if (message.charAt(0) == 'a') {
                        message = message.substring(1);
                        if (model.getTitleString().equals(UDPHostOne.HOSTNAME1)) {

                            receivedMsg.append(UDPHostTwo.HOSTNAME2 + ": " + message.toString() + "\n");
                        } else {
                            receivedMsg.append(UDPHostOne.HOSTNAME1 + ": " + message.toString() + "\n");

                        }
                    } else {
                        sb.append(message);
//                        receivedMsg.append("文件内容：" + message);
//                        if (sb.toString().endsWith("end")) {

                        receivedMsg.append("收到文件：" + sb.toString() + "\n");
//                        }
                    }

                }
            }
        }
    }


    class Mythread1 extends Thread {
        @Override
        public void run() {
            while (true) {
                if (socket == null) {
                    break;
                } else {
                    byte[] buff = new byte[8192];
                    DatagramPacket packet = new DatagramPacket(buff, buff.length, group, 2018);
                    try {
                        socket.receive(packet);
                        String message = new String(packet.getData(), 0, packet.getLength());
                        receivedMsg.append(model.getTitleString() + "收到：" + message.toString() + "\n");
                        System.out.println(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
