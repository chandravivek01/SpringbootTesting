package com.vcs.springbootTesting.service;

import com.vcs.springbootTesting.exception.ResourceNotFoundException;
import com.vcs.springbootTesting.model.Employee;
import com.vcs.springbootTesting.repository.EmployeeRepository;
import com.vcs.springbootTesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    //    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup() {

//        employeeRepository = Mockito.mock(EmployeeRepository.class);
//        employeeService = new EmployeeServiceImpl(employeeRepository);
        employee = Employee.builder()
                .id(1L)
                .firstName("Vivek")
                .lastName("Chandra")
                .email("vcs@vcs.com")
                .build();
    }

    // JUnit test for saveEmployee method
    @DisplayName("JUnit test for saveEmployee method")
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {

        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test for saveEmployee method with Exception Handling
    @DisplayName("JUnit test for saveEmployee method which throws Exception")
    @Test
    public void givenExistingEmail_whenSaveEmployee_thenThrowsException() {

        // given - precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));
//        given(employeeRepository.save(employee)).willReturn(employee);

        // when - action or the behaviour that we are going to test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

//        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then - verify the output
//        assertThat(savedEmployee).isNotNull();
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test to getAllEmployees method")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmployeesList() {

        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Vraja")
                .lastName("Mohana")
                .email("vraja@vraja.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));
        // when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // JUnit test for getAllEmployees method
    @DisplayName("JUnit test to getAllEmployees method (negative scenario - when the list is empty.")
    @Test
    public void givenEmployeesList_whenGetAllEmployees_thenReturnEmptyEmployeesList() {

        // given - precondition or setup
        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("Vraja")
                .lastName("Mohana")
                .email("vraja@vraja.com")
                .build();
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());
        // when - action or the behaviour that we are going to test
        List<Employee> employeeList = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    // JUnit test to getEmployeeById method
    @DisplayName("JUnit test to getEmployeeById method")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {

        // given - precondition or setup
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going to test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
    }

    // JUnit test to updateEmployee method
    @DisplayName("JUnit test to updateEmployee")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {

        // given - precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("vivek@gmail.com");

        // when - action or the behaviour that we are going to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        // then - verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("vivek@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Vivek");
    }

    // JUnit test to deleteEmployee method
    @DisplayName("JUnit test to deleteEmployee method")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenNothing() {

        // given - precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        // when - action or the behaviour that we are going to test
        employeeService.deleteEmployee(employee.getId());

        // then - verify the output
        verify(employeeRepository, times(1)).deleteById(1L);
    }
}
