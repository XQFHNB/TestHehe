package com.company;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XQF on 2016/12/20.
 */
public class OnLinePeople {
    public static OnLinePeople onLine;
    private List<String> nameOnline;

    private OnLinePeople() {
        nameOnline = new ArrayList<>();
    }

    public static synchronized OnLinePeople getOnLine() {
        if (onLine == null) {
            onLine = new OnLinePeople();
            System.out.println("第一次");
        } else {
            System.out.println("第二次");
        }
        return onLine;
    }

    public void addName(String name) {
        nameOnline.add(name);
    }

    public List<String> getNameOnline() {
        return nameOnline;
    }

    public boolean isExists(String name) {
        if (nameOnline.isEmpty()) {
            return false;
        } else {
            for (int i = 0; i < nameOnline.size(); i++) {
                if (name.equals(nameOnline.get(i))) {
                    return true;
                }
            }
        }
        return false;
    }

}
