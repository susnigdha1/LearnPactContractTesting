package com.learning.pact.contract.testing.restControllers;

import com.learning.pact.contract.testing.businessEntities.Employee;
import com.learning.pact.contract.testing.businessLogic.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/getEmployee")
    //We can use either ResponseStatus or ResponseBody annotations for a Rest Mapping.
    //@ResponseStatus(code = HttpStatus.OK, reason = "Received view request on all employees")
    @ResponseBody()
    public List<Employee> simpleGet(){
        return employeeService.getAllEmployeeData();
    }

    @PostMapping("/addEmployee")
    @ResponseStatus(code= HttpStatus.CREATED, reason="New employee added")
    public void simplePost(@RequestBody Employee employee){
        employeeService.addAnEmployee(employee);
    }
}
