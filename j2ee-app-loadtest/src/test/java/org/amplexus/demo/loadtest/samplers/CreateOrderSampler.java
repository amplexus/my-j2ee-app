package org.amplexus.demo.loadtest.samplers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.amplexus.demo.orderservice.CreateOrderFault;
import org.amplexus.demo.orderservice.OrderService;
import org.amplexus.demo.orderservice.messages.CreateOrderRequest;
import org.amplexus.demo.orderservice.messages.CreateOrderResponse;
import org.amplexus.demo.testlib.ServiceLocator;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * A load test sampler for the order creation web service.
 */
public class CreateOrderSampler extends AbstractJavaSamplerClient {
    Logger logger = Logger.getLogger(CreateOrderSampler.class.getName());

    static ThreadLocal<OrderService> client = new ThreadLocal<OrderService>();

    @Override
    public SampleResult runTest(JavaSamplerContext arg0) {
        SampleResult results = new SampleResult();

        results.sampleStart();
        results.samplePause();

        /*
         * Get the client
         */
        if (client.get() == null)
            client.set(ServiceLocator.locateOrderService());

        /*
         * Randomise the order quantity and price
         */
        results.sampleResume();

        /*
         * Create the order
         */
        try {
            CreateOrderRequest request = new CreateOrderRequest();
            request.setEmail("poo@bigpond.com");
            request.setName("poo bah");
            request.setAddress("100 tinks wy");
            request.setPhoneNumber("0407788888");
            CreateOrderResponse response = client.get().createOrder(request);
            if (response != null && response.getId() > 0) {
                results.setSampleLabel("CreateOrderSampler");
                results.setResponseOK();
                results.setSuccessful(true);
            } else {
                final String error = "response was invalid";
                results.setSampleLabel("CreateOrderSampler: failed: " + error);
                results.setResponseCode("500");
                results.setResponseMessage(error);
                results.setSuccessful(false);
                logger.log(Level.SEVERE, "Error calling service: " + error);
            }

        } catch (CreateOrderFault e) {
            results.setSampleLabel("CreateOrderSampler: failed: "
                    + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
            logger.log(Level.SEVERE, "Error calling service", e);
        } catch (RuntimeException e) {
            results.setSampleLabel("CreateOrderSampler: failed: "
                    + e.getClass().getName() + ": " + e.getMessage());
            results.setResponseCode("500");
            results.setResponseMessage(e.getMessage());
            results.setSuccessful(false);
            logger.log(Level.SEVERE, "Error calling service", e);
        }

        results.sampleEnd();
        return results;
    }
}
