package com.anand.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contactDetails")
public class ContactDetails {
    //auto Generated
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cid;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String email;
	@Column(nullable = false)
	private String designation;
	@ManyToOne
	@JoinColumn(name = "clientId",referencedColumnName = "clientId")
	// Specifies the foreign key column in the database table that links to the client's `clientId`
    // `nullable = false` ensures that every contact person must be associated with a client
	@JsonBackReference
	private Client client;
	
	//When client set the Password with email id 
	@JsonIgnore
	@OneToOne(cascade = { CascadeType.PERSIST,CascadeType.REMOVE},orphanRemoval = true)
	@JoinColumn(name = "user_id",referencedColumnName = "id")// Foreign key to User table
	public User user;
	
	public ContactDetails() {
        System.out.println("inside ContactDetails");
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}
