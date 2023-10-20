package com.vcs.springbootTesting.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.vcs.springbootTesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setUp() {

        employee = Employee.builder()
                .firstName("Vivek")
                .lastName("Chandra")
                .email("vcs@vcs.com")
                .build();

    }

    // JUnit test to save operation
    @DisplayName("JUnit test for save operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Chandra")
//                .email("vcs@vcs.com")
//                .build();

        // when - action or the behaviour  that we are going to test
        Employee savedEmployee = employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // JUnit test to get all employees operation
    @DisplayName("JUnit test to get all employees operation")
    @Test
    public void givenEmployeesList_whenFindAll_thenEmployeesList() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Vivek")
//                .email("vivek@vivek.com")
//                .id(1)
//                .build();

        Employee employee2 = Employee.builder()
                .firstName("Chandra")
                .lastName("Chandra")
                .email("chandra@chandra.com")
                .id(2)
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee2);

        // when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test to get employee by id operation
    @DisplayName("JUnit test to get employee by id operation")
    @Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Vivek")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test to get employee by email operation
    @DisplayName("JUnit test to get employee by email operation")
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmployeeObject() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Vivek")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test to update employee operation
    @DisplayName("JUnit test to update employee operation")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Vivek")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();
        employeeDB.setLastName("Chandra");
        Employee updatedEmployee = employeeRepository.save(employeeDB);

        // then - verify the output
        assertThat(updatedEmployee.getLastName()).isEqualTo("Chandra");
    }

    // JUnit test to delete employee operation
    @DisplayName("JUnit test to delete employee operation")
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Vivek")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);

        // when - action or the behaviour that we are going to test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then - verify the output
        assertThat(employeeOptional).isEmpty();
    }

    // JUnit test to custom query using JPQL with index
    @DisplayName("JUnit test to custom query using JPQL with index")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnEmployeeObject() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Chandra")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Vivek";
        String lastName = "Chandra";

        // when - action or the behaviour that we are going to test
        Employee employeeDB =  employeeRepository.findByJPQL(firstName, lastName);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test to custom query using JPQL with named params
    @DisplayName("JUnit test to custom query using JPQL with named params")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnEmployeeObject() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Chandra")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Vivek";
        String lastName = "Chandra";

        // when - action or the behaviour that we are going to test
        Employee employeeDB =  employeeRepository.findByJPQLNamedParams(firstName, lastName);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test to custom query using Native SQL with index
    @Test
    @DisplayName("JUnit test to custom query using Native SQL with index")
    public void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnEmployeeObject() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Chandra")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Vivek";
        String lastName = "Chandra";

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByNativeSQL(firstName, lastName);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }

    // JUnit test to custom query using Native SQL with named params
    @Test
    @DisplayName("JUnit test to custom query using Native SQL with named params")
    public void givenFirstNameAndLastName_whenFindByNativeSQLNamedParams_thenReturnEmployeeObject() {

        // given - precondition or setup
//        Employee employee = Employee.builder()
//                .firstName("Vivek")
//                .lastName("Chandra")
//                .email("vivek@vivek.com")
//                .build();

        employeeRepository.save(employee);
        String firstName = "Vivek";
        String lastName = "Chandra";

        // when - action or the behaviour that we are going to test
        Employee employeeDB = employeeRepository.findByNativeSQLNamedParams(firstName, lastName);

        // then - verify the output
        assertThat(employeeDB).isNotNull();
    }
}
