/*
 * 此代码创建于 2015年12月26日 上午10:17:04。
 */
package com.apollo.demos.osgi.service.invoker.impl;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.apollo.demos.osgi.service.api.bad.IBadSiri;
import com.apollo.demos.osgi.service.api.bad.IPhone;

public class BadAppleWatch extends ServiceTracker {

    private static final Logger s_logger = LoggerFactory.getLogger(BadAppleWatch.class);

    public BadAppleWatch(BundleContext context) {
        super(context, IBadSiri.class.getName(), null);
        s_logger.info("New.");
    }

    @Override
    public Object addingService(ServiceReference reference) {
        s_logger.info("Adding service.");

        IBadSiri badSiri = (IBadSiri) super.addingService(reference);
        IPhone ip = badSiri.giveMeAnIPhone();

        s_logger.info("IBadSiri: [ID = {}] Say: {}", IBadSiri.ID, badSiri.sayHello());
        s_logger.info("IPhone: [ID = {}] Say: {}", IPhone.ID, ip.sayHello());

        return badSiri;
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
        s_logger.info("Removed service.");

        super.removedService(reference, service);
    }

}
