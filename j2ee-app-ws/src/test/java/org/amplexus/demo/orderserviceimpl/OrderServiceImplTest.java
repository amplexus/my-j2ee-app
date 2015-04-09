package org.amplexus.demo.orderserviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.amplexus.demo.bean.OrderBean;
import org.amplexus.demo.model.Order;
import org.amplexus.demo.orderservice.CancelOrderFault;
import org.amplexus.demo.orderservice.CreateOrderFault;
import org.amplexus.demo.orderservice.RetrieveOrderFault;
import org.amplexus.demo.orderservice.messages.CancelOrderRequest;
import org.amplexus.demo.orderservice.messages.CancelOrderResponse;
import org.amplexus.demo.orderservice.messages.CreateOrderRequest;
import org.amplexus.demo.orderservice.messages.CreateOrderResponse;
import org.amplexus.demo.orderservice.messages.RetrieveOrderRequest;
import org.amplexus.demo.orderservice.messages.RetrieveOrderResponse;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for the order service implementation class.
 */
public class OrderServiceImplTest {

    OrderServiceImpl orderService = new OrderServiceImpl();
    OrderBean mockedOrderBean;

    @Before
    public void init() {
        mockedOrderBean = mock(OrderBean.class);
        orderService.orderBean = mockedOrderBean;
    }

    /**
     * Simulate EJB persisting order - expect the createOrder service to
     * succeed.
     */
    @Test
    public void testCreateOrderSuccess() throws CreateOrderFault {

        when(mockedOrderBean.createOrder(any(Order.class))).thenReturn(1);

        CreateOrderRequest request = new CreateOrderRequest();
        request.setName("Sebastian Wigglesworth");
        request.setAddress("123 Wiggle Dr Wiggleton NSW");
        request.setPhoneNumber("0407788333");
        request.setEmail("wiggles@worth.com");
        CreateOrderResponse response = orderService.createOrder(request);
        assertNotNull(response);
    }

    /**
     * Simulate EJB throwing a RuntimeException - expect the createOrder service
     * to wrap in CreateOrderFault.
     */
    @Test(expected = CreateOrderFault.class)
    public void testCreateOrderEjbFailure() throws CreateOrderFault {

        doThrow(new RuntimeException()).when(mockedOrderBean).createOrder(
                any(Order.class)); // FAIL!

        CreateOrderRequest request = new CreateOrderRequest();
        request.setName("Sebastian Wigglesworth");
        request.setAddress("123 Wiggle Dr Wiggleton NSW");
        request.setPhoneNumber("0407788333");
        request.setEmail("wiggles@worth.com");

        @SuppressWarnings("unused")
        CreateOrderResponse response = orderService.createOrder(request);
    }

    /**
     * Simulate EJB retrieve order - expect the retrieveOrder service to
     * succeed.
     */
    @Test
    public void testRetrieveOrderSuccess() throws RetrieveOrderFault {

        Order order = new Order();
        order.setName("Sebastian Wigglesworth");
        order.setAddress("123 Wiggle Dr Wiggleton NSW");
        order.setPhoneNumber("0407788333");
        order.setEmail("wiggles@worth.com");
        when(mockedOrderBean.retrieveOrder(anyInt())).thenReturn(order);

        RetrieveOrderRequest request = new RetrieveOrderRequest();
        request.setId(1);
        RetrieveOrderResponse response = orderService.retrieveOrder(request);

        assertNotNull(response);
        assertEquals(response.getId(), order.getId());
        assertEquals(response.getName(), order.getName());
        assertEquals(response.getAddress(), order.getAddress());
        assertEquals(response.getPhoneNumber(), order.getPhoneNumber());
        assertEquals(response.getEmail(), order.getEmail());
    }

    /**
     * Simulate EJB throwing a RuntimeException - expect the retrieveOrder
     * service to wrap in RetrieveOrderFault.
     */
    @Test(expected = RetrieveOrderFault.class)
    public void testRetrieveOrderEjbFailure() throws RetrieveOrderFault {

        doThrow(new RuntimeException()).when(mockedOrderBean).retrieveOrder(
                anyInt());

        RetrieveOrderRequest request = new RetrieveOrderRequest();
        request.setId(1);

        @SuppressWarnings("unused")
        RetrieveOrderResponse response = orderService.retrieveOrder(request);
    }

    /**
     * Simulate EJB cancel order - expect the cancelOrder service to succeed.
     */
    @Test
    public void testCancelOrderSuccess() throws CancelOrderFault {

        // when(mockedOrderBean.cancelOrder(anyInt())).thenDoNothing();
        doNothing().when(mockedOrderBean).cancelOrder(anyInt());

        CancelOrderRequest request = new CancelOrderRequest();
        request.setId(1);
        CancelOrderResponse response = orderService.cancelOrder(request);
        assertNotNull(response);
    }

    /**
     * Simulate EJB throwing a RuntimeException - expect the createOrder service
     * to wrap in CreateOrderFault.
     */
    @Test(expected = CancelOrderFault.class)
    public void testCancelOrderEjbFailure() throws CancelOrderFault {

        doThrow(new RuntimeException()).when(mockedOrderBean).cancelOrder(
                anyInt());

        CancelOrderRequest request = new CancelOrderRequest();
        request.setId(1);

        @SuppressWarnings("unused")
        CancelOrderResponse response = orderService.cancelOrder(request);
    }
}
