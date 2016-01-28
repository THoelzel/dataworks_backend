package dataworks.hibernate;

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
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "team")
@JsonInclude(Include.NON_EMPTY)
public class Team implements java.io.Serializable {
	
	private String id;
	private String name;
	private String description;
	private Set<EmployeeTeam> employeeTeams = new HashSet<EmployeeTeam>(0);

	public Team() {
		
	}
	
	public Team(String id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public Team(String id, String name, String description, Set<EmployeeTeam> employeeTeams) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.employeeTeams = employeeTeams;
	}
	
	@Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "team_id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

//	@OneToMany(mappedBy = "pk.team", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	@Fetch(FetchMode.SELECT) 
//	public Set<EmployeeTeam> getEmployeeTeams() {
//		return employeeTeams;
//	}
//
//	public void setEmployeeTeams(Set<EmployeeTeam> employeeTeams) {
//		this.employeeTeams = employeeTeams;
//	}
}