package com.miya10kei.models.order;

import com.miya10kei.models.customer.Customer;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

@ApplicationScoped
public class OrderRepository {
  private final EntityManager entityManager;

  @Inject
  public OrderRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Orders> findAll(Long customerId) {
    return entityManager
        .createNamedQuery("Orders.findAll", Orders.class)
        .setParameter("customerId", customerId)
        .getResultList();
  }

  public Orders findOrderById(Long id) {
    var order = entityManager.find(Orders.class, id);
    return Optional.ofNullable(order)
        .orElseThrow(
            () ->
                new WebApplicationException(
                    MessageFormat.format("Order with id of %d does not exist.", id),
                    Status.NOT_FOUND));
  }

  @Transactional
  public void updateOrder(Orders orders) {
    var orderToUpdate = findOrderById(orders.getId());
    orderToUpdate.setItem(orders.getItem());
    orderToUpdate.setPrice(orders.getPrice());
  }

  @Transactional
  public void createOrder(Orders orders, Customer customer) {
    orders.setCustomer(customer);
    entityManager.persist(orders);
  }

  @Transactional
  public void deleteOrder(Long id) {
    var order = findOrderById(id);
    entityManager.remove(order);
  }
}
