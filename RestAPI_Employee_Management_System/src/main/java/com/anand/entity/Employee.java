package com.anand.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.anand.utility.IDGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {
	
    @Id
	private String employeeId;
    
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false)
	private String department;
	
	@Column(nullable = false)
	//email should be unique
	private String email;
	
	
	@Column(nullable = false)
	private String phone;
	
	
	@Column(nullable = false)// Field "joiningDate" cannot be null in the database
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy")
	private LocalDate joiningDate=LocalDate.now();// Defaults to current date
	/*
	 * @ManyToOne: This annotation defines a many-to-one relationship 
	 * between the Employee entity and the Project entity.
	 *  One project can have multiple employees.
	 * @JoinTable::This specifies the foreign key
	 *  column in the employee table that links to the project table. 
	 *  The column will be named project_id.
	 */
	@ManyToOne
	@JoinColumn(name = "projectId",referencedColumnName = "projectId")
	// Prevents recursive serialization or hiding project information from the  in API response
     @JsonIgnore
	private Project project;
	
	//user database 
	//when Employee  set password  then this employee will be stored 
	//in the user table these is done by this casecadeType.PERSIST,and if we delete the 
	//employee then in user table the employee will also be deleted this by CascadeType.REMOVE
	
	@JsonIgnore // Prevents user details from appearing in API responses
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
	@JoinColumn(name="user_id",referencedColumnName ="id" )// Foreign key to User table
	private User user;
	
	public Employee() {
        System.out.println("inside Employee Constructor");
	}
	//first Custom Generate the EmployeeID Before save the Employee 
	//Object these method is called before saving the employee in the database
	/*
	 * The @PrePersist annotation means the assignEmployeeId() method will be called 
	 * automatically by JPA/Hibernate
	 *  just before a new Employee entity is inserted into the database.
	 */
	@PrePersist
	public  void assignEmployeeId() {
		if(this.employeeId==null || this.employeeId.isEmpty()) {
			//auto Generate EmployeeID
			this.employeeId=IDGenerator.generateEmployeeID();
		}
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public LocalDate getJoiningDate() {
		return joiningDate;
	}
	public void setJoiningDate(LocalDate joiningDate) {
		this.joiningDate = joiningDate;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
