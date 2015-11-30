/*
 * �˴��봴���� 2013-4-17 ����10:14:02��
 */
package com.apollo.demos.concurrent;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    /**
     * ����������
     * @return �������
     */
    private static String random() {
        return String.valueOf((int) (Math.random() * 1000));
    }

    public static void main(String[] args) {
        final BoundedSet<String> set = new BoundedSet<String>(10);

        new Thread(new Runnable() { //����̡߳�

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                while (true) {
                    try {
                        if (set.add(random())) {
                            System.out.println("��ӳɹ������ϴ�СΪ��" + set.size());
                            Thread.yield();
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }).start();

        new Thread(new Runnable() { //�Ƴ��̡߳�

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                while (true) {
                    if (set.remove((random()))) {
                        System.out.println("�Ƴ��ɹ������ϴ�СΪ��" + set.size());
                        Thread.yield();
                    }
                }
            }

        }).start();
    }

}

/**
 * �н缯�ϡ�
 */
class BoundedSet<T> {

    /**
     * �ź�����
     */
    private final Semaphore m_semaphore;

    /**
     * �޽缯�ϡ�
     */
    private final Set<T> m_set;

    /**
     * ���췽����
     * @param bound ��ֵ��
     */
    public BoundedSet(int bound) {
        m_semaphore = new Semaphore(bound);
        m_set = Collections.synchronizedSet(new HashSet<T>());
    }

    /**
     * ���һ������
     * @param object ����
     * @return ��ʶ����Ƿ�ɹ���
     * @throws InterruptedException ����ź���ʱ�������������ʱ���ж��ź����״��쳣��
     */
    public boolean add(T object) throws InterruptedException {
        m_semaphore.acquire(); //����ź��������û�о��������ȵ��������ź����ͷš�

        boolean wasAdded = false; //��ʶ�Ƿ��Ѿ���ӡ�

        try {
            wasAdded = m_set.add(object);

            return wasAdded;

        } finally {
            if (!wasAdded) { //���û����ӳɹ������ͷ�һ���ź�����
                m_semaphore.release();
            }
        }
    }

    /**
     * �Ƴ�һ������
     * @param object ����
     * @return ��ʶ�Ƴ������Ƿ�ɹ���
     */
    public boolean remove(Object object) {
        boolean wasRemoved = m_set.remove(object);

        if (wasRemoved) { //����Ƴ��ɹ������ͷ�һ���ź�����
            m_semaphore.release();
        }

        return wasRemoved;
    }

    /**
     * ���ϴ�С��
     * @return ���ϴ�С��
     */
    public int size() {
        return m_set.size();
    }

}
