/*
 * �˴��봴���� 2013-4-17 ����04:36:08��
 */
package com.apollo.demos.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {

    public static void main(String[] args) {
        final int threadSum = 10;
        final CyclicBarrier barrier = new CyclicBarrier(threadSum, new Runnable() {

            /**
             * @see java.lang.Runnable#run()
             */
            @Override
            public void run() {
                System.out.println("դ���Ѵ򿪣�");
            }

        });

        for (int i = 0; i < threadSum; i++) {
            new Thread(new Runnable() {

                /**
                 * @see java.lang.Runnable#run()
                 */
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "��������");

                    try {
                        Thread.sleep(3000);

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + "�ѵ���դ����");

                    try {
                        barrier.await();

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();

                    } catch (BrokenBarrierException ex) {
                        ex.printStackTrace();
                    }
                }

            }).start();
        }
    }

}
