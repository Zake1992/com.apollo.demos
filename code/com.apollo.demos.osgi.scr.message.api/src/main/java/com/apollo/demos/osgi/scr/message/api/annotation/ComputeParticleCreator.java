/*
 * �˴��봴���� 2016��1��13�� ����11:22:03��
 */
package com.apollo.demos.osgi.scr.message.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.apollo.demos.osgi.scr.message.api.EScope;
import com.apollo.demos.osgi.scr.message.api.EType;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ComputeParticleCreator {

    public EType type();

    public String function();

    public EScope scope() default EScope.CNode;

}
