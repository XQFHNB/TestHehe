package com.company;

/**
 * Created by XQF on 2016/12/20.
 */
public class Model {
    private String titleString;
    private int inPort;
    private int outPort;

    public Model(String titleString, int inPort, int outPort) {
        this.titleString = titleString;
        this.inPort = inPort;
        this.outPort = outPort;
    }

    public String getTitleString() {
        return titleString;
    }

    public int getInPort() {
        return inPort;
    }


    public int getOutPort() {
        return outPort;
    }
}
