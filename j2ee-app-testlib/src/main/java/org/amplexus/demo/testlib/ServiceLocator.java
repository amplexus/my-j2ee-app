package org.amplexus.demo.testlib;

import org.amplexus.demo.orderservice.OrderService;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * A service locator for locating services.
 */
public class ServiceLocator {

    static ThreadLocal<JaxWsProxyFactoryBean> factory = new ThreadLocal<JaxWsProxyFactoryBean>();
    static ThreadLocal<OrderService> client = new ThreadLocal<OrderService>();

    /**
     * Locates the order service.
     * 
     * @return the order service located.
     */
    public static OrderService locateOrderService() {
        if (factory.get() == null) {
            JaxWsProxyFactoryBean bean = new JaxWsProxyFactoryBean();
            bean = new JaxWsProxyFactoryBean();
            // bean.getInInterceptors().add(new LoggingInInterceptor());
            // bean.getOutInterceptors().add(new LoggingOutInterceptor());
            bean.setServiceClass(OrderService.class);
            String url = Configurator.getTestProperties().getProperty(
                    Configurator.PROP_ORDERMANAGER_WS_URL);
            System.out.println("Got test.properties url=" + url);
            bean.setAddress(url);
            factory.set(bean);
        }
        if (client.get() == null) {
            client.set((OrderService) factory.get().create());
        }
        return client.get();
    }
}
