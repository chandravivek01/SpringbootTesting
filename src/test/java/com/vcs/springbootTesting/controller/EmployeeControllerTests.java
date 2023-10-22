package com.vcs.springbootTesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vcs.springbootTesting.model.Employee;
import com.vcs.springbootTesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // JUnit test to createEmployee method
    @DisplayName("JUnit test to createEmployee method")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("Vivek").lastName("Chandra").email("vcs@vcs.com").build();

        BDDMockito.given(employeeService.saveEmployee((ArgumentMatchers.any(Employee.class))))
                .willAnswer(invocation -> invocation.getArgument(0));
        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is((employee.getLastName()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    // JUnit test to getAllEmployees method REST API
    @DisplayName("JUnit test to getAllEmployees method REST API")
    @Test
    public void givenListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {

        // given - precondition or setup
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("Vivek").lastName("Chandra").email("vcs@vcs.com").build());
        employeeList.add(Employee.builder().firstName("Vraja").lastName("Mohana").email("vraja@vraja.com").build());

        BDDMockito.given(employeeService.getAllEmployees()).willReturn(employeeList);

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(employeeList.size())));
    }

    // JUnit test to getEmployeeById method REST API (Positive Scenario)
    @DisplayName("JUnit test to getEmployeeById method REST API (Positive Scenario)")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Vivek")
                .lastName("Chandra")
                .email("vcs@vcs.com")
                .build();

        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(employee.getEmail())));
    }

    // JUnit test to getEmployeeById method REST API (Negative Scenario)
    @DisplayName("JUnit test to getEmployeeById method REST API (Negative Scenario)")
    @Test
    public void givenInvalidEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {

        // given - precondition or setup
        long employeeId = 1L;
        Employee employee = Employee.builder()
                .firstName("Vivek")
                .lastName("Chandra")
                .email("vcs@vcs.com")
                .build();

        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // JUnit test to updateEmployee method REST API (Positive Scenario)
    @DisplayName("JUnit test to updateEmployee method REST API (Positive Scenario)")
    @Test
    public void givenUpdatedEmployee_whenUpdatedEmployee_thenReturnUpdatedEmployeeObject() throws Exception {

        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Vivek").lastName("Chandra").email("vcs@vcs.com").build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Vraja").lastName("Chandra").email("vraja@vraja.com").build();

        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));
    }

    // JUnit test to updateEmployee method REST API (Negative Scenario)
    @DisplayName("JUnit test to updateEmployee method REST API (Negative Scenario)")
    @Test
    public void givenUpdatedEmployee_whenUpdatedEmployee_thenReturn404() throws Exception {

        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("Vivek").lastName("Chandra").email("vcs@vcs.com").build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Vraja").lastName("Chandra").email("vraja@vraja.com").build();

        BDDMockito.given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        BDDMockito.given(employeeService.updateEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // JUnit test to deleteEmployee method REST API
    @DisplayName("JUnit test to deleteEmployee method REST API")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {

        // given - precondition or setup
        long employeeId = 1L;
        BDDMockito.willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when - action or the behaviour that we are going to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/{id}", employeeId));

        // then - verify the output
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
