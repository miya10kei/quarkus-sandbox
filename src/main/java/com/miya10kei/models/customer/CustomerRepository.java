package com.miya10kei.models.customer;

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
public class CustomerRepository {
  private final EntityManager entityManager;

  @Inject
  public CustomerRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public List<Customer> findAll() {
    return entityManager.createNamedQuery("Customer.findAll", Customer.class).getResultList();
  }

  public Customer findCustomerById(Long id) {
    var customer = entityManager.find(Customer.class, id);
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
    entityManager.persist(customer);
  }

  @Transactional
  public void deleteCustomer(Long customerId) {
    var customer = findCustomerById(customerId);
    entityManager.remove(customer);
  }
}
