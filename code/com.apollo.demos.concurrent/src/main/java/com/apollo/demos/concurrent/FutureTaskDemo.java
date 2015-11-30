/*
 * �˴��봴���� 2013-4-17 ����09:14:22��
 */
package com.apollo.demos.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {

    /**
     * ���񣬺ķ����룬Ȼ�󷵻�һ���ַ�����
     */
    private static FutureTask<String> s_task = new FutureTask<String>(new Callable<String>() {

        /**
         * @see java.util.concurrent.Callable#call()
         */
        @Override
        public String call() throws Exception {
            System.out.println("�����߳̿�ʼ��");
            Thread.sleep(5000);
            System.out.println("�����߳̽�����");

            return "TestFutureTask";
        }

    });

    public static void main(String[] args) {
        new Thread(s_task).start();

        try {
            System.out.println("���̻߳�ȡ�� " + s_task.get());

        } catch (InterruptedException ex) {
            ex.printStackTrace();

        } catch (ExecutionException ex) {
            ex.printStackTrace();
        }
    }

}
