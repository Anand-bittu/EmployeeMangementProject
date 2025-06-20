package com.anand.entity;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.anand.utility.IDGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client {

	@Id     //make Client Id a primaryKey
	private String clientId;
	
	@Column(nullable =false)
	private String companyName;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy") // Format date in JSON as dd-MM-yyyy
	private LocalDate relationshipDate=LocalDate.now();// Defaults to current date
	 /* Defines a one-to-many relationship with Contact details entity
     *`mappedBy = "client"` means the `client` field in ContactPerson owns the relationship
     *`cascade = CascadeType.ALL` ensures all operations (persist, merge, remove) are applied to related contact details
     *`orphanRemoval = true` ensures contact persons are
     * deleted when removed from the list*/
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL,orphanRemoval = true)//fetch type is pending
	private List<ContactDetails> contactDetails;
	/*@OneToMany(mappedBy = "client"): Defines a one-to-many relationship 
	 * between the Client entity and the Project entity. One client can have multiple projects. 
	 * mappedBy = "client" indicates that the relationship is 
	 * managed from the Project side through the client field.
	 * Similar cascade and orphanRemoval behavior as contact details
	 */
	@OneToMany(mappedBy = "client",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Project> projects;
	
	public Client() {
         System.out.println("inside a client");
	}
	/*
	 * The @PrePersist annotation means the assignClientId() method will be called 
	 * automatically by JPA/Hibernate
	 *  just before a new Employee entity is inserted into the database.
	 */
	@PrePersist
	public  void assignClientId() {
		if(this.clientId==null||this.clientId.isEmpty()) {
			//auto Generate ClientID
			this.clientId=IDGenerator.generateClientID();
		}
	}
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public LocalDate getRelationshipDate() {
		return relationshipDate;
	}

	public void setRelationshipDate(LocalDate relationshipDate) {
		this.relationshipDate = relationshipDate;
	}

	public List<ContactDetails> getContactDetails() {
		return contactDetails;
	}

	public void setContactDetails(List<ContactDetails> contactDetails) {
		this.contactDetails = contactDetails;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	
	
}
