/*
 * �˴��봴���� 2016��1��8�� ����4:45:24��
 */
package com.apollo.demos.osgi.app.message.api;

public interface IMessageManager {

    @SuppressWarnings("rawtypes")
    public abstract void postMessage(ParticleMessage message);

}
