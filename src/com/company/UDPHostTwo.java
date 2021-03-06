package com.company;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * Created by XQF on 2016/12/20.
 */
public class UDPHostTwo {
    public static final String HOSTNAME2 = "小熊";
    private String content;
    private String newContent;

    public UDPHostTwo(Model model) {
        UDPCollection collection = new UDPCollection(model);
        try {
            BufferedReader br = new BufferedReader(new FileReader(Config.FILE));
            StringBuffer sb = new StringBuffer();
            String stringContent;
            while ((stringContent = br.readLine()) != null) {
                sb.append(stringContent);
            }
            br.close();
            content = sb.toString().trim();
            newContent = model.getTitleString() + "a";
            content = content + newContent;
            PrintWriter pw = new PrintWriter(new FileWriter(Config.FILE));
            pw.write(content);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //关闭窗口的时候抹除登陆日志
        collection.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                try {
                    BufferedReader br = new BufferedReader(new FileReader(Config.FILE));
                    StringBuffer sb = new StringBuffer();
                    String stringContent;
                    while ((stringContent = br.readLine()) != null) {
                        sb.append(stringContent);
                    }
                    br.close();
                    content = sb.toString().trim();
                    // content = content.substring(0, content.length() - newContent.length());
                    content = content.replace(HOSTNAME2 + "a", "");
                    PrintWriter pw = new PrintWriter(new FileWriter(Config.FILE));
                    pw.write(content);
                    pw.close();

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    public static void main(String[] args) {

        Model model = new Model(HOSTNAME2, 2016, 2012);

        new UDPHostTwo(model);
    }
}