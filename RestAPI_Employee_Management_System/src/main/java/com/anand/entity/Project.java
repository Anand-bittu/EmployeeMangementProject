package com.anand.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.anand.utility.IDGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "project")
public class Project {
    @Id
	private String projectId;
    
    @Column(nullable = false)
	private String name;
    
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern ="dd-MM-yyyy")
	private LocalDate startDate=LocalDate.now();
    
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    //taking the End data from the endUser
	private LocalDate endDate;
    
 // Prevent recursive  of employees in response of project
    @JsonIgnore
    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Employee> employee;
	
    /*
     * @ManyToOne: Defines a many-to-one relationship between 
     * the Project entity and the Client entity. Many projects can
     *  be associated with one client.
     * @JoinColumn(name = "client_id"): Specifies the foreign key column in the project table
     *  that links to the client table. 
     * The column will be named client_id.
     */
    @ManyToOne
    @JoinColumn(name = "clientId",referencedColumnName = "clientId")
    @JsonIgnore //prevent recursive of client in response of project
    private Client client;
       
	public Project() {
        System.out.println("inside the project ");
	}

	/*
	 * The @PrePersist annotation means the assignProjectId() method will be called 
	 * automatically by JPA/Hibernate
	 *  just before a new Project entity is inserted into the database.
	 */
	@PrePersist
	public  void assignClientId() {
		if(this.projectId==null||this.projectId.isEmpty()) {
			//auto Generate ProjectID
			System.out.println("inside project ID IF block");
			this.projectId=IDGenerator.generateProjectID();
		}
	}


	public String getProjectId() {
		return projectId;
	}




	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}




	public LocalDate getEndDate() {
		return endDate;
	}




	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}




	public List<Employee> getEmployee() {
		return employee;
	}




	public void setEmployee(List<Employee> employee) {
		this.employee = employee;
	}




	public Client getClient() {
		return client;
	}




	public void setClient(Client client) {
		this.client = client;
	}

	
}
