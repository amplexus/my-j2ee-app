package org.amplexus.itest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.xml.ws.soap.SOAPFaultException;

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
import org.amplexus.demo.testlib.ServiceLocator;
import org.junit.Test;

/**
 * A test suite for testing the order creation system.
 */
public class BasicTest {

    /**
     * Test CreateOrder service.
     * 
     * @throws CreateOrderFault
     *             if there was a problem creating the order,
     */
    @Test
    public void testCreateOrder() throws CreateOrderFault {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setName("Sebastian Wigglesworth");
        request.setAddress("123 Wiggle St Worthery NSW");
        request.setPhoneNumber("0407733773");
        request.setEmail("sebastian@poo.com");
        OrderService client = ServiceLocator.locateOrderService();
        CreateOrderResponse resp = client.createOrder(request);
        assertNotNull(resp);
        assertTrue(resp.getId() > 0);
    }

    /**
     * Test CancelOrder service.
     * 
     * @throws CancelOrderFault
     *             if there was a problem cancelling the order,
     */
    @Test
    public void testCancelOrder() throws CreateOrderFault, CancelOrderFault {
        CreateOrderRequest createRequest = createTestOrderRequest();
        int id = createTestOrder(createRequest);
        CancelOrderRequest request = new CancelOrderRequest();
        request.setId(id);
        OrderService client = ServiceLocator.locateOrderService();
        CancelOrderResponse resp = client.cancelOrder(request);
        assertNotNull(resp);
        assertTrue(resp.getId() > 0);
    }

    /**
     * Test RetrieveOrder service.
     * 
     * Check that we can retrieve an order created previously
     * 
     * @throws RetrieveOrderFault
     *             if there was a problem retrieving the order,
     */
    @Test
    public void testRetrieveOrder() throws CreateOrderFault, RetrieveOrderFault {
        CreateOrderRequest createRequest = createTestOrderRequest();
        int id = createTestOrder(createRequest);
        RetrieveOrderRequest request = new RetrieveOrderRequest();
        request.setId(id);
        OrderService client = ServiceLocator.locateOrderService();
        RetrieveOrderResponse resp = client.retrieveOrder(request);
        assertNotNull(resp);
        assertTrue(resp.getId() > 0);
        assertEquals(resp.getName(), createRequest.getName());
        assertEquals(resp.getAddress(), createRequest.getAddress());
        assertEquals(resp.getAddress(), createRequest.getAddress());
        assertEquals(resp.getPhoneNumber(), createRequest.getPhoneNumber());
        assertEquals(resp.getEmail(), createRequest.getEmail());
    }

    /**
     * A helper method for creating test order requests.
     */
    private CreateOrderRequest createTestOrderRequest() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setName("Sebastian Wigglesworth");
        request.setAddress("123 Wiggle St Worthery NSW");
        request.setPhoneNumber("0407733773");
        request.setEmail("sebastian@poo.com");
        return request;
    }

    /**
     * A helper method for creating test orders.
     */
    private int createTestOrder(CreateOrderRequest request)
            throws CreateOrderFault {
        OrderService client = ServiceLocator.locateOrderService();
        CreateOrderResponse resp = client.createOrder(request);
        return resp.getId();
    }

}
