package net.java.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import net.java.springboot.repository.EmployeeRepository;
import net.java.springboot.exception.ResourceNotFoundException;
import net.java.springboot.model.*;

@RestController
@RequestMapping("api/v1/")
public class EmployeeController {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
   //get employees
   @GetMapping("employees")
   public List<Employee> getAllEmployee(){
	   return this.employeeRepository.findAll();
   }
	
   //get employee by
   @GetMapping("/employees/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Employee employee = employeeRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));
		return ResponseEntity.ok().body(employee);
	}
   
   //save employee
   @PostMapping("employees")
   public Employee createEmployee(@RequestBody Employee employee) {
	return this.employeeRepository.save(employee);
	   
   }
   
   //update employee
   @PutMapping("/employees/{id}")
   public ResponseEntity<Employee> updateEmployee(@PathVariable(value ="id") Long employeeId,
		   @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException{
	   Employee employee= employeeRepository.findById(employeeId)
			   .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id:"+ employeeId));
       employee.setFirstName(employeeDetails.getFirstName());
       employee.setLastName(employeeDetails.getLastName());
       employee.setMail(employeeDetails.getMail());
       employee.setSalary(employeeDetails.getSalary());
       
       return ResponseEntity.ok(this.employeeRepository.save(employee));
   }
   
   //delete employee
   @DeleteMapping("employees/{id}")
   public Map<String,Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId) throws ResourceNotFoundException{
	
	   Employee employee= employeeRepository.findById(employeeId)
			   .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id:"+ employeeId));
	   this.employeeRepository.delete(employee);
	   
	   Map<String,Boolean> response= new HashMap<>();
	   response.put("deleted", Boolean.TRUE);
	   return null;
	   
   }
}
