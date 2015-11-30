/*
 * �˴��봴���� 2015��6��5�� ����8:55:02��
 */
package com.apollo.demos.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class SingletonDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*
         * �����м��λ�������´���
         * ��ʼ����Singleton1...
         *
         * Singleton1���ִ���
         * 0.com.apollo.demos.concurrent.Singleton1@3d4eac69
         * 1.com.apollo.demos.concurrent.Singleton1@42a57993
         * 2.com.apollo.demos.concurrent.Singleton1@42a57993
         * 3.com.apollo.demos.concurrent.Singleton1@42a57993
         * 4.com.apollo.demos.concurrent.Singleton1@42a57993
         *
         * ��������Singleton1��
         */
        Singleton1.test();
        //Singleton2.test();
        //Singleton3.test();
    }

}

/*
 * һ�㳣���ĵ��������߳��»��д���
 */
class Singleton1 {

    static Singleton1 s_singleton = null;

    static Singleton1 getSingleton() {
        if (s_singleton == null) {
            s_singleton = new Singleton1();
        }

        return s_singleton;
    }

    static void test() throws InterruptedException, ExecutionException {
        System.out.println("\n��ʼ����Singleton1...");

        List<Future<Singleton1>> results = null;

        for (int i = 0; i < 1000; i++) {
            Singleton1.s_singleton = null;
            results = test(Runtime.getRuntime().availableProcessors() + 1);
            if (results != null) {
                System.out.println("\nSingleton1���ִ���");
                for (int j = 0, size = results.size(); j < size; j++) {
                    System.out.println(j + "." + results.get(j).get());
                }
            }
        }

        System.out.println("\n��������Singleton1��\n");
    }

    static List<Future<Singleton1>> test(int nThreads) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        CountDownLatch readyGate = new CountDownLatch(nThreads); //׼������
        CountDownLatch startGate = new CountDownLatch(1); //��ʼ����

        List<Future<Singleton1>> results = new ArrayList<Future<Singleton1>>();

        for (int i = 0; i < nThreads; i++) {
            results.add(executor.submit(getTask(readyGate, startGate)));
        }

        try {
            readyGate.await(); //�ȴ������߳�׼�����

            startGate.countDown(); //�����߳̿�ʼ����

            Singleton1 first = results.get(0).get();
            for (Future<Singleton1> result : results) {
                Singleton1 current = result.get();
                if (first != current) {
                    return results;
                }
            }

            return null;

        } finally {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
    }

    static Callable<Singleton1> getTask(final CountDownLatch readyGate, final CountDownLatch startGate) {
        return new Callable<Singleton1>() {

            @Override
            public Singleton1 call() throws Exception {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return Singleton1.getSingleton();
            }

        };
    }

}

/*
 * ˫�ؼ�����ģʽ��DCL������������֤���Ƿ�ģʽ�����м�����ɻ�ȡ��һ����ʼ��һ��Ķ���TODO Ŀǰδ����Ч�Ĵ���֤�������⡣
 */
class Singleton2 {

    static Singleton2 s_singleton = null;

    static Singleton2 getSingleton() {

        if (s_singleton == null) {
            synchronized (Singleton2.class) {
                if (s_singleton == null) {
                    s_singleton = new Singleton2();
                }
            }
        }

        return s_singleton;
    }

    static void test() throws InterruptedException, ExecutionException {
        System.out.println("\n��ʼ����Singleton2...");

        List<Future<Singleton2>> results = null;

        for (int i = 0; i < 1000; i++) {
            Singleton2.s_singleton = null;
            results = test(Runtime.getRuntime().availableProcessors() + 1);
            if (results != null) {
                System.out.println("\nSingleton2���ִ���");
                for (int j = 0, size = results.size(); j < size; j++) {
                    System.out.println(j + "." + results.get(j).get());
                }
            }
        }

        System.out.println("\n��������Singleton2��\n");
    }

    static List<Future<Singleton2>> test(int nThreads) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        CountDownLatch readyGate = new CountDownLatch(nThreads); //׼������
        CountDownLatch startGate = new CountDownLatch(1); //��ʼ����

        List<Future<Singleton2>> results = new ArrayList<Future<Singleton2>>();

        for (int i = 0; i < nThreads; i++) {
            results.add(executor.submit(getTask(readyGate, startGate)));
        }

        try {
            readyGate.await(); //�ȴ������߳�׼�����

            startGate.countDown(); //�����߳̿�ʼ����

            Singleton2 first = results.get(0).get();
            for (Future<Singleton2> result : results) {
                Singleton2 current = result.get();
                if (first != current) {
                    return results;
                }
            }

            return null;

        } finally {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
    }

    static Callable<Singleton2> getTask(final CountDownLatch readyGate, final CountDownLatch startGate) {
        return new Callable<Singleton2>() {

            @Override
            public Singleton2 call() throws Exception {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return Singleton2.getSingleton();
            }

        };
    }

}

/*
 * �ӳٳ�ʼ������ȫ��ȫ�ķ�ʽ��TODO �����޷����ó�ʼ���࣬Ŀǰ�޷���Ч���ڵ���������֤��
 */
class Singleton3 {

    private static final class Singleton3Holder {

        static Singleton3 s_singleton = new Singleton3();

    }

    static Singleton3 getSingleton() {
        return Singleton3Holder.s_singleton;
    }

    static void test() throws InterruptedException, ExecutionException {
        System.out.println("\n��ʼ����Singleton3...");

        List<Future<Singleton3>> results = null;

        for (int i = 0; i < 1; i++) {
            //����û�����ã�ֻ��1��ѭ��
            results = test(Runtime.getRuntime().availableProcessors() + 1);
            if (results != null) {
                System.out.println("\nSingleton3���ִ���");
                for (int j = 0, size = results.size(); j < size; j++) {
                    System.out.println(j + "." + results.get(j).get());
                }
            }
        }

        System.out.println("\n��������Singleton3��\n");
    }

    static List<Future<Singleton3>> test(int nThreads) throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);

        CountDownLatch readyGate = new CountDownLatch(nThreads); //׼������
        CountDownLatch startGate = new CountDownLatch(1); //��ʼ����

        List<Future<Singleton3>> results = new ArrayList<Future<Singleton3>>();

        for (int i = 0; i < nThreads; i++) {
            results.add(executor.submit(getTask(readyGate, startGate)));
        }

        try {
            readyGate.await(); //�ȴ������߳�׼�����

            startGate.countDown(); //�����߳̿�ʼ����

            Singleton3 first = results.get(0).get();
            for (Future<Singleton3> result : results) {
                Singleton3 current = result.get();
                if (first != current) {
                    return results;
                }
            }

            return null;

        } finally {
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
    }

    static Callable<Singleton3> getTask(final CountDownLatch readyGate, final CountDownLatch startGate) {
        return new Callable<Singleton3>() {

            @Override
            public Singleton3 call() throws Exception {
                readyGate.countDown();

                try {
                    startGate.await();

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return Singleton3.getSingleton();
            }

        };
    }

}
