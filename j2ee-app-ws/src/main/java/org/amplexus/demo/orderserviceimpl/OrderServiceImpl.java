package org.amplexus.demo.orderserviceimpl;

import javax.ejb.EJB;
import javax.jws.WebService;

import org.amplexus.demo.bean.OrderBean;
import org.amplexus.demo.model.Order;
import org.amplexus.demo.orderservice.CancelOrderFault;
import org.amplexus.demo.orderservice.CreateOrderFault;
import org.amplexus.demo.orderservice.OrderService;
import org.amplexus.demo.orderservice.RetrieveOrderFault;
import org.amplexus.demo.orderservice.messages.CancelOrderRequest;
import org.amplexus.demo.orderservice.messages.CancelOrderResponse;
import org.amplexus.demo.orderservice.messages.CreateOrderRequest;
import org.amplexus.demo.orderservice.messages.CreateOrderResponse;
import org.amplexus.demo.orderservice.messages.RetrieveOrderRequest;
import org.amplexus.demo.orderservice.messages.RetrieveOrderResponse;
import org.apache.log4j.Logger;

import com.telstra.tdi.annotation.Alarming;
import com.telstra.tdi.annotation.Metric;

/**
 * Order service implementation.
 */
@WebService(serviceName = "OrderService", portName = "OrderServiceSOAP", targetNamespace = "http://demo.amplexus.org/orderservice/", wsdlLocation = "WEB-INF/wsdl/j2ee-app-order-contract-1.0/j2ee-app-order-contract.wsdl", endpointInterface = "org.amplexus.demo.orderservice.OrderService")
public class OrderServiceImpl implements OrderService {

    @EJB()
    OrderBean orderBean;

    static Logger log = Logger.getLogger(OrderServiceImpl.class.getName());

    /**
     * Create an order.
     * 
     * @param request
     *            the CreateOrderRequest
     * @return the CreateOrderResponse
     * @throws CreateOrderFault
     *             if there was a problem creating the order.
     */
    @Metric
    @Alarming(exceptions = { org.amplexus.demo.orderservice.CreateOrderFault.class })
    public CreateOrderResponse createOrder(CreateOrderRequest request)
            throws CreateOrderFault {
        CreateOrderResponse resp = new CreateOrderResponse();
        Order order = new Order();
        order.setName(request.getName());
        order.setAddress(request.getAddress());
        order.setPhoneNumber(request.getPhoneNumber());
        order.setEmail(request.getEmail());
        try {
            int id = orderBean.createOrder(order);
            resp.setId(id);
            return resp;
        } catch (Exception e) {
            throw new CreateOrderFault();
        }
    }

    /**
     * Cancel an order.
     * 
     * @param request
     *            the CancelOrderRequest
     * @return the CancelOrderResponse
     * @throws CancelOrderFault
     *             if there was a problem cancelling the order.
     */
    @Metric
    @Alarming(exceptions = { org.amplexus.demo.orderservice.CancelOrderFault.class })
    public CancelOrderResponse cancelOrder(CancelOrderRequest request)
            throws CancelOrderFault {
        try {
            orderBean.cancelOrder(request.getId());
            CancelOrderResponse resp = new CancelOrderResponse();
            resp.setId(request.getId());
            return resp;
        } catch (Exception e) {
            throw new CancelOrderFault();
        }
    }

    /**
     * Retrieve an order.
     * 
     * @param request
     *            the RetrieveOrderRequest
     * @return the RetrieveOrderResponse
     * @throws RetrieveOrderFault
     *             if there was a problem retrieving the order.
     */
    @Metric
    @Alarming(exceptions = { org.amplexus.demo.orderservice.RetrieveOrderFault.class })
    public RetrieveOrderResponse retrieveOrder(RetrieveOrderRequest request)
            throws RetrieveOrderFault {
        try {
            Order order = orderBean.retrieveOrder(request.getId());
            if (order == null)
                throw new RetrieveOrderFault();
            RetrieveOrderResponse resp = new RetrieveOrderResponse();
            resp.setId(order.getId());
            resp.setName(order.getName());
            resp.setAddress(order.getAddress());
            resp.setPhoneNumber(order.getPhoneNumber());
            resp.setEmail(order.getEmail());
            return resp;
        } catch (RetrieveOrderFault e) {
            // log.info("Order not found", e);
            throw e;
        } catch (RuntimeException e) {
            log.error("Error retrieving order", e);
            throw new RetrieveOrderFault();
        }
    }
}
