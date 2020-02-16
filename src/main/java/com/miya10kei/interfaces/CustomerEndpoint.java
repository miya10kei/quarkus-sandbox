package com.miya10kei.interfaces;

import com.miya10kei.models.customer.Customer;
import com.miya10kei.models.customer.CustomerRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("customers")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerEndpoint {
  private final CustomerRepository customerRepository;

  @Inject
  public CustomerEndpoint(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @GET
  public List<Customer> getAll() {
    return customerRepository.findAll();
  }

  @POST
  public Response create(Customer customer) {
    customerRepository.createCustomer(customer);
    return Response.status(Status.NO_CONTENT).build();
  }

  @PUT
  public Response update(Customer customer) {
    customerRepository.updateCustomer(customer);
    return Response.status(Status.NO_CONTENT).build();
  }

  @DELETE
  public Response delete(@QueryParam("id") Long customerId) {
    customerRepository.deleteCustomer(customerId);
    return Response.status(Status.NO_CONTENT).build();
  }
}
