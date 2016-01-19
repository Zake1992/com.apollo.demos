/*
 * �˴��봴���� 2016��1��11�� ����9:54:45��
 */
package com.apollo.demos.osgi.scr.message.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.scr.message.api.IMessageContext;

public class MessageContextImpl implements IMessageContext {

    private static final Logger s_logger = LoggerFactory.getLogger(MessageContextImpl.class);

    public MessageContextImpl() {
        s_logger.info("New.");
    }

    @Override
    public String toString() {
        return "This is a message context.";
    }

}
