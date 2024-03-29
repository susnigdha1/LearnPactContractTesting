package com.learning.pact.contract.testing.databaseRepository;

import com.learning.pact.contract.testing.businessEntities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryInterface extends CrudRepository<Employee, String> {
}
