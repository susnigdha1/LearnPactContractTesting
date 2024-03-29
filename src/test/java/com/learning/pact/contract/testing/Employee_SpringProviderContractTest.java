package com.learning.pact.contract.testing;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/***
 * This test class runs the Provider contract test using SpringBoot
 * This differentiates this test class from Employee_ProviderContractTest.class
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // Custom pact runner, child of PactRunner which runs only REST tests
@Provider("rest-employee") // Set up name of tested provider
@PactFolder("resources/pact") // Point where to find pacts (See also section Pacts source in documentation)
public class Employee_SpringProviderContractTest {

    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State("A request is made to add an employee information")
    public void helloPost(){
        System.out.println("Hello");
    }

    @State("A request is made to view an employee information")
    public void helloGet(){
        System.out.println("Hello");
    }

}
