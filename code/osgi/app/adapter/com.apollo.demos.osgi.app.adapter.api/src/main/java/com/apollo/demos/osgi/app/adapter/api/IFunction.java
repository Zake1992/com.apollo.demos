/*
 * �˴��봴���� 2016��5��20�� ����1:33:50��
 */
package com.apollo.demos.osgi.app.adapter.api;

import org.osgi.service.component.ComponentFactory;

public interface IFunction {

    public abstract ComponentFactory[] getProcessors();

}
