package org.amplexus.demo.bean;

import org.amplexus.demo.model.Order;

/**
 * The EJB service for managing orders.
 */
public interface OrderBean {

    /**
     * The createOrder service.
     * 
     * Creates an order.
     * 
     * @param order the order to create.
     * @return the orderId of the order that was created.
     */
    public int createOrder(Order order);

    /**
     * The retrieveOrder service.
     * 
     * Retrieves an order.
     * 
     * @param orderId the order id of the order to retrieve.
     * @return the order, or null if not found.
     */
    public Order retrieveOrder(int orderId);

    /**
     * The cancelOrder service.
     * 
     * Cancels an order.
     * 
     * @param orderId the order id of the order to cancel - the order.getId() must reference a valid order.
     */
    public void cancelOrder(int orderId);

    /**
     * The updateOrder service.
     * 
     * Updates an order.
     * 
     * @param order the order to update - the order.getId() must reference a valid order.
     */
    public void updateOrder(Order order);
}