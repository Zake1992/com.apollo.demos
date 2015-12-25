/*
 * �˴��봴���� 2015��12��24�� ����10:20:12��
 */
package com.apollo.demos.osgi.service.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.service.api.ISiri;

public class SiriImpl implements ISiri {

    private static final Logger s_logger = LoggerFactory.getLogger(SiriImpl.class);

    public SiriImpl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, Service.";
    }

}
