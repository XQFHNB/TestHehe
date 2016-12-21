package com.company;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by XQF on 2016/12/20.
 */
class MyAddTimerTask extends TimerTask {
    int num = 1;

    public void run() {
        num++;
        System.out.println("num:" + num);
    }
}

class MySubTimerTask extends TimerTask {
    int num = 20;

    public void run() {
        num--;
    }
}

public class RuntimeTest {
    public static void main(String[] args) {
        Timer myTimer = new Timer();
        MyAddTimerTask myAddTimerTask = new MyAddTimerTask();
        MySubTimerTask mySubTimerTask = new MySubTimerTask();
        myTimer.schedule(myAddTimerTask, 1, 1);

        // myTimer.schedule(mySubTimerTask, 1, 1);这样的输出结果为9，真是大跌眼镜，判断的时间段内是相等的，输出的时候线程又执行了
        //  myTimer.schedule(mySubTimerTask, 2, 2);
//        while (true) {
//            if (Math.abs(myAddTimerTask.num - mySubTimerTask.num) == 10) {
//                System.out.println(Math.abs(myAddTimerTask.num - mySubTimerTask.num));
//                System.out.println(myAddTimerTask.num + "       " + mySubTimerTask.num);
//                myTimer.cancel();
//                System.out.println(myAddTimerTask.num + "       " + mySubTimerTask.num);
//                break;
//            }
//        }
    }
}