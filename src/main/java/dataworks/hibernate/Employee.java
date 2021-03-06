package dataworks.hibernate;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "employee")
@JsonInclude(Include.NON_EMPTY)
public class Employee implements java.io.Serializable {
	
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String title;
	private Date birthdate;
	private Set<Address> addresses;
	private Set<Email> emails;
	private Set<Telephone> telephones;
	private Set<Task> tasks;
	private Set<EmployeeTeam> teams = new HashSet<EmployeeTeam>(0);

	public Employee() {
		
	}
	
	public Employee(String id, String firstName, String middleName, String lastName, String gender, String title,
			Date birthdate, Set<Address> addresses, Set<Email> emails, Set<Telephone> telephones, Set<Task> tasks,
			Set<EmployeeTeam> teams) {
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.title = title;
		this.birthdate = birthdate;
		this.addresses = addresses;
		this.emails = emails;
		this.telephones = telephones;
		this.tasks = tasks;
		this.teams = teams;
	}

	@Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "employee_id", unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(name = "middle_name")
	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Column(name = "gender")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "title")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "birth_dt")
	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT) 
	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
/*	public void addAddress(Address address) {
		this.addresses.add(address);
	}*/

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT) 
	public Set<Email> getEmails() {
		return emails;
	}

	public void setEmails(Set<Email> emails) {
		this.emails = emails;
	}

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT) 
	public Set<Telephone> getTelephones() {
		return telephones;
	}

	public void setTelephones(Set<Telephone> telephones) {
		this.telephones = telephones;
	}

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT) 
	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(mappedBy = "employeeTeamId.employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT) 
	public Set<EmployeeTeam> getTeams() {
		return teams;
	}

	public void setTeams(Set<EmployeeTeam> teams) {
		this.teams = teams;
	}
}