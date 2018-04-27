package com.uken.platform.demo.controllers.requests;

import org.hibernate.validator.constraints.NotEmpty;

public class RedisSetRequest {

  @NotEmpty private String key;

  @NotEmpty private String value;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
