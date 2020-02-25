package com.miya10kei.models.customer;

import io.quarkus.panache.common.Sort;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;

@Slf4j
@ApplicationScoped
public class CustomerRepository {

  //  @Timeout(250)
  //  @Fallback(fallbackMethod = "findAllStatic")
  //  @Retry(
  //      maxRetries = 3,
  //      retryOn = {RuntimeException.class, TimeoutException.class})
  @CircuitBreaker(
      successThreshold = 5,
      requestVolumeThreshold = 4,
      failureRatio = 0.75,
      delay = 1000)
  public List<Customer> findAll() {
    //    randomSleep();
    //    possibleFailure();
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

  private List<Customer> findAllStatic() {
    log.info("Building Static List of Customers");
    return buildStaticList();
  }

  private void randomSleep() {
    try {
      //      Thread.sleep(new Random().nextInt(400));
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private List<Customer> buildStaticList() {
    List<Customer> customerList = new ArrayList();
    Customer c1 = new Customer();
    c1.setId(1l);
    c1.setName("John");
    c1.setSurname("Doe");

    Customer c2 = new Customer();
    c2.setId(2l);
    c2.setName("Fred");
    c2.setSurname("Smith");

    customerList.add(c1);
    customerList.add(c2);
    return customerList;
  }

  private void possibleFailure() {
    if (new Random().nextFloat() < 0.5f)
      throw new RuntimeException(
          "I have generated a Random Resource failure! Try again accessing the service.");
  }
}
