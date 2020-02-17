package com.miya10kei.models.order;

import com.miya10kei.models.customer.Customer;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
@NamedQuery(
    name = "Orders.findAll",
    query = "SELECT o FROM Orders o WHERE o.customer.id = :customerId ORDER BY o.item")
public class Orders extends PanacheEntityBase {
  @Id
  @SequenceGenerator(name = "orderSequence", sequenceName = "orderId_seq", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSequence")
  private Long id;

  @Column(length = 40)
  private String item;

  @Column private Long price;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  @JsonbTransient
  private Customer customer;
}
