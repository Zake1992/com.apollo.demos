/*
 * �˴��봴���� 2016��6��2�� ����10:12:38��
 */
package com.apollo.demos.osgi.app.message.api;

public final class MessageUtilities implements IMessageConstants {

    public static final String showClass(Object object) {
        return object == null ? "null" : object.getClass().getName();
    }

    public static final String showId(Object object) {
        return "0x" + Integer.toHexString(System.identityHashCode(object));
    }

    /**
     * ���췽����
     */
    private MessageUtilities() {
        /* ��ֹ���ⲿʵ�������� */
    }

}
