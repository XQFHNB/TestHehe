package com.company;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

/**
 * Created by XQF on 2016/12/21.
 */
class MyTask implements Runnable {
    private Logger logger;
    private String string;

    public MyTask(String string) {
        logger = Logger.getLogger();
        this.string = string;
    }

    @Override
    public void run() {
        logger.writeToLogFile(string);
    }
}

public class Logger {
    private static Logger logger;
    private RandomAccessFile loggerFile;

    private Logger() {
        try {
            //使用随机文件的方法写入
            this.loggerFile = new RandomAccessFile("D:\\logger.txt", "rw");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Logger getLogger() {
        if (logger == null) {
            logger = new Logger();
        }
        return logger;

    }

    public void writeToLogFile(String string) {
        try {
            loggerFile.seek(loggerFile.length());

            //    loggerFile.writeUTF(string);
            loggerFile.write(string.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //唯一的实例
        Logger logger = Logger.getLogger();
        String string = "20162213202020---写入";
        // String s = new String(string.getBytes(), "utf-8");
        logger.writeToLogFile(string);

        for (int i = 0; i < 3; i++) {

            try {
                Random random = new Random(47);
                int a = random.nextInt(1000);
                Thread.sleep(a);
                new Thread(new MyTask("this is the No" + i + " thread!\n")).start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
