/*
 * �˴��봴���� 2015��11��21�� ����10:53:44��
 */
package com.apollo.demos.test.powermock;

public class C {

    private static void throwException() {
        throw new RuntimeException("Do not invoke.");
    }

    public static void doNotInvoke() {
        throwException();
    }

}
