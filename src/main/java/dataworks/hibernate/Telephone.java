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
@Table(name = "telephone")
@JsonInclude(Include.NON_EMPTY)
public class Telephone {
	private String id;
	private boolean primary;
	private String countryCode;
	private String areaCode;
	private String number;
	private String extension;
	private Employee employee;
	
	public Telephone() {
		
	}

	public Telephone(String id, boolean primary, String countryCode, String areadCode, String number, String extension,
			Employee employee) {
		this.id = id;
		this.primary = primary;
		this.countryCode = countryCode;
		this.areaCode = areadCode;
		this.number = number;
		this.extension = extension;
		this.employee = employee;
	}

	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!this.getClass().equals(obj.getClass())) return false;

		Telephone newObj = (Telephone)obj;
		if((this.id == newObj.getId()) && (this.number.equals(newObj.getNumber()))) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		int result = 0;
		result = (id + number).hashCode();
		return result;
	}

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "telephone_id", unique = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_employee_telephone", nullable = false)
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	@Column(name = "primary_telephone")
	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	@Column(name = "country_code")
	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Column(name = "area_code")
	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

}