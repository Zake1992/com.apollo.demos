/*
 * 此代码创建于 2015年12月26日 上午10:10:22。
 */
package com.apollo.demos.osgi.service.provider.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.service.api.bad.IBadSiri;
import com.apollo.demos.osgi.service.api.bad.IPhone;

public class BadSiriImpl implements IBadSiri {

    private static final Logger s_logger = LoggerFactory.getLogger(BadSiriImpl.class);

    public BadSiriImpl() {
        s_logger.info("New.");
    }

    @Override
    public String sayHello() {
        return "Hello, I am a bad Siri. [ID = " + ID + "]"; //这里的ID始终都是最新的，其实是编译优化引起的，并不是OSGi中存在2个IBadSiri。
    }

    @Override
    public IPhone giveMeAnIPhone() {
        return new IPhone();
    }

}
