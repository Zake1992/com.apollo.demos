/*
 * �˴��봴���� 2016��5��25�� ����1:21:49��
 */
package com.apollo.demos.osgi.app.adapter.api;

public interface IAdapter {

    public abstract IFunction getFunction(String functionId, String deviceType, String version);

}
