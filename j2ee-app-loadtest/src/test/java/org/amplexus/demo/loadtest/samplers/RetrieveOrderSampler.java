package org.amplexus.demo.loadtest.samplers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.amplexus.demo.orderservice.CreateOrderFault;
import org.amplexus.demo.orderservice.RetrieveOrderFault;
import org.amplexus.demo.orderservice.OrderService;
import org.amplexus.demo.orderservice.messages.CreateOrderRequest;
import org.amplexus.demo.orderservice.messages.CreateOrderResponse;
import org.amplexus.demo.orderservice.messages.RetrieveOrderRequest;
import org.amplexus.demo.orderservice.messages.RetrieveOrderResponse;
import org.amplexus.demo.testlib.ServiceLocator;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * A load test sampler for the order retrieval web service.
 */
public class RetrieveOrderSampler extends AbstractJavaSamplerClient {
    Logger logger = Logger.getLogger(RetrieveOrderSampler.class.getName());

    static ThreadLocal<OrderService> client = new ThreadLocal<OrderService>();

    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        SampleResult results = new SampleResult();
        int orderId = 0;

        results.sampleStart();

        /*
         * Pause timing because we don't want to include test setup in the measurements
         */
        results.samplePause();

        /*
         * Get the client
         */
        if (client.get() == null)
            client.set(ServiceLocator.locateOrderService());

        /*
         * Create the order we will retrieve later
         */
        try {
            CreateOrderResponse createOrderResponse = createOrder();
            orderId = createOrderResponse.getId();
        } catch (CreateOrderFault e) {
            results.sampleResume();
            results.setSampleLabel("RetrieveOrderSampler: failed: " + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
            results.sampleEnd();
            logger.log(Level.SEVERE, "Error calling create order service", e);
            return results;
        } catch (RuntimeException e) {
            results.sampleResume();
            results.setSampleLabel("RetrieveOrderSampler: failed: " + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
            results.sampleEnd();
            logger.log(Level.SEVERE, "Error calling create order service", e);
            return results;
        }

        /*
         * Resume timing now because we want to measure the retrieveOrder service
         */
        results.sampleResume();

        /*
         * Retrieve the order
         */
        try {
            RetrieveOrderRequest request = new RetrieveOrderRequest();
            request.setId(orderId);
            RetrieveOrderResponse response = client.get().retrieveOrder(request);

            results.setSampleLabel("RetrieveOrderSampler");
            results.setResponseOK();
            results.setSuccessful(true);

        } catch (RetrieveOrderFault e) {
            results.setSampleLabel("RetrieveOrderSampler: failed: " + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
            logger.log(Level.SEVERE, "Error calling cancel order service", e);
        } catch (RuntimeException e) {
            results.setSampleLabel("RetrieveOrderSampler: failed: " + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
            logger.log(Level.SEVERE, "Error calling cancel order service", e);
        }

        results.sampleEnd();
        return results;
    }

    CreateOrderResponse createOrder() throws CreateOrderFault {
        CreateOrderRequest request = makeCreateOrderRequest();
        CreateOrderResponse response = client.get().createOrder(request);
        return response;
    }

    CreateOrderRequest makeCreateOrderRequest() {
        CreateOrderRequest request = new CreateOrderRequest();
        request.setEmail("poo@bigpond.com");
        request.setName("poo bah");
        request.setAddress("100 tinks wy");
        request.setPhoneNumber("0407788888");
        return request;
    }
}
