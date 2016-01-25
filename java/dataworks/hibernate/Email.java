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
@Table(name = "email")
@JsonInclude(Include.NON_EMPTY)
public class Email {
	private String id;
	private boolean primary;
	private String email;
	private Employee employee;
	
	public Email() {
		
	}
	
	public Email(String id, boolean primary, String email, Employee employee) {
		this.id = id;
		this.primary = primary;
		this.email = email;
		this.employee = employee;
	}

	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!this.getClass().equals(obj.getClass())) return false;

		Email newObj = (Email)obj;
		if((this.id == newObj.getId()) && (this.email.equals(newObj.getEmail()))) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		int result = 0;
		result = (id + email).hashCode();
		return result;
	}

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "email_id", unique = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_employee_email", nullable = false)
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}