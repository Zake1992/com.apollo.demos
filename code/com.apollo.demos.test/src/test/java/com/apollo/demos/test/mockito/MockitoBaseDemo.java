/*
 * �˴��봴���� 2015��11��19�� ����5:05:10��
 */
package com.apollo.demos.test.mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.internal.util.reflection.Whitebox.setInternalState;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class MockitoBaseDemo {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * ��֤������Ϊ��
     */
    @Test
    public void test01() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        mock.add(1);
        mock.clear();

        verify(mock).add(1);
        verify(mock).clear();
    }

    /**
     * һ����ʵ�Ķ���ʱ���ܽ�����֤�ġ�
     */
    @Test
    @Ignore
    public void test01_01() {
        List<Integer> mock = new ArrayList<Integer>();

        mock.add(1);
        mock.clear();

        verify(mock).add(1); //һ����ʵ�Ķ���ʱ���ܽ�����֤�ġ�
        verify(mock).clear();
    }

    /**
     * ��ӵĵ���Ҳ����֤��������Ҫ�ܹ���ӵ��á�
     */
    @Test
    public void test02() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(ArrayList.class);
        when(mock.contains(1)).thenCallRealMethod();

        mock.contains(1);

        verify(mock).contains(1);
        verify(mock).indexOf(1);
    }

    /**
     * ��ӵ��ò�û��ʵ�ʵ��õ���
     */
    @Test
    @Ignore
    public void test02_01() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(ArrayList.class);

        mock.contains(1);

        verify(mock).contains(1);
        verify(mock).indexOf(1); //containsû�н���when�����ã����������ʵ�ķ������Ͳ�����õ�indexOf�ˡ�
    }

    /**
     * �ӿ�û����ʵ�ĵ���ʵ�֡�
     */
    @Test
    @Ignore
    public void test02_02() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);
        when(mock.contains(1)).thenCallRealMethod(); //List�ǽӿڣ�û����ʵ����������ᱨ��

        mock.contains(1);

        verify(mock).contains(1);
        verify(mock).indexOf(1);
    }

    /**
     * ʹ��spy������Ա���ʹ��when����Ԥ����Ϊ��
     */
    @Test
    public void test03() {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> spy = spy(list);

        spy.contains(1); //spy�������з���ȱʡ���ǵ�����ʵ�����ġ�

        verify(spy).contains(1);
        verify(spy).indexOf(1);
    }

    /**
     * Ҫ������ʵ����mock�����Լ�spy���������
     */
    @Test
    public void test04() {
        List<Integer> list = new ArrayList<Integer>();
        List<Integer> spy = spy(list);

        assertEquals(0, spy.size());

        spy.add(1);

        assertEquals(1, spy.size());
        assertEquals(Integer.valueOf(1), spy.get(0));
    }

    /**
     * mock�������з���ȱʡʲô�������򷵻�null��ȱʡֵ�ȡ�
     */
    @Test
    @Ignore
    public void test04_01() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(ArrayList.class);

        assertEquals(0, mock.size());

        mock.add(1); //mock�������з���ȱʡʲô�������򷵻�null��ȱʡֵ�ȡ�

        assertEquals(1, mock.size());
        assertEquals(Integer.valueOf(1), mock.get(0));
    }

    /**
     * ģ�������������Ľ����
     */
    @Test
    public void test05() {
        @SuppressWarnings("unchecked")
        Iterator<Integer> mock = mock(Iterator.class);

        when(mock.next()).thenReturn(1, 2, 3); //Ԥ�赱iterator����next()ʱ��һ�η���1���ڶ��η���2���������۶��ٴζ�����3
        //when(mock.next()).thenReturn(1).thenReturn(2).thenReturn(3); //��ôдҲ���ԣ��������һ����

        assertEquals(Integer.valueOf(1), mock.next());
        assertEquals(Integer.valueOf(2), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
    }

    /**
     * �˽�when�Ļ�������
     */
    @Test
    @Ignore
    public void test05_01() {
        @SuppressWarnings("unchecked")
        Iterator<Integer> mock = mock(Iterator.class);

        when(mock.next()).thenReturn(1); //��Ч��
        when(mock.next()).thenReturn(2); //��Ч��
        when(mock.next()).thenReturn(3); //�����ֿ�д�Ͳ����ˣ����һ��Ḳ��ǰ������ã�ʹǰ�����Ч��

        assertEquals(Integer.valueOf(1), mock.next());
        assertEquals(Integer.valueOf(2), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
        assertEquals(Integer.valueOf(3), mock.next());
    }

    /**
     * ģ���쳣�׳���
     * @throws IOException
     */
    @Test(expected = IOException.class)
    public void test06() throws IOException {
        OutputStream mock = mock(OutputStream.class);

        doThrow(new IOException()).when(mock).close(); //Ԥ�赱���ر�ʱ�׳��쳣��

        mock.close();
    }

    /**
     * ����when����ͨ��������ȷƥ��ģ�ⲻͬ�����������
     */
    @Test
    public void test07() {
        @SuppressWarnings("unchecked")
        Comparable<String> mock = mock(Comparable.class);

        when(mock.compareTo("a")).thenReturn(1);
        when(mock.compareTo("b")).thenReturn(2);

        assertEquals(1, mock.compareTo("a"));
        assertEquals(2, mock.compareTo("b"));
        assertEquals(0, mock.compareTo("c")); //����û��Ԥ�������᷵��Ĭ��ֵ��
    }

    /**
     * ���˾�ȷƥ���⣬������ƥ���Լ���Ҫ�����������
     */
    @Test
    public void test08() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        Matcher<Integer> isValid = new ArgumentMatcher<Integer>() {

            @Override
            public boolean matches(Object argument) {
                Integer i = (Integer) argument;
                return i == 1 || i == 2;
            }

        };

        when(mock.get(anyInt())).thenReturn(1); //ƥ����������� 
        when(mock.contains(argThat(isValid))).thenReturn(true);

        assertEquals(Integer.valueOf(1), mock.get(0));
        assertEquals(Integer.valueOf(1), mock.get(1));
        assertEquals(Integer.valueOf(1), mock.get(999));
        assertTrue(mock.contains(1));
        assertFalse(mock.contains(3));
    }

    /**
     * ��Ҫע����������ʹ���˲���ƥ�䣬��ô���еĲ���������ͨ��matchers��ƥ�䡣
     */
    @Test
    public void test09() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        mock.compare("a", "b");

        verify(mock).compare(anyString(), eq("b")); //�����ʹ���˲���ƥ�䣬��ô���еĲ���������ͨ��matchers��ƥ�䡣
    }

    /**
     * ���������Ͳ��С�
     */
    @Test
    @Ignore
    public void test09_01() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        mock.compare("a", "b");

        verify(mock).compare(anyString(), "b"); //��Ч�Ĳ���ƥ��ʹ�á�
    }

    /**
     * when�Ĳ���ƥ�����Ҳ��һ������������ͬʱ����ƥ��;�ȷ�����Ҿ�ȷ���ȡ�
     */
    @Test
    public void test10() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        when(mock.compare(anyString(), eq("b"))).thenReturn(1);
        when(mock.compare("c", "b")).thenReturn(2); //when�Ĳ���ƥ�����Ҳ��һ������������ͬʱ����ƥ��;�ȷ�����Ҿ�ȷ���ȡ�

        assertEquals(1, mock.compare("a", "b"));
        assertEquals(2, mock.compare("c", "b"));
    }

    /**
     * ������������ͬ�����С�
     */
    @Test
    @Ignore
    public void test10_01() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        when(mock.compare(anyString(), "b")).thenReturn(1); //��Ч�Ĳ���ƥ��ʹ�á�
        when(mock.compare("c", "b")).thenReturn(2);

        assertEquals(1, mock.compare("a", "b"));
        assertEquals(2, mock.compare("c", "b"));
    }

    /**
     * ����ƥ����ʹ����Ҫע����verify�в��ܷ�����д��when���޴����ơ�
     */
    @Test
    @Ignore
    public void test10_02() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        int anyInt = anyInt();
        when(mock.get(anyInt)).thenReturn(999); //����anyInt������дû�����⡣

        assertEquals(Integer.valueOf(999), mock.get(10));

        anyInt = anyInt();
        verify(mock).get(anyInt); //����anyInt�����л����Mockito�������˳��Ҫ��д��һ���С�
        //verify(mock).get(anyInt()); //�������������⣬Matchers��verify�ж��ϸ�Ҫ������д����
    }

    /**
     * ��֤ȷ�еĵ��ô�����
     */
    @Test
    public void test11() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        mock.add(1);
        mock.add(2);
        mock.add(2);
        mock.add(3);
        mock.add(3);
        mock.add(3);

        verify(mock).add(1); //��֤�Ƿ񱻵���һ�Σ���Ч�������times(1)��
        verify(mock, times(1)).add(1); //������֤���Է���ִ�ж�Ρ�

        verify(mock, times(2)).add(2); //��֤�Ƿ񱻵���2�� ��

        verify(mock, times(3)).add(3); //��֤�Ƿ񱻵���3�Ρ�

        verify(mock, never()).add(4); //��֤�Ƿ��δ�����ù���

        verify(mock, atLeastOnce()).add(1); //��֤���ٵ���һ�Ρ�

        verify(mock, atLeast(2)).add(2); //��֤���ٵ���2�Ρ�

        verify(mock, atMost(3)).add(3); //��֤�������3�Ρ�
    }

    /**
     * ��ִ֤��˳��
     */
    @Test
    public void test12() {
        @SuppressWarnings("unchecked")
        List<Integer> mock1 = mock(List.class);
        @SuppressWarnings("unchecked")
        List<String> mock2 = mock(List.class);

        mock1.add(1);
        mock2.add("a");
        mock1.add(2);
        mock2.add("b");

        //����Ҫ�����mock�������InOrder��  
        InOrder inOrder = inOrder(mock1, mock2);

        //����Ĵ��벻�ܵߵ�˳����ִ֤��˳��  
        inOrder.verify(mock1).add(1);
        inOrder.verify(mock2).add("a");
        inOrder.verify(mock1).add(2);
        inOrder.verify(mock2).add("b");
    }

    /**
     * ȷ��ģ��������޻���������
     */
    @Test
    public void test13() {
        @SuppressWarnings("unchecked")
        List<Integer> mock1 = mock(List.class);
        @SuppressWarnings("unchecked")
        List<Integer> mock2 = mock(List.class);
        @SuppressWarnings("unchecked")
        List<Integer> mock3 = mock(List.class);

        mock1.add(1);

        verify(mock1).add(1);
        verify(mock1, never()).add(2);

        verifyZeroInteractions(mock2, mock3); //��֤�㻥����Ϊ��ע�⣺���ﲻ��ָmock2��mock3֮���л�����ã�����ָtest13����mock2��mock3û���κη��������á�
    }

    /**
     * �ҳ�����Ļ�������δ����֤���ģ���
     */
    @Test(expected = NoInteractionsWanted.class)
    public void test14() {
        @SuppressWarnings("unchecked")
        List<Integer> mock1 = mock(List.class);
        mock1.add(1);
        mock1.add(2);
        verify(mock1, times(2)).add(anyInt());
        verifyNoMoreInteractions(mock1); //����Ƿ���δ����֤�Ļ�����Ϊ����Ϊadd(1)��add(2)���ᱻ�����anyInt()��֤������������Ĵ����ͨ����

        @SuppressWarnings("unchecked")
        List<Integer> mock2 = mock(List.class);
        mock2.add(1);
        mock2.add(2);
        verify(mock2).add(1);
        verifyNoMoreInteractions(mock2); //����Ƿ���δ����֤�Ļ�����Ϊ����Ϊadd(2)û�б���֤����������Ĵ����ʧ���׳��쳣��
    }

    /**
     * ʹ�ûص���������ֵ��
     */
    @Test
    public void test15() {
        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class);

        Answer<Integer> answer = new Answer<Integer>() {

            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                int index = (int) args[0];
                return index;
            }

        };
        when(mock.get(anyInt())).thenAnswer(answer); //ʹ��Answer�������������������ķ��ء�

        assertEquals(Integer.valueOf(0), mock.get(0));
        assertEquals(Integer.valueOf(1), mock.get(1));
        assertEquals(Integer.valueOf(999), mock.get(999));
    }

    /**
     * �����ʵ����ʹ��spy�������ʵ�Ķ�����Ҫע����Ǵ�ʱ������Ҫ������ʹ��when-then��䣬������do-when��䡣
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test16() {
        List<Integer> list = new LinkedList<Integer>();
        List<Integer> spy = spy(list);

        //when(spy.get(999)).thenReturn(999); //����Ԥ���spy.get(0)�ᱨ����Ϊ�������ʵ�����get(0)�����׳�Խ���쳣��  
        doReturn(999).when(spy).get(999); //ʹ��doReturn-when���Ա���when-thenReturn������ʵ����

        //when(spy.size()).thenReturn(100); //Ԥ��size()����ֵ����������ͬ���������ʵ�����size()��ֻ�����������ʲô����Ӱ�졣
        doReturn(100).when(spy).size(); //����spy�������ַ�ʽ��д��

        //���涼�������ʵ�����ʵ�ʷ�����
        spy.add(1);
        spy.add(2);

        assertEquals(100, spy.size());
        assertEquals(Integer.valueOf(1), spy.get(0));
        assertEquals(Integer.valueOf(2), spy.get(1));
        assertEquals(Integer.valueOf(999), spy.get(999));

        verify(spy).add(1);
        verify(spy).add(2);

        spy.get(2); //���ﻹ�ǻ���IndexOutOfBoundsException������
    }

    /**
     * �޸Ķ�δԤ��ĵ��÷���Ĭ������ֵ��
     */
    @Test
    public void test17() {
        Answer<Object> defaultAnswer = new Answer<Object>() {

            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return 999; //������ʾ����ܼ򵥴ֱ�����ʵ���Answer��Ҫ����mock��������з��������Բ�����Object����Ӧ���в�������ͬ�������صĲ������Ϳ��ܶ���һ����
            }

        };

        @SuppressWarnings("unchecked")
        List<Integer> mock = mock(List.class, defaultAnswer); //mock����ʹ��Answer����δԤ��ĵ��÷���Ĭ������ֵ��

        assertEquals(Integer.valueOf(999), mock.get(1)); //get(1)û��Ԥ�裬ͨ������»᷵��NULL������ʹ����Answer�ı���Ĭ������ֵ��
        assertEquals(999, mock.size()); //size()û��Ԥ�裬ͨ������»᷵��0������ʹ����Answer�ı���Ĭ������ֵ��
    }

    /**
     * �����������һ�����ԡ�
     */
    @Test
    public void test18() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        String[] strings = new String[] { "a", "b" };
        Arrays.sort(strings, mock);

        ArgumentCaptor<String> argument1 = ArgumentCaptor.forClass(String.class); //Captor�������any����any��CaptorĿǰû�У��ٷ�˵�������ῼ�����ӣ���ʵĿǰ�Լ�дMatcher���Ը㶨��
        ArgumentCaptor<String> argument2 = ArgumentCaptor.forClass(String.class);
        verify(mock).compare(argument1.capture(), argument2.capture()); //ע�⣺�Ȿ�����һ����֤��ֻ������֤������˳���ռ�������

        assertEquals("b", argument1.getValue());
        assertEquals("a", argument2.getValue());
    }

    /**
     * �������������һ�����ԡ�
     */
    @Test
    public void test19() {
        @SuppressWarnings("unchecked")
        Comparator<String> mock = mock(Comparator.class);

        String[] strings = new String[] { "a", "b", "c", "d" };
        Arrays.sort(strings, mock);

        ArgumentCaptor<String> argument1 = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> argument2 = ArgumentCaptor.forClass(String.class);
        verify(mock, times(3)).compare(argument1.capture(), argument2.capture());

        List<String> argument1Values = argument1.getAllValues(); //������Ҫ�ر�ע�⣺Captor�ظ�ʹ�û��ۼ��ռ�������û����Mock������reset������Ԥ�ƺ���Ӧ�û�����reset���ܡ�
        assertEquals("b", argument1Values.get(0));
        assertEquals("c", argument1Values.get(1));
        assertEquals("d", argument1Values.get(2));

        List<String> argument2Values = argument2.getAllValues();
        assertEquals("a", argument2Values.get(0));
        assertEquals("b", argument2Values.get(1));
        assertEquals("c", argument2Values.get(2));
    }

    /**
     * ����mock��
     */
    @Test
    @SuppressWarnings("unchecked")
    public void test20() {
        List<Integer> mock = mock(List.class);

        when(mock.size()).thenReturn(10);
        mock.add(1);
        assertEquals(10, mock.size());
        verify(mock).add(1);

        reset(mock); //����mock��������еĻ�����Ԥ�衣
        assertEquals(0, mock.size());
        verify(mock, never()).add(1);
    }

    /**
     * spy�����doNothing�����þͺ�mock�����thenCallRealMethod���ƣ������������෴��
     * doNothing��
     */
    @Test
    public void test21() {
        A spy = spy(new A());

        doNothing().when(spy).doNotInvoke(); //mock����thenCallRealMethod��Ӵ�mock�Ѷȣ�������spy��˵������doNothing��ʱ���޷���ɲ����ˡ�
        spy.doNotInvoke();
        verify(spy).doNotInvoke();
    }

    /**
     * returnsFirstArg��
     */
    @Test
    public void test22() {
        @SuppressWarnings("unchecked")
        Map<String, String> mock = mock(Map.class);

        when(mock.get(anyString())).then(returnsFirstArg());

        assertEquals("a", mock.get("a"));
        assertEquals("b", mock.get("b"));
        assertEquals("c", mock.get("c"));

        verify(mock, times(3)).get(anyString());
    }

    /**
     * setInternalState���о��õĵط����Ǻܶ࣬�ı��ڲ�״̬һ�㲻��ֱ��mock���ýӿ�����ֱ�ӡ�
     */
    @Test
    public void test23() {
        B spy = spy(new B());
        setInternalState(spy, "m_value", 999); //���Ƶ�����java�ķ���ӿڣ��ӽӿ�ע���Ͽ�������ֻ�������ֶΣ��ǲ���˽������ν��
        assertEquals(999, spy.getValue());
        verify(spy).getValue();
    }

    /**
     * ��ȷ���Ͳ�����ע�⣺���ﲻ�����䣬���仹����Ҫ�Լ�дMatcher��
     */
    @Test
    @SuppressWarnings("unchecked")
    public void test24() {
        C mock = mock(C.class);

        mock.process(Arrays.asList("a", "b"));
        mock.process(Arrays.asList("c", "d", "e"));
        mock.process(Arrays.asList("f"));
        mock.process(Collections.unmodifiableCollection(Arrays.asList("g", "h")));
        mock.process(Collections.unmodifiableCollection(Arrays.asList("i", "j", "k")));

        verify(mock, times(3)).process(any(List.class)); //���Ƽ�������д�޷�ָ���������ͣ����и����棬������������⣬��Ϊjava�ķ���ǩ�����Է��Ͳ�������ʶ��
        verify(mock, times(3)).process(Matchers.<List<String>> any()); //any����ֵ�������в������ģ����г�ͻ������¾���Ҫ��ȷָ����
        verify(mock, times(3)).process(anyListOf(String.class)); //���������Ч��һ���������������о����ԡ�

        verify(mock, times(2)).process(any(Collection.class));
        verify(mock, times(2)).process(Matchers.<Collection<String>> any());
        verify(mock, times(2)).process(anyCollectionOf(String.class));
    }

}
