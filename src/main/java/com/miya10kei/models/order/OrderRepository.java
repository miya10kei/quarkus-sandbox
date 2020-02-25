package com.miya10kei.models.order;

import com.miya10kei.models.customer.Customer;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

@Slf4j
@ApplicationScoped
public class OrderRepository {

  public List<Orders> findAll(Long customerId) {
    return Orders.list("id", customerId);
  }

  public Long countAll() {
    return Orders.count();
  }

  public Orders findOrderById(Long id) {
    Orders order = Orders.findById(id);
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
    orders.persist();
    withSomeLogging(orders.getItem());
  }

  @Transactional
  public void deleteOrder(Long id) {
    var order = findOrderById(id);
    order.delete();
  }

  @Asynchronous
  @Bulkhead(value = 5, waitingTaskQueue = 10)
  private Future withSomeLogging(String item) {
    log.info("New Customer order at {}", new Date());
    log.info("item {}", item);
    return CompletableFuture.completedFuture("ok");
  }
}
