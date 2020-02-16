package com.miya10kei.models.customer;

import com.miya10kei.models.order.Orders;
import java.util.List;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@NamedQuery(
    name = "Customer.findAll",
    query = "SELECT c FROM Customer c ORDER BY c.id",
    hints = @QueryHint(name = "org.hibernate.cacheable", value = "true"))
public class Customer {
  @Id
  @SequenceGenerator(name = "customerSequence", sequenceName = "customerId_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSequence")
  private Long id;

  @Column(length = 40)
  private String name;

  @Column(length = 40)
  private String surname;

  @OneToMany(mappedBy = "customer")
  @JsonbTransient
  private List<Orders> orders;
}
