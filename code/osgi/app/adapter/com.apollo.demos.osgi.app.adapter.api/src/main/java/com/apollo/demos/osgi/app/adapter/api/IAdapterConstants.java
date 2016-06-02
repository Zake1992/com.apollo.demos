/*
 * �˴��봴���� 2016��5��20�� ����1:44:12��
 */
package com.apollo.demos.osgi.app.adapter.api;

import static com.apollo.demos.osgi.app.adapter.api.IAdapterConstants.Factory.Processor;
import static org.osgi.service.component.ComponentConstants.COMPONENT_FACTORY;
import static org.osgi.service.component.ComponentConstants.COMPONENT_NAME;

public interface IAdapterConstants {

    public static interface Factory {

        public static final String Processor = "adapter.processor";

    }

    public static interface Target {

        public static final String ProcessorPrefix = "(" + COMPONENT_FACTORY + "=" + Processor + ")(" + COMPONENT_NAME + "=";

    }

    public static interface Function {

        public static final String Adapter = "adapter";

    }

    public static interface Property {

        public static final String FunctionId = "adapter.functionId";

        public static final String DeviceType = "adapter.deviceType";

        public static final String Version = "adapter.version";

    }

}
