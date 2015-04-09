package org.amplexus.demo.bean;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.amplexus.demo.model.Order;
import org.apache.log4j.Logger;

/**
 * The Order EJB implementation class.
 */
@Stateless()
@Local(OrderBean.class)
public class OrderBeanImpl implements OrderBean {

    static Logger log = Logger.getLogger(OrderBeanImpl.class.getName());

    @PersistenceContext(unitName = "order-pu")
    EntityManager em;

    /**
     * The createOrder service.
     * 
     * @see org.amplexus.demo.bean.OrderBean#createOrder
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int createOrder(Order order) {
        log.info("creating " + order.getName());
        em.persist(order);
        log.info("created " + order.getId());
        return order.getId();
    }

    /**
     * The retrieveOrder service.
     * 
     * @see org.amplexus.demo.bean.OrderBean#retrieveOrder
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Order retrieveOrder(int orderId) {
        log.info("retrieving " + orderId);
        return em.find(Order.class, orderId);
    }

    /**
     * The cancelOrder service.
     * 
     * @see org.amplexus.demo.bean.OrderBean#cancelOrder
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cancelOrder(int orderId) {
        log.info("cancelling " + orderId);
        Order order = em.find(Order.class, orderId);
	if(order == null)
	  return;
        em.remove(order);
    }

    /**
     * The updateOrder service.
     * 
     * @see org.amplexus.demo.bean.OrderBean#updateOrder
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void updateOrder(Order order) {
        log.info("updating " + order.getId());
	Order updatedOrder = em.merge(order);
    }
}
