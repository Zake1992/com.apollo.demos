/*
 * �˴��봴���� 2015��11��21�� ����9:20:23��
 */
package com.apollo.demos.test.powermock;

public class A {

    private String getA(int n) {
        return "This is A. [n = " + n + "]";
    }

    public String getMessage(int n) {
        return getA(n);
    }

}
