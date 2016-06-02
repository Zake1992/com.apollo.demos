/*
 * �˴��봴���� 2016��6��2�� ����10:55:06��
 */
package com.apollo.demos.osgi.app.adapter.api;

public final class AdapterUtilities implements IAdapterConstants {

    public static final String showClass(Object object) {
        return object == null ? "null" : object.getClass().getName();
    }

    public static final String showId(Object object) {
        return object == null ? "null" : ("0x" + Integer.toHexString(System.identityHashCode(object)));
    }

    /**
     * ���췽����
     */
    private AdapterUtilities() {
        /* ��ֹ���ⲿʵ�������� */
    }

}
