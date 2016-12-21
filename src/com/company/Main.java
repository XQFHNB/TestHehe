package com.company;

public class Main {

    public static int a = 0;

    public Main() {
        a++;
        System.out.println(a);
    }

    public static int getCount() {
        return a;
    }

    public static void main(String[] args) {
        // write your code here

        new Main();
        new Main();
        System.out.println(a);
        System.out.println(Main.a);

    }
}
