/**
 * 
 */
package com.ss.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author sachin
 *
 */
@Entity
@Table(name = "address")
public class Address {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@NotNull
    private String line1;
	@NotNull
    private String line2;
    @NotNull
    private Long zipCode;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private FINCRUser user;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLine1() {
		return line1;
	}
	public void setLine1(String line1) {
		this.line1 = line1;
	}
	public String getLine2() {
		return line2;
	}
	public void setLine2(String line2) {
		this.line2 = line2;
	}
	public Long getZipCode() {
		return zipCode;
	}
	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}
	public FINCRUser getUser() {
		return user;
	}
	public void setUser(FINCRUser user) {
		this.user = user;
	}
    
}
