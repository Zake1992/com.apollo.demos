/*
 * �˴��봴���� 2013-3-27 ����03:27:40��
 */
package com.apollo.demos.concurrent;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.apollo.base.util.BaseUtilities;
import com.apollo.base.util.IBaseConstants;
import com.apollo.swing.component.text.IntegerField;
import com.apollo.swing.laf.TLookAndFeelManager;

public class SwingWorkerDemo {

    public static void main(String[] args) {
        TLookAndFeelManager.loadAlloy(false);

        FindPrimeWindow window = new FindPrimeWindow();
        window.setVisible(true);
    }

}

/**
 * �����������ڡ�
 */
@SuppressWarnings("all")
class FindPrimeWindow extends JFrame implements IBaseConstants, PropertyChangeListener {

    /**
     * �ж��Ƿ���������ʱ�临�Ӷ�O(n)��
     * @param number ���֡�
     * @return ��ʶ�Ƿ���������
     */
    public static final boolean isPrime1(int number) {
        if (number < 2) {
            return false;
        }

        for (int i = 2; i < number; ++i) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * �ж��Ƿ����������Ľ���ȥ��ż�����жϣ�ʱ�临�Ӷ�O(n/2)���ٶ����һ����
     * @param number ���֡�
     * @return ��ʶ�Ƿ���������
     */
    public static final Boolean isPrime2(int number) {
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        for (int i = 3; i < number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * �ж��Ƿ�����������һ�������жϵķ�Χ��ʱ�临�Ӷ�O(sqrt(n)/2)���ٶ����O((n-sqrt(n))/2)��
     * <p>�������n������������n������1<d<=sqrt(n)��һ������d��</p>
     * <p>֤�������n�������������ɶ���n��һ������d����1<d<n��</p>
     * <p>���d����sqrt(n)����n/d������1<n/d<=sqrt(n)��һ�����ӡ�</p>
     * <p>�����Ч�㷨��鿴http://www.cnblogs.com/luluping/archive/2010/03/03/1677552.html��</p>
     * @param number ���֡�
     * @return ��ʶ�Ƿ���������
     */
    public static final boolean isPrime3(int number) {
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * ���������Ĺ����ߡ�
     */
    protected class FindPrimeWorker extends SwingWorker<List<Integer>, Integer> {

        /**
         * ��ǰ���֡�
         */
        protected int m_currentNumber;

        /**
         * ������
         */
        protected int m_sum;

        /**
         * ������
         */
        protected int m_count = 1;

        /**
         * ���췽����
         * @param startNumber ��ʼ���֡�
         * @param sum ������
         */
        public FindPrimeWorker(int startNumber, int sum) {
            m_currentNumber = startNumber;
            m_sum = sum;
            addPropertyChangeListener(FindPrimeWindow.this);
        }

        /**
         * @see javax.swing.SwingWorker#doInBackground()
         */
        @Override
        protected List<Integer> doInBackground() throws Exception {
            List<Integer> primes = new ArrayList<Integer>();

            while (!isCancelled() && primes.size() < m_sum) {
                while (!isPrime1(m_currentNumber++)) {
                }

                int prime = m_currentNumber - 1;
                primes.add(prime);

                publish(prime);
                setProgress(100 * primes.size() / m_sum);
            }

            return primes;
        }

        /**
         * @see javax.swing.SwingWorker#process(java.util.List)
         */
        @Override
        protected void process(List<Integer> chunks) {
            if (!isCancelled()) {
                for (Integer chunk : chunks) {
                    m_result.append(chunk + (m_count++ % 14 == 0 ? "\n" : "\t"));
                }
            }
        }

        /**
         * @see javax.swing.SwingWorker#done()
         */
        @Override
        protected void done() {
            List<Integer> primes = null;

            try {
                if (!isCancelled()) {
                    primes = get();
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();

            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }

            m_result.append(isCancelled() ? "��ȡ����" : "�Ѳ�����ϣ������� " + primes.size() + " ��������");

            removePropertyChangeListener(FindPrimeWindow.this);
            m_findPrimeWorker = null;
            constrain();
        }

    }

    /**
     * �����------��ʼ���֡�
     */
    protected IntegerField m_startNumber = new IntegerField(4, 0, 999999, 0);

    /**
     * �����------������
     */
    protected IntegerField m_sum = new IntegerField(4, 0, 999999, 10000);

    /**
     * ��ʾ����------�����
     */
    protected JTextArea m_result = new JTextArea();

    /**
     * ������------���ҽ��ȡ�
     */
    protected JProgressBar m_progress = new JProgressBar(0, 100);

    /**
     * ����------��ʼ��
     */
    protected Action m_start = new AbstractAction() {

        {
            BaseUtilities.setAction(this, I18N_INFO_START);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent evt) {
            start();
        }

    };

    /**
     * ����------ȡ����
     */
    protected Action m_cancel = new AbstractAction() {

        {
            BaseUtilities.setAction(this, I18N_INFO_CANCEL);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent evt) {
            cancel();
        }

    };

    /**
     * ����------�رա�
     */
    protected Action m_close = new AbstractAction() {

        {
            BaseUtilities.setAction(this, I18N_INFO_CLOSE);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent evt) {
            close();
        }

    };

    /**
     * ���������Ĺ����ߡ�
     */
    protected FindPrimeWorker m_findPrimeWorker = null;

    /**
     * ���췽����
     */
    public FindPrimeWindow() {
        super("��������");

        m_result.setEditable(false);
        JScrollPane resultView = new JScrollPane(m_result);
        resultView.setBorder(BorderFactory.createTitledBorder("���ҽ��"));

        m_progress.setStringPainted(true);
        m_progress.setBorder(BorderFactory.createTitledBorder("���ҽ���"));

        m_startNumber.setMinimumSize(m_startNumber.getPreferredSize());
        m_sum.setMinimumSize(m_sum.getPreferredSize());

        JPanel view = new JPanel(new GridBagLayout());
        BaseUtilities.addComponentWithBothFill(view, resultView, 0, 0, GBC_REMAINDER, 1, GBC_WEST);
        BaseUtilities.addComponentWithHorizontalFill(view, m_progress, 0, 1, GBC_REMAINDER, 1, GBC_WEST);
        BaseUtilities.addComponentWithNoSpace(view, new JLabel(), 0, 2, 1, 1, 1.0, 0.0, GBC_WEST, GBC_HORIZONTAL);
        BaseUtilities.addComponentWithNoneFill(view, new JLabel("��ʼ���֣�"), GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, m_startNumber, GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, new JLabel("����������"), GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, m_sum, GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, new JButton(m_start), GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, new JButton(m_cancel), GBC_RELATIVE, 2, GBC_EAST);
        JButton closeBtn = new JButton(m_close);
        BaseUtilities.addComponentWithNoneFill(view, closeBtn, GBC_RELATIVE, 2, GBC_EAST);

        setContentPane(view);
        rootPane.setDefaultButton(closeBtn);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));

        pack();
        BaseUtilities.center(this);

        constrain();
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            m_progress.setValue((Integer) evt.getNewValue());
        }
    }

    /**
     * @see javax.swing.JFrame#processWindowEvent(java.awt.event.WindowEvent)
     */
    protected void processWindowEvent(WindowEvent evt) {
        if (evt.getID() == WindowEvent.WINDOW_CLOSING) {
            close();
        }

        super.processWindowEvent(evt);
    }

    /**
     * Լ����
     */
    protected void constrain() {
        boolean hasFindPrimeWorker = m_findPrimeWorker != null;

        m_startNumber.setEnabled(!hasFindPrimeWorker);
        m_sum.setEnabled(!hasFindPrimeWorker);
        m_start.setEnabled(!hasFindPrimeWorker);
        m_cancel.setEnabled(hasFindPrimeWorker);
    }

    /**
     * ��ʼ��
     */
    protected void start() {
        if (m_findPrimeWorker != null) {
            m_findPrimeWorker.cancel(true);
        }

        try {
            m_startNumber.commitEdit();

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
            m_startNumber.requestFocusInWindow();
            return;
        }

        try {
            m_sum.commitEdit();

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "����", JOptionPane.ERROR_MESSAGE);
            m_sum.requestFocusInWindow();
            return;
        }

        m_result.setText("");
        m_result.append("��ʼ���ң�\n");

        m_findPrimeWorker = new FindPrimeWorker(m_startNumber.getInteger().intValue(), m_sum.getInteger().intValue());
        constrain();

        m_findPrimeWorker.execute();
    }

    /**
     * ȡ����
     */
    protected void cancel() {
        m_findPrimeWorker.cancel(true);
    }

    /**
     * �رա�
     */
    protected void close() {
        if (m_findPrimeWorker != null) {
            m_findPrimeWorker.cancel(true);
        }

        System.exit(0);
    }

}
