/*
 * �˴��봴���� 2016��1��8�� ����2:24:30��
 */
package com.apollo.demos.osgi.scr.message.api;

public interface IComputeParticle {

    public abstract void processMessage(IMessageContext context, ParticleMessage message);

}
