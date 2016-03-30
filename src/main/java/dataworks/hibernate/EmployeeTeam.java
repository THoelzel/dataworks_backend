package dataworks.hibernate;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "employee_team")
@AssociationOverrides({
	@AssociationOverride(name = "employeeTeamId.employee", 
		joinColumns = @JoinColumn(name = "fk_employee_employee_team")),
	@AssociationOverride(name = "employeeTeamId.team", 
		joinColumns = @JoinColumn(name = "fk_team_employee_team")) })
public class EmployeeTeam implements java.io.Serializable {

	private boolean primary;
	private EmployeeTeamId employeeTeamId = new EmployeeTeamId();
	
	public EmployeeTeam() {
		
	}

	@EmbeddedId
	@JsonIgnore
	public EmployeeTeamId getEmployeeTeamId() {
		return employeeTeamId;		
	}
	
	public void setEmployeeTeamId(EmployeeTeamId employeeTeamId) {
		this.employeeTeamId = employeeTeamId;
	}
	
	@Column(name = "primary_team")
	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	@Transient
	public Team getTeam() {
		return getEmployeeTeamId().getTeam();
	}

	public void setTeam(Team team) {
		getEmployeeTeamId().setTeam(team);
	}

	@JsonIgnore
	@Transient
	public Employee getEmployee() {
		return getEmployeeTeamId().getEmployee();
	}

	public void setEmployee(Employee employee) {
		getEmployeeTeamId().setEmployee(employee);
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EmployeeTeam that = (EmployeeTeam) o;

		if (getEmployeeTeamId() != null ? !getEmployeeTeamId().equals(that.getEmployeeTeamId())
				: that.getEmployeeTeamId() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getEmployeeTeamId() != null ? getEmployeeTeamId().hashCode() : 0);
	}
}
