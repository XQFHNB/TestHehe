package com.company;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

/**
 * Created by XQF on 2016/12/20.
 */
public class UDPHostOne {
    public static final String HOSTNAME1 = "主机1";

    private String content;
    private String newContent;

    public UDPHostOne(Model model) {

        UDPCollection collection = new UDPCollection(model);
        try {
            BufferedReader br = new BufferedReader(new FileReader(UDPCollection.FILE));
            StringBuffer sb = new StringBuffer();
            String stringContent;
            while ((stringContent = br.readLine()) != null) {
                sb.append(stringContent);
            }
            br.close();
            content = sb.toString().trim();
            newContent = model.getTitleString() + "a";
            content = content + newContent;
            PrintWriter pw = new PrintWriter(new FileWriter(UDPCollection.FILE));
            pw.write(content);
            pw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        collection.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);

                try {
                    BufferedReader br = new BufferedReader(new FileReader(UDPCollection.FILE));
                    StringBuffer sb = new StringBuffer();
                    String stringContent;
                    while ((stringContent = br.readLine()) != null) {
                        sb.append(stringContent);
                    }
                    br.close();
                    content = sb.toString().trim();
                    content = content.replace(HOSTNAME1+"a", "");
                    PrintWriter pw = new PrintWriter(new FileWriter(UDPCollection.FILE));
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

        Model model = new Model(HOSTNAME1, 2012, 2016);
        new UDPHostOne(model);
    }

}
