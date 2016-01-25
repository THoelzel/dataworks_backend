package dataworks.hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "address")
@JsonInclude(Include.NON_EMPTY)
public class Address {
	private String id;
	private boolean primary;
	private String line1;
	private String line2;
	private String state;
	private String postalCode;
	private String city;
	private String country;
	private String type;
	private Employee employee;
	
	public Address() {
		
	}
	
	public Address(String id, boolean primary, String line1, String line2, String state, String postalCode,
			String city, String country, String type) {
		this.id = id;
		this.primary = primary;
		this.line1 = line1;
		this.line2 = line2;
		this.state = state;
		this.postalCode = postalCode;
		this.city = city;
		this.country = country;
		this.type = type;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!this.getClass().equals(obj.getClass())) return false;

		Address newObj = (Address)obj;
		if((this.id == newObj.getId()) && (this.line1.equals(newObj.getLine1()))) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		int result = 0;
		result = (id + line1).hashCode();
		return result;
	}

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "address_id", unique = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_employee_address", nullable = false)
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	@Column(name = "line_1")
	public String getLine1() {
		return line1;
	}

	public void setLine1(String line1) {
		this.line1 = line1;
	}

	@Column(name = "line_2")
	public String getLine2() {
		return line2;
	}

	public void setLine2(String line2) {
		this.line2 = line2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "postal_code")
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}