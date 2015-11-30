/*
 * �˴��봴���� 2013-4-17 ����09:35:41��
 */
package com.apollo.demos.concurrent;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) {
        final int threadSum = 100; //�߳���
        final CountDownLatch readyGate = new CountDownLatch(threadSum); //Ԥ����ʼ����
        final CountDownLatch startGate = new CountDownLatch(1); //��ʼ����
        final CountDownLatch endGate = new CountDownLatch(threadSum); //��ֹ����

        for (int i = 0; i < threadSum; i++) {
            new Thread(new Runnable() {

                /**
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "׼����");

                    readyGate.countDown();

                    try {
                        startGate.await();

                        try {
                            System.out.println(Thread.currentThread().getName() + "��ʼ��");

                            Thread.sleep(3000);

                        } finally {
                            System.out.println(Thread.currentThread().getName() + "������");
                            endGate.countDown();
                        }

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

            }).start();
        }

        try {
            long startTime = System.currentTimeMillis();

            readyGate.await();

            System.out.println("��ʼ���Ѵ򿪣�");

            startGate.countDown();

            endGate.await();

            System.out.println("�������Ѵ򿪣��Ѻķ�ʱ�䣺" + (System.currentTimeMillis() - startTime) + "����");

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

}
