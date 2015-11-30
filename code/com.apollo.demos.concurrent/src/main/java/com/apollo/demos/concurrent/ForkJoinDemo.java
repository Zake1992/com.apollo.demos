/*
 * �˴��봴���� 2013-8-4 ����3:35:56��
 */
package com.apollo.demos.concurrent;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinDemo {

    protected static class FileCounterTask extends RecursiveTask<Integer> {

        private static final long serialVersionUID = 1L;

        private final File m_file;

        public FileCounterTask(File file) {
            m_file = file;
        }

        /**
         * @see java.util.concurrent.RecursiveTask#compute()
         */
        @Override
        protected Integer compute() {
            try {
                Thread.sleep(1);

            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            int fileCount = 0;

            if (m_file == null) {
                return fileCount;
            }

            if (m_file.isDirectory()) {
                List<FileCounterTask> childTasks = new ArrayList<FileCounterTask>();

                for (File child : m_file.listFiles()) {
                    FileCounterTask childTask = new FileCounterTask(child);
                    childTask.fork();
                    childTasks.add(childTask);
                }

                for (FileCounterTask childTask : childTasks) {
                    fileCount += childTask.join();
                }

            } else {
                fileCount++;
            }

            return fileCount;
        }

    }

    public static void main(String[] args) {
        String dir = "e:/etc";

        Date start = new Date();
        System.out.println("��ʼForkJoin���̵߳ݹ����\"" + dir + "\"...");

        ForkJoinTask<Integer> fjt = new FileCounterTask(new File(dir));
        ForkJoinPool fjp = new ForkJoinPool();

        fjp.submit(fjt);

        try {
            System.out.println("\"" + dir + "\"" + "��" + fjt.get() + "���ļ���");

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds.");

        } catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        fjp.shutdown();

        start = new Date();
        System.out.println("\n��ʼ���̵߳ݹ����\"" + dir + "\"...");

        System.out.println("\"" + dir + "\"" + "��" + getFileCount(new File(dir)) + "���ļ���");

        Date end = new Date();
        System.out.println(end.getTime() - start.getTime() + " total milliseconds.");
    }

    protected static int getFileCount(File file) {
        try {
            Thread.sleep(1);

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        int fileCount = 0;

        if (file == null) {
            return fileCount;
        }

        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                fileCount += getFileCount(child);
            }

        } else {
            fileCount++;
        }

        return fileCount;
    }

}
