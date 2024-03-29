package com.learning.pact.contract.testing;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;
import au.com.dius.pact.provider.spring.junit5.PactVerificationSpringProvider;
import com.learning.pact.contract.testing.businessEntities.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/***
 * This test class will run the provider test by using a Rest Controller method and MockMvcTestTarget
 * This test does not run the test using SpringBoot, which differentiates it from Employee_SpringProviderContractTest.class
 */
@Provider("rest-employee") //The PACT provider name is defined in line 50 of Employee_ConsumerContractTest.class
//@Consumer("test_consumer") //By uncommenting this line, you can run the test using PACT consumer
@PactFolder("resources/pact") //Provides path to the pact contract file. This file will be created/replaced everytime you run the Employee_ConsumerContractTest.class
public class Employee_ProviderContractTest {
    @TestTemplate
    @ExtendWith(PactVerificationSpringProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(new EmployeeController());
        context.setTarget(testTarget);
    }

    @RestController
    static class EmployeeController {
        @GetMapping("/getEmployee")
        @ResponseStatus(HttpStatus.OK)
        List<Employee> getData() {
            ArrayList<Employee> employees = new ArrayList<>();
            employees.add(new Employee("AB123", "23/09/2021","Associate"));
            return employees;
        }
    }

    @State("A request is made to view an employee information")
    public void hello(){
        System.out.println("Hello");
    }

}
