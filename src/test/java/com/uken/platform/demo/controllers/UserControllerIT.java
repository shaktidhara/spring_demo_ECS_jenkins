package com.uken.platform.demo.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles(
    "test") // often it's helpful to have a separate profile for running tests. This turns it on
// automatically
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserControllerIT {

  private MockMvc mockMvc;

  private MediaType jsonMediaType =
      new MediaType(
          MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          Charset.forName("utf8"));

  @Autowired private WebApplicationContext webApplicationContext;

  @Before
  public void setup() {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  @Test
  public void postUserShouldReturnA200() throws Exception {
    mockMvc
        .perform(post("/api/users").contentType(jsonMediaType).content(""))
        .andExpect(status().isOk());
  }
}
