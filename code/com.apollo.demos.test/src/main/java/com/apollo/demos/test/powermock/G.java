/*
 * �˴��봴���� 2015��11��21�� ����3:56:00��
 */
package com.apollo.demos.test.powermock;

public class G {

    private static final G s_instance = new G();

    public static G getInstance() {
        return s_instance;
    }

    public String getMessage() {
        return "This is G.";
    }

}
