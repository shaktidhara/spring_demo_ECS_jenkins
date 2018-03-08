package com.uken.platform.demo.models;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", unique = true, nullable = false, updatable = false)
  private String id;

  public String getId() {
    return id;
  }
}
