package com.miya10kei.interfaces.encoder;

import com.miya10kei.models.customer.Customer;
import java.io.StringWriter;
import java.util.List;
import javax.json.Json;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<List<Customer>> {
  @Override
  public String encode(List<Customer> customers) throws EncodeException {
    var jsonArray = Json.createArrayBuilder();
    customers.forEach(
        customer -> {
          jsonArray.add(
              Json.createObjectBuilder()
                  .add("Name", customer.getName())
                  .add("Surname", customer.getName()));
        });
    var array = jsonArray.build();
    var buffer = new StringWriter();
    Json.createWriter(buffer).writeArray(array);
    return buffer.toString();
  }

  @Override
  public void init(EndpointConfig endpointConfig) {
    System.out.println("Init");
  }

  @Override
  public void destroy() {
    System.out.println("Destroy");
  }
}
