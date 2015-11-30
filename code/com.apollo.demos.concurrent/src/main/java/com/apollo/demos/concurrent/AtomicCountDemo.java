/*
 * �˴��봴���� 2013-2-17 ����03:56:08��
 */
package com.apollo.demos.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCountDemo {

    public static void main(String[] args) throws InterruptedException {
        //Count.count(); //��ͨ����i++�ļ���
        AtomicCount.count(); //ԭ�Ӳ����ļ���
    }

}

/**
 * ��ͨ�����ࡣ
 */
class Count {

    int m_count = 0;

    void increment() {
        m_count++;
    }

    /**
     * ���̼߳�����
     */
    static void count() {
        for (int i = 0; i < 10; i++) { //�ظ�10��
            final CountDownLatch startGate = new CountDownLatch(1); //��ʼ����
            final CountDownLatch endGate = new CountDownLatch(100); //��ֹ����

            final Count count = new Count(); //��ͨ����
            for (int j = 0; j < 100; j++) { //����100�������߳�
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            startGate.await(); //��ʼ��������

                            try {
                                for (int i = 0; i < 100; i++) { //����100������
                                    count.increment();
                                }

                            } finally {
                                endGate.countDown(); //��ֹ��������
                            }

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                }).start();
            }

            startGate.countDown(); //�����߳̿�ʼ����

            try {
                endGate.await(); //�����߳�ȫ���������
                System.out.println("��" + i + "�Σ�Count = " + count.m_count);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}

/**
 * ԭ�Ӽ����ࡣ
 */
class AtomicCount {

    AtomicInteger m_count = new AtomicInteger(0);

    void increment() {
        m_count.incrementAndGet();
    }

    /**
     * ���̼߳�����
     */
    static void count() {
        for (int i = 0; i < 10; i++) { //�ظ�10��
            final CountDownLatch startGate = new CountDownLatch(1); //��ʼ����
            final CountDownLatch endGate = new CountDownLatch(100); //��ֹ����

            final AtomicCount count = new AtomicCount(); //��ͨ����
            for (int j = 0; j < 100; j++) { //����100�������߳�
                new Thread(new Runnable() {

                    public void run() {
                        try {
                            startGate.await(); //��ʼ��������

                            try {
                                for (int i = 0; i < 100; i++) { //����100������
                                    count.increment();
                                }

                            } finally {
                                endGate.countDown(); //��ֹ��������
                            }

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }

                }).start();
            }

            startGate.countDown(); //�����߳̿�ʼ����

            try {
                endGate.await(); //�����߳�ȫ���������
                System.out.println("��" + i + "�Σ�AtomicCount = " + count.m_count.get());

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

}
