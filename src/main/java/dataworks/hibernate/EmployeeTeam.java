package dataworks.hibernate;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
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
	@AssociationOverride(name = "pk.employee", 
		joinColumns = @JoinColumn(name = "fk_employee_employee_team")),
	@AssociationOverride(name = "pk.team", 
		joinColumns = @JoinColumn(name = "fk_team_employee_team")) })
//@JsonInclude(Include.NON_EMPTY)
public class EmployeeTeam implements java.io.Serializable {

	private boolean primary;
	private EmployeeTeamId pk = new EmployeeTeamId();
//    private Team team;
//    private Employee employee;
	
	public EmployeeTeam() {
		
	}

//	public EmployeeTeam(boolean primary, Team team, Employee employee) {
//		this.primary = primary;
//		this.team = team;
//		this.employee = employee;
//	}

	@EmbeddedId
	@JsonIgnore
	public EmployeeTeamId getPk() {
		return pk;		
	}
	
	public void setPk(EmployeeTeamId pk) {
		this.pk = pk;
	}
	
	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

//	@Id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_team_employee_team")
	@Transient
	public Team getTeam() {
		return getPk().getTeam();
	}

	public void setTeam(Team team) {
		getPk().setTeam(team);
	}

//	@Id
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "fk_employee_employee_team")
	@JsonIgnore
	@Transient
	public Employee getEmployee() {
		return getPk().getEmployee();
	}

	public void setEmployee(Employee employee) {
		getPk().setEmployee(employee);
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		EmployeeTeam that = (EmployeeTeam) o;

		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
}
