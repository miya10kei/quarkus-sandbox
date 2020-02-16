package com.miya10kei.interfaces;

import com.miya10kei.interfaces.encoder.MessageEncoder;
import com.miya10kei.models.customer.Customer;
import com.miya10kei.models.customer.CustomerRepository;
import java.text.MessageFormat;
import java.util.List;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/customers", encoders = {MessageEncoder.class})
public class WebSocketEndpoint {
  private final CustomerRepository customerRepository;

  @Inject
  public WebSocketEndpoint(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> addCustomer(String message, Session session) {
    var jsonb = JsonbBuilder.create();
    var customer = jsonb.fromJson(message, Customer.class);
    customerRepository.createCustomer(customer);
    return customerRepository.findAll();
  }

  @OnOpen
  public void myOnOpen(Session session) {
    System.out.println(MessageFormat.format("WebSocket opened: %s", session.getId()));
  }

  @OnClose
  public void myOnClose(CloseReason reason) {
    System.out.println(MessageFormat.format("Closing a due to %s", reason.getReasonPhrase()));
  }

  @OnError
  public void error(Throwable t) {}
}
