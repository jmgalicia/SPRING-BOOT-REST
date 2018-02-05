package com.jmgalicia.test;

/**
 * Uses JsonPath: http://goo.gl/nwXpb, Hamcrest and MockMVC
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmgalicia.Application;
import com.jmgalicia.controller.UserController;
import com.jmgalicia.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;
import java.util.regex.Pattern;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("test")
public class UserControllerTest {

    private static final String RESOURCE_LOCATION_PATTERN = "http://localhost/users/[0-9]+";

    @InjectMocks
    UserController controller;

    @Autowired
    WebApplicationContext context;

    private MockMvc mvc;

    @Before
    public void initTests() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    //@Test
    public void shouldHaveEmptyDB() throws Exception {
        mvc.perform(get("/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldCreateRetrieveDelete() throws Exception {
        User r1 = buildUser("shouldCreateRetrieveDelete");
        byte[] r1Json = toJson(r1);

        //CREATE
        MvcResult result = mvc.perform(post("/users")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        //RETRIEVE
        mvc.perform(get("/users/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.email", is(r1.getEmail())))
                .andExpect(jsonPath("$.address", is(r1.getAddress())))
                .andExpect(jsonPath("$.password", is(r1.getPassword())));

        //DELETE
        mvc.perform(delete("/users/" + id))
                .andExpect(status().isNoContent());

        //RETRIEVE should fail
        mvc.perform(get("/users/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //todo: you can test the 404 error body too.

/*
JSONAssert.assertEquals(
  "{foo: 'bar', baz: 'qux'}",
  JSONObject.fromObject("{foo: 'bar', baz: 'xyzzy'}"));
 */
    }

    @Test
    public void shouldCreateAndUpdateAndDelete() throws Exception {
        User r1 = buildUser("shouldCreateAndUpdate");
        byte[] r1Json = toJson(r1);
        //CREATE
        MvcResult result = mvc.perform(post("/users")
                .content(r1Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern(RESOURCE_LOCATION_PATTERN))
                .andReturn();
        long id = getResourceIdFromUrl(result.getResponse().getRedirectedUrl());

        User r2 = buildUser("shouldCreateAndUpdate2");
        r2.setId(id);
        byte[] r2Json = toJson(r2);

        //UPDATE
        result = mvc.perform(put("/users/" + id)
                .content(r2Json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        //RETRIEVE updated
        mvc.perform(get("/users/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) id)))
                .andExpect(jsonPath("$.email", is(r2.getEmail())))
                .andExpect(jsonPath("$.address", is(r2.getAddress())))
                .andExpect(jsonPath("$.password", is(r2.getPassword())));

        //DELETE
        mvc.perform(delete("/users/" + id))
                .andExpect(status().isNoContent());
    }


    /*
    ******************************
     */

    private long getResourceIdFromUrl(String locationUrl) {
        String[] parts = locationUrl.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }


    private User buildUser(String prefix) {
        User r = new User();
        r.setAddress(prefix + "_address");
        r.setPassword(prefix + "_password");
        r.setEmail(prefix + "_email");
        return r;
    }

    private byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }

    // match redirect header URL (aka Location header)
    private static ResultMatcher redirectedUrlPattern(final String expectedUrlPattern) {
        return new ResultMatcher() {
            public void match(MvcResult result) {
                Pattern pattern = Pattern.compile("\\A" + expectedUrlPattern + "\\z");
                assertTrue(pattern.matcher(result.getResponse().getRedirectedUrl()).find());
            }
        };
    }

}
