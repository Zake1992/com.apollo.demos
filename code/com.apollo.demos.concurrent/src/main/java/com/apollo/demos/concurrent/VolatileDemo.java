/*
 * �˴��봴���� 2013-2-17 ����03:10:08��
 */
package com.apollo.demos.concurrent;

public class VolatileDemo {

    public static void main(String[] args) {
        Visibility.test();
        //Synchronized.test();
        //Reordering.test();
    }

}

/**
 * �ɼ��ԣ�˵��volatile�����õĳ�����
 */
@SuppressWarnings("all")
class Visibility {

    /**
     * ��Ա����m_booleanValueʹ��volatile�Ͳ�ʹ��volatile������������ģ���������Ҫ���Լ��Σ�����֪������֮�������ġ�
     */
    static void test() {
        final Visibility visibility = new Visibility();

        new Thread(new Runnable() { //����������һ�����޽���value���ֵ���̡߳�

            @Override
            public void run() {
                System.out.println("�����߳�������");

                while (true) {
                    visibility.swap();
                    Thread.yield(); //��һ���ֹ������ѭ��̫ռCPU��
                }
            }

        }).start();

        int count = 0; //��������
        while (true) { //���޴�����֤�̣߳������Ҫ�Ļ���
            final int current = count++;

            Thread test = new Thread(new Runnable() {

                @Override
                public void run() {
                    System.out.println("��֤�߳���������" + current + "��");

                    while (visibility.isEqual()) { //��value�Լ����Լ����ʱ�˳��������̡߳�
                        Thread.yield(); //��һ���ֹ������ѭ��̫ռCPU��
                    }
                }

            });

            test.start(); //������֤�̡߳�

            try {
                test.join(); //�ȴ���֤�߳̽�����

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            Thread.yield(); //��һ���ֹ������ѭ��̫ռCPU��
        }
    }

    /**
     * ����volatile����ʱ����ӡ������ͣ��ĳ�ε���֤�̴߳���Ҳ������֤�̱߳���һ�λ�һֱ����m_value == m_value�����ܴ�ʱvalueֵ��Ƶ���Ľ������ֵ��
     * ��volatile����ʱ�򲻻ᣬ��֤�̻߳�һֱѭ����ӡ��ȥ��
     */
    volatile boolean m_value;

    /**
     * �ж�value�Ƿ����Լ���ȣ�����ȥ��Щ��֡�
     */
    boolean isEqual() {
        /*
         * ��������д���������value��use������use������JMMΪ�����ڴ�����ڴ��ͬ�������8������֮һ����
         * ���value�Ƿ�volatileʱ��JVM����֤ÿ��use����ǰ�������load������������֤ÿ��use������ʹ�����µ����ڴ��е�value��
         * ���value��volatileʱ��JVM�ᱣ֤ÿ��use����ǰ�������load����������֤ÿ��use������ʹ�����µ����ڴ��е�value��
         * ���ۣ�
         *     1.value�Ƿ�volatile�����߳�A��valueƵ���仯ʱ���߳�B�е�m_value == m_value����һֱ����true����Ϊ�п���valueһֱû�д����ڴ��и��¡�
         *     2.value��volatile�����߳�A��valueƵ���仯ʱ���߳�B�е�m_value == m_value������һֱ����true����Ϊ������value������ʵ����ǰ��2��ʱ�����ڴ���value��ֵ��
         */
        return m_value == m_value;
    }

    /**
     * ����value�����ֵ��
     */
    void swap() {
        m_value = !m_value; //����ͬ������isEqual()�е������ֻ�����Ѷ���������д������ԭ�����ơ�
    }

}

/**
 * ͬ����˵��volatile�������õĳ������޷����ͬ�����ơ�
 */
class Synchronized {

    /**
     * ������֤ab���������Ƿ���ڡ�
     */
    static void test() {
        while (true) { //������֤ab�����Ĳ��ԡ�
            final Synchronized sync = new Synchronized();

            final Thread ab = new Thread(new Runnable() { //����a=b�̡߳�

                @Override
                public void run() {
                    sync.ab();
                }

            });

            final Thread ba = new Thread(new Runnable() { //����b=a�̡߳�

                @Override
                public void run() {
                    sync.ba();
                }

            });

            ab.start();
            ba.start();

            new Thread(new Runnable() { //�������ab�������̡߳�

                @Override
                public void run() {
                    try {
                        ab.join();
                        ba.join();

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (sync.m_a != sync.m_b) { //��Ϊǰ���join�����ﲻ���й���a��b�����ݾ������������ڶ���߳��з��ʡ�
                        //���磺Thread-52829: a=2 b=1
                        System.out.println(Thread.currentThread().getName() + ": a=" + sync.m_a + " b=" + sync.m_b);
                        System.exit(0);
                    }
                }

            }).start();

            Thread.yield();
        }
    }

    /**
     * ����a,b�����Ƿ�ʹ��volatile���Σ�������� a��bֵ��������Ϊ����ľ�̬�����漰a��b����������volatile�������޷���֤ԭ���ԡ�
     * ����ֻ��ʹ��ͬ�����ܱ������ab����������������ab()��ba()ͬʱ���synchronized���Ρ�
     */
    int m_a = 1, m_b = 2;

    /**
     * a=b��
     */
    void ab() {
        m_a = m_b;
    }

    /**
     * b=a��
     */
    void ba() {
        m_b = m_a;
    }

}

/**
 * ������һ������������Ĵ�����֤������������β��ܳ��֡�
 */
class Reordering {

    /**
     * ������֤x == 0, y == 0�Ƿ���ڡ�
     */
    static void test() {
        while (true) { //������֤x == 0, y == 0�Ĳ��ԡ�
            final Reordering reordering = new Reordering();

            final Thread a1xb = new Thread(new Runnable() { //����a=1;x=b�̡߳�

                @Override
                public void run() {
                    reordering.a1xb();
                }

            });

            final Thread b1ya = new Thread(new Runnable() { //����b=1;y=a�̡߳�

                @Override
                public void run() {
                    reordering.b1ya();
                }

            });

            a1xb.start();
            b1ya.start();

            new Thread(new Runnable() { //�������x == 0, y == 0���̡߳�

                @Override
                public void run() {
                    try {
                        a1xb.join();
                        b1ya.join();

                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }

                    if (reordering.m_x == 0 && reordering.m_y == 0) { //��Ϊǰ���join�����ﲻ���й���x��y�����ݾ������������ڶ���߳��з��ʡ�
                        //���磺Thread-70898: x=0 y=0
                        System.out.println(Thread.currentThread().getName() + ": x=" + reordering.m_x + " y=" + reordering.m_y);
                        System.exit(0);
                    }
                }

            }).start();

            Thread.yield();
        }
    }

    /**
     * ��������ǡ�Java Concurrency in Practice��P279�����ӣ���Ϊ��֤��������Ĵ��ڣ�������x=0 y=0�Ľ��ʱ������֤����
     * ����Ϊ������ӻ��Ǵ�������ģ���Ϊ��JMM������������x=0 y=0�Ľ�����Ϊ�������ڴ�ͬ���ڴ��ͬ���ӳ١���Ϊ����
     */
    int m_a = 0, m_b = 0;

    int m_x = 0, m_y = 0;

    /**
     * a=1;x=b��
     */
    void a1xb() {
        m_a = 1;
        m_x = m_b;
    }

    /**
     * b=1;y=a��
     */
    void b1ya() {
        m_b = 1;
        m_y = m_a;
    }

}
