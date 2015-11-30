/*
 * �˴��봴���� 2015��11��20�� ����4:20:02��
 */
package com.apollo.demos.test.powermock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import com.apollo.demos.test.mockito.MyDictionary;

@RunWith(PowerMockRunner.class)
public class PowermockBaseDemo {

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
     * ģ��private�ķ�����
     * @throws Exception
     */
    @Test
    @PrepareForTest(A.class)
    public void test01() throws Exception {
        A spy = spy(new A()); //˽�з���һ��ʹ��spy����Ϊ����˽�з����Ĳ��ֶ����д���ʵ�ֵġ�

        doReturn("This is mock A.").when(spy, "getA", 1);
        assertEquals("This is mock A.", spy.getMessage(1)); //˽�з���һ��ʹ�ù��нӿڽ��м�Ӳ��ԡ�
        assertEquals("This is A. [n = 2]", spy.getMessage(2));

        doReturn("This is mock A.").when(spy, "getA", anyInt());
        assertEquals("This is mock A.", spy.getMessage(2));
        assertEquals("This is mock A.", spy.getMessage(3));

        verifyPrivate(spy, times(4)).invoke("getA", anyInt());
    }

    /**
     * ģ��static������
     * @throws Exception
     */
    @Test
    @PrepareForTest(B.class)
    public void test02() {
        mockStatic(B.class); //static����ֻ��mock������spy��
        when(B.getMessage(999)).thenReturn("This is mock B.");
        when(B.getMessage(888)).thenCallRealMethod();

        assertEquals("This is mock B.", B.getMessage(999));
        assertEquals("This is B. [n = 888]", B.getMessage(888));
        assertNull(B.getMessage(777));

        verifyStatic(times(3)); //������֤��Ŀ�꾲̬�����ǽ�������һ��֮��ľ�̬�������ã���2�����ɶԳ��֣���һ���ض�����
        B.getMessage(anyInt()); //��һ�䲻���������þ�̬���������Ǹ���̬������������
    }

    /**
     * ģ��private static������
     * @throws Exception
     */
    @Test
    @PrepareForTest(C.class)
    public void test03() throws Exception {
        mockStatic(C.class);
        doCallRealMethod().when(C.class, "throwException");
        doNothing().when(C.class, "doNotInvoke"); //��䲻��Ҳû�й�ϵ����Ϊmock����Ĭ�Ͼ���doNothing�ġ�

        try {
            C.doNotInvoke();

        } catch (RuntimeException ex) {
            fail("This is not invoked");
        }

        doCallRealMethod().when(C.class, "doNotInvoke");

        try {
            C.doNotInvoke();
            fail("This is not invoked");

        } catch (RuntimeException ex) {
        }

        verifyPrivate(C.class).invoke("throwException"); //��֤throwExceptionֻ������һ�Ρ�
        verifyStatic(times(2));
        C.doNotInvoke();
    }

    /**
     * ģ��final�ࡢfinal������final��̬������
     */
    @Test
    @PrepareForTest({ D1.class, D2.class, D3.class })
    public void test04() {
        D1 mockD1 = mock(D1.class);
        when(mockD1.getMessage(999)).thenReturn("This is mock D1.");
        assertEquals("This is mock D1.", mockD1.getMessage(999));
        verify(mockD1).getMessage(999);

        D2 mockD2 = mock(D2.class);
        when(mockD2.getMessage(888)).thenReturn("This is mock D2.");
        assertEquals("This is mock D2.", mockD2.getMessage(888));
        verify(mockD2).getMessage(888);

        mockStatic(D3.class);
        when(D3.getMessage(777)).thenReturn("This is mock D3.");
        assertEquals("This is mock D3.", D3.getMessage(777));
        verifyStatic();
        D3.getMessage(777);
    }

    /**
     * final����Mockito�����޷�ģ��ġ�
     */
    @Test
    @Ignore
    public void test04_01() {
        D1 mockD1 = Mockito.mock(D1.class);
        Mockito.when(mockD1.getMessage(999)).thenReturn("This is mock D1.");
        assertEquals("This is mock D1.", mockD1.getMessage(999));
        Mockito.verify(mockD1).getMessage(999);
    }

