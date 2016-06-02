/*
 * �˴��봴���� 2016��5��20�� ����2:22:37��
 */
package com.apollo.demos.osgi.app.adapter.ptn.api;

import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Target.ProcessorPrefix;
import static com.apollo.demos.osgi.app.adapter.ptn.api.IPtnAdapterConstants.ComponentName.Processor1;
import static com.apollo.demos.osgi.app.adapter.ptn.api.IPtnAdapterConstants.ComponentName.Processor99;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.service.component.ComponentFactory;

import com.apollo.demos.osgi.app.adapter.api.BaseFunction;
import com.apollo.demos.osgi.app.adapter.api.Processor;

/*�ӱ�ע��Ϊ����maven-scr-plugin����ȷ������DS�����ļ���*/
@Component
public abstract class PtnBaseFunction extends BaseFunction {

    @Reference(target = ProcessorPrefix + Processor1 + ")")
    @Processor(order = 1)
    protected volatile ComponentFactory processor1;

    @Reference(target = ProcessorPrefix + Processor99 + ")")
    @Processor(order = 99)
    protected volatile ComponentFactory processor99;

}
