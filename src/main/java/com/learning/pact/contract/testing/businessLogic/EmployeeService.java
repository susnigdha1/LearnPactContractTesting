package com.learning.pact.contract.testing.businessLogic;

import com.learning.pact.contract.testing.businessEntities.Employee;
import com.learning.pact.contract.testing.databaseRepository.EmployeeRepositoryInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class EmployeeService {
    @Autowired
    EmployeeRepositoryInterface employeeRepositoryInterface;

    public List<Employee> getAllEmployeeData()
    {
        List<Employee> employees = new ArrayList<Employee>();
        employeeRepositoryInterface.findAll().forEach(employees::add);
        return employees;
    }

    public void addAnEmployee(Employee employee){
        employeeRepositoryInterface.save(employee);
        //Need to write a JPA method to insert an employee record to the database
    }
}
