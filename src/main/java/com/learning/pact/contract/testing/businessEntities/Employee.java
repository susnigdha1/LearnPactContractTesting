package com.learning.pact.contract.testing.businessEntities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class Employee {
    @Id
    //@GeneratedValue
    @Column(nullable = false)
    String employee_id;

    @Basic(optional = false)
    @Column(nullable = false)
    String employee_joining_date;

    @Basic(optional = false)
    @Column(nullable = false)
    String employee_designation;
}
