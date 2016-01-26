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
@Table(name = "task")
@JsonInclude(Include.NON_EMPTY)
public class Task {
	private String id;
	private boolean primary;
	private Employee employee;
	private String name;
	private String description;

	public Task() {
		
	}

	public Task(String id, boolean primary, Employee employee, String name, String description) {
		this.id = id;
		this.primary = primary;
		this.employee = employee;
		this.name = name;
		this.description = description;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!this.getClass().equals(obj.getClass())) return false;

		Task newObj = (Task)obj;
		if((this.id == newObj.getId()) && (this.name.equals(newObj.getName()))) {
			return true;
		}
		return false;
	}
	
	public int hashCode() {
		int result = 0;
		result = (id + name).hashCode();
		return result;
	}

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    @Column(name = "task_id", unique = true)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_employee_task", nullable = false)
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
}