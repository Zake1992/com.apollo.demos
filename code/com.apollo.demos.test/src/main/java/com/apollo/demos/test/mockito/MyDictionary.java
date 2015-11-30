/*
 * �˴��봴���� 2015��11��20�� ����5:25:42��
 */
package com.apollo.demos.test.mockito;

import java.util.HashMap;
import java.util.Map;

public class MyDictionary {

    private Map<String, String> m_wordMap;

    public MyDictionary() {
        m_wordMap = new HashMap<String, String>();
    }

    public void add(String word, String meaning) {
        m_wordMap.put(word, meaning);
    }

    public String getMeaning(String word) {
        return m_wordMap.get(word);
    }

}
