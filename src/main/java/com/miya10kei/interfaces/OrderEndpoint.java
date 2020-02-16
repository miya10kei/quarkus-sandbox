package com.miya10kei.interfaces;

import com.miya10kei.models.customer.CustomerRepository;
import com.miya10kei.models.order.Orders;
import com.miya10kei.models.order.OrderRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("orders")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderEndpoint {
  private final OrderRepository orderRepository;
  private final CustomerRepository customerRepository;

  @Inject
  public OrderEndpoint(OrderRepository orderRepository, CustomerRepository customerRepository) {
    this.orderRepository = orderRepository;
    this.customerRepository = customerRepository;
  }

  @GET
  public List<Orders> getAll(@QueryParam("customerId") Long customerId) {
    return orderRepository.findAll(customerId);
  }

  @POST
  @Path("/{customerId}")
  public Response create(Orders orders, @PathParam("customerId") Long customerId) {
    var customer = customerRepository.findCustomerById(customerId);
    orderRepository.createOrder(orders, customer);
    return Response.status(Status.NO_CONTENT).build();
  }

  @PUT
  public Response update(Orders orders) {
    orderRepository.updateOrder(orders);
    return Response.status(Status.NO_CONTENT).build();
  }

  @DELETE
  @Path("/{orderId}")
  public Response delete(@PathParam("orderId") Long orderId) {
    orderRepository.deleteOrder(orderId);
    return Response.status(Status.NO_CONTENT).build();
  }
}
