package com.miya10kei.models.customer;

import io.quarkus.panache.common.Sort;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

@ApplicationScoped
public class CustomerRepository {
  public CustomerRepository(EntityManager entityManager) {}

  public List<Customer> findAll() {
    return Customer.listAll(Sort.by("id"));
  }

  public Customer findCustomerById(Long id) {
    Customer customer = Customer.findById(id);
    return Optional.ofNullable(customer)
        .orElseThrow(
            () ->
                new WebApplicationException(
                    MessageFormat.format("Customer with id of %d does not exist.", id),
                    Status.NOT_FOUND));
  }

  @Transactional
  public void updateCustomer(Customer customer) {
    var customerToUpdate = findCustomerById(customer.getId());
    customerToUpdate.setName(customer.getName());
    customerToUpdate.setSurname(customer.getSurname());
  }

  @Transactional
  public void createCustomer(Customer customer) {
    customer.persist();
  }

  @Transactional
  public void deleteCustomer(Long customerId) {
    var customer = findCustomerById(customerId);
    customer.delete();
  }
}
