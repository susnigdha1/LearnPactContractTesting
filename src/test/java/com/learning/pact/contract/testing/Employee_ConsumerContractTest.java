package com.learning.pact.contract.testing;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.MockServerConfig;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.web.server.LocalServerPort;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/***
 * This test class is to illustrate on how to perform Consumer Contract test using PACT
 * When this test is run, it creates MockServer for the Web Service to run
 */
@ExtendWith(PactConsumerTestExt.class)
@MockServerConfig(providerName = "rest-employee", port = "8080", hostInterface = "localhost")
@PactDirectory("resources/pact")
public class Employee_ConsumerContractTest {
    RequestSpecification requestSpecification;

    @LocalServerPort
    private int port;

    Map<String, String> headers = new HashMap<>();

    @BeforeEach
    public void setUp(MockServer mockServer) {
        assertThat(mockServer, is(notNullValue()));
    }

    @Pact(provider="rest-employee",consumer="test_consumer") //Here we defined the PACT provider and consumer names. To be referred in other test classes
    public RequestResponsePact createPact(PactDslWithProvider builder) throws NullPointerException {
        headers.put("Content-Type", "application/json; charset=UTF-8");

        DslPart body_get_employee = PactDslJsonArray
                        .arrayEachLike()
                        .stringMatcher("employee_id", "[A-Z]{2}[0-9]{3}")
                        .stringType("employee_joining_date", "23/09/2021")
                        .stringType("employee_designation", "Associate")
                        .closeObject();

        DslPart body_add_employee = new PactDslJsonBody()
                .stringMatcher("employee_id", "[A-Z]{2}[0-9]{3}")
                .stringType("employee_joining_date", "23/09/2021")
                .stringType("employee_designation", "Associate")
                .close();

        assert body_add_employee != null;
        assert body_get_employee != null;

        return builder
                .given("A request is made to add an employee information")
                .uponReceiving("A request is made to add an employee information")
                .path("/addEmployee")
                .body(body_add_employee)
                .method("POST")
                .willRespondWith()
                .status(201)

                .given("A request is made to view an employee information")
                .uponReceiving("A request is made to view an employee information")
                .path("/getEmployee")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body_get_employee)
                .toPact();
    }

    public void setup_rest_assured()
    {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(ContentType.JSON)
                .setContentType("application/json; charset=UTF-8")
                .setBaseUri("http://localhost")
                .setPort(8080);
        this.requestSpecification = requestSpecBuilder.build();
    }

    @Test
    @PactTestFor(providerName="rest-employee", pactMethod ="createPact", pactVersion = PactSpecVersion.V3)
    public void testPostRequest() {
        setup_rest_assured();
        String employee_id=RandomStringUtils.randomAlphabetic(2).toUpperCase()+RandomStringUtils.randomNumeric(3);
        ResponseBody objBody = given(this.requestSpecification)
                .body("{\n\"employee_id\":\""+employee_id+"\"," +
                        "\n\"employee_joining_date\":\"23/09/2021\"," +
                        "\"employee_designation\":\"Associate\"\n}")
                .post("/addEmployee");
        System.out.println(objBody.prettyPrint());
        System.out.println(201);

        objBody = given(this.requestSpecification)
                .get("/getEmployee")
                        .body();
        System.out.println(objBody.prettyPrint());
        System.out.println(200);
    }

}