    /**
     * final������Mockito�����޷�ģ��ġ�
     */
    @Test
    @Ignore
    public void test04_02() {
        D2 mockD2 = Mockito.mock(D2.class);
        Mockito.when(mockD2.getMessage(888)).thenReturn("This is mock D2.");
        assertEquals("This is mock D2.", mockD2.getMessage(888));
        Mockito.verify(mockD2).getMessage(888);
    }

    /**
     * ��new��������mock��ע��@PrepareForTest����д���Ǳ�Ӱ����࣬�������Mock���ࡣ
     * @throws Exception
     */
    @Test
    @PrepareForTest(MyDictionary.class)
    public void test05() throws Exception {
        @SuppressWarnings("unchecked")
        HashMap<String, String> mock = mock(HashMap.class);

        whenNew(HashMap.class).withNoArguments().thenReturn(mock);
        when(mock.get("a")).thenReturn("b");

        MyDictionary myDictionary = new MyDictionary();
        assertEquals("b", myDictionary.getMeaning("a"));

        verifyNew(HashMap.class).withNoArguments();
    }

    /**
     * ���췽���ڲ��쳣������ʹ��whenNew����Ҫԭ��
     * @throws Exception
     */
    @Test
    @PrepareForTest(E.class)
    public void test06() throws Exception {
        E mock = mock(E.class);

        whenNew(E.class).withNoArguments().thenReturn(mock);

        assertSame(mock, new E());
        assertSame(mock, new E());
        assertSame(mock, new E());

        verifyNew(E.class, times(3)).withNoArguments();
    }

    /**
     * ��̬��ʼ�����쳣�ᵼ�������ʧ�ܣ�ֱ�����Ƶ���ĿǰΨһ�Ĵ�������
     * @throws Exception
     */
    @Test
    @PrepareForTest(F.class)
    @SuppressStaticInitializationFor("com.apollo.demos.test.powermock.F")
    public void test07() throws Exception {
        F mock = mock(F.class);

        whenNew(F.class).withNoArguments().thenReturn(mock);

        assertSame(mock, new F());
        assertSame(mock, new F());
        assertSame(mock, new F());

        verifyNew(F.class, times(3)).withNoArguments();
    }

    /**
     * verifyNew������û��mock������½��У������mockito�����Ƶġ�
     * @throws Exception
     */
    @Test
    @Ignore
    @PrepareForTest(F.class)
    @SuppressStaticInitializationFor("com.apollo.demos.test.powermock.F")
    public void test07_01() throws Exception {
        new F();
        new F();
        new F();

        verifyNew(F.class, times(3)).withNoArguments();
    }

    /**
     * verifyNew��ǰ����whenNew��ʱ��������mock��������ܲ����ռ������Ϣ����verify��
     * @throws Exception
     */
    @Test
    @Ignore
    @PrepareForTest(F.class)
    @SuppressStaticInitializationFor("com.apollo.demos.test.powermock.F")
    public void test07_02() throws Exception {
        F mock = mock(F.class);

        //whenNew(F.class).withNoArguments().thenReturn(mock);

        assertNotSame(mock, new F());
        assertNotSame(mock, new F());
        assertNotSame(mock, new F());

        verifyNew(F.class, times(3)).withNoArguments();
    }

    /**
     * setInternalState��powermock��Whiteboxǿ���˺ܶ࣬����Ȼ�о��õĵط����Ǻܶࡣ
     * @throws Exception
     */
    @Test
    @PrepareForTest(G.class)
    public void test08() throws Exception {
        G mock = mock(G.class);
        when(mock.getMessage()).thenReturn("This is mock G.");
        setInternalState(G.class, "s_instance", mock);

        mockStatic(G.class);
        doCallRealMethod().when(G.class, "getInstance");

        assertSame(mock, G.getInstance());
        assertEquals("This is mock G.", G.getInstance().getMessage());

        verify(mock).getMessage();
        verifyStatic(times(2));
        G.getInstance();
    }

    /**
     * ��ʾ@PowerMockIgnore�����á�
     * @throws Exception
     */
    @Test
    @PowerMockIgnore({ "java.awt.*", "javax.swing.*" })
    public void test09() {
        //TODO @PowerMockIgnore�ǽ��һЩpowermock������������ClassCastException�����Ψһ����������Ҫ����ʱ��û�ҵ����ʵ����ӡ�
    }

}
