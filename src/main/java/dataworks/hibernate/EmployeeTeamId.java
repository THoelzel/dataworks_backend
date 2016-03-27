package dataworks.hibernate;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class EmployeeTeamId implements java.io.Serializable {

	private Team team;
	private Employee employee;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeTeamId that = (EmployeeTeamId) o;

        if (employee != null ? !employee.equals(that.employee) : that.employee != null) return false;
        if (team != null ? !team.equals(that.team) : that.team != null)
            return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = (employee != null ? employee.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        return result;
    }
}
