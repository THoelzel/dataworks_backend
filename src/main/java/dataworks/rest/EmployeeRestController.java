package dataworks.rest;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.sql.JoinType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dataworks.hibernate.Employee;
import dataworks.hibernate.Team;

@RestController
@CrossOrigin
public class EmployeeRestController {

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	public EmployeeRestController() {
		createSessionFactory();
	}

    /**
     * Adds the employee.
     *
     * @param employee the employee
     * @return the employee
     */
    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public Employee addEmployee(@RequestBody Employee employee) {
    	
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(employee);
        transaction.commit();

        return employee;    
    }
    
    /**
     * Updates the employee.
     *
     * @param employee the employee
     * @return the employee
     */
    @RequestMapping(value = "/employee", method = RequestMethod.PUT)
    public Employee updateEmployee(@RequestBody Employee employee) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(employee);
        transaction.commit();

        return employee;    
    }
    
    /**
     * Updates the team.
     *
     * @param team the team
     * @return the team
     */
    @RequestMapping(value = "/team", method = RequestMethod.PUT)
    public Team updateTeam(@RequestBody Team team) {
    	
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(team);
        transaction.commit();

        return team;    
    }
    
    /**
     * Adds the team.
     *
     * @param team the team
     * @return the team
     */
    @RequestMapping(value = "/team", method = RequestMethod.POST)
    public Team addTeam(@RequestBody Team team) {
    	
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(team);
        transaction.commit();

        return team;    
    }
    
    /**
     * Gets the employee.
     *
     * @param uuid the uuid
     * @return the employee
     */
    @RequestMapping(value = "/employee/{uuid}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable String uuid) {
    	
        Session session = sessionFactory.openSession();
        
        Employee employee = (Employee) session.createCriteria(Employee.class)
        		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
        		.add(Restrictions.eq("id", uuid))
        		.list().get(0);

        return employee;    
    }
    
    /**
     * Deletes the employee.
     *
     * @param uuid the uuid
     */
    @RequestMapping(value = "/employee/{uuid}", method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable String uuid) {
    	
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Employee employee = (Employee) session.get(Employee.class, uuid);
        session.delete(employee);
        session.getTransaction().commit();
    }
    
    /**
     * Deletes the team.
     *
     * @param uuid the uuid
     */
    @RequestMapping(value = "/team/{uuid}", method = RequestMethod.DELETE)
    public void deleteTeam(@PathVariable String uuid) {
    	
        Session session = sessionFactory.openSession();
        
        Transaction transaction = session.beginTransaction();
        String hql = "delete from EmployeeTeam where fk_team_employee_team = :team";
        Query query = session.createQuery(hql);
        query.setString("team", uuid);
        query.executeUpdate();
        transaction.commit();
        transaction = session.beginTransaction();
        Team employee = (Team) session.get(Team.class, uuid);
        session.delete(employee);
        transaction.commit();
    }

    /**
     * Gets the employees.
     *
     * @return the employees
     */
    @RequestMapping(value = "/employee", method = RequestMethod.GET)
    public List<String> getEmployees() {
    	
        Session session = sessionFactory.openSession();
        return session.createCriteria(Employee.class).list();
    }
    
    /**
     * Gets the team.
     *
     * @param uuid the uuid
     * @return the team
     */
    @RequestMapping(value = "/team/{uuid}", method = RequestMethod.GET)
    public Team getTeam(@PathVariable String uuid) {
    	
        Session session = sessionFactory.openSession();
        
        Team team = (Team) session.createCriteria(Team.class)
        		.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
        		.add(Restrictions.eq("id", uuid))
        		.list().get(0);

        return team;    
    }
    
    /**
     * Gets the teams.
     *
     * @return the teams
     */
    @RequestMapping("/team")
    public List<String> getTeams() {
    	
        Session session = sessionFactory.openSession();
        return session.createCriteria(Team.class).list();
    }
    
    /**
     * Gets the employee by team.
     *
     * @param uuid the uuid
     * @return the employee by team
     */
    @RequestMapping("/employee/team/{uuid}")
    public List<String> getEmployeeByTeam(@PathVariable String uuid) {
    	
        Session session = sessionFactory.openSession();
        
        List employees = session.createCriteria(Employee.class)
        		.createAlias("teams", "teams")
        		.add(Restrictions.eq("teams.employeeTeamId.team.id", uuid))
        		.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
        		.list();

        return employees;    
    }

    /**
     * Search for employee.
     *
     * @param searchTerm the search term
     * @return the list
     */
    @RequestMapping("/employee/search/all/{searchTerm}")
    public List<String> searchForEmployee(@PathVariable String searchTerm) {

        Session session = sessionFactory.openSession();

        List employees = session.createCriteria(Employee.class)
        		.createAlias("addresses", "addresses", JoinType.LEFT_OUTER_JOIN)
        		.createAlias("tasks", "tasks", JoinType.LEFT_OUTER_JOIN)
        		.add(Restrictions.disjunction()
        				.add(Restrictions.ilike("firstName", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("middleName", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("lastName", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("title", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("lastName", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("lastName", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("addresses.line1", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("addresses.line2", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("addresses.city", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("addresses.country", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("addresses.city", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("tasks.name", searchTerm, MatchMode.ANYWHERE))
        				.add(Restrictions.ilike("tasks.description", searchTerm, MatchMode.ANYWHERE))
        				)
        		.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
        		.list();
        
        return employees;
    }

    /**
     * Search for employee.
     *
     * @param searchCriteria the search criteria
     * @param searchTerm the search term
     * @return the list
     */
    @RequestMapping("/employee/search/{searchCriteria}/{searchTerm}")
    public List<String> searchForEmployee(@PathVariable String searchCriteria, @PathVariable String searchTerm) {

    	if(!(searchCriteria.equalsIgnoreCase("firstName") || 
    			searchCriteria.equalsIgnoreCase("middleName") ||
    			searchCriteria.equalsIgnoreCase("lastName") ||
    			searchCriteria.equalsIgnoreCase("title") ||
    			searchCriteria.equalsIgnoreCase("gender") ||
    			searchCriteria.equalsIgnoreCase("addresses.line1") ||
    			searchCriteria.equalsIgnoreCase("addresses.line2") ||
    			searchCriteria.equalsIgnoreCase("addresses.city") ||
    			searchCriteria.equalsIgnoreCase("addresses.country") ||
    			searchCriteria.equalsIgnoreCase("tasks.name") ||
    			searchCriteria.equalsIgnoreCase("tasks.description"))
    			) {
    		
    		ArrayList<String> result = new ArrayList<String>();
    		result.add("Invalid searchCriteria.");
    		return result;
    	}
    	
        Session session = sessionFactory.openSession();

        Criteria criteria = session.createCriteria(Employee.class);
        
        if(searchCriteria.equalsIgnoreCase("firstName")) {
        	criteria.add(Restrictions.ilike("firstName", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("middleName")) {
        	criteria.add(Restrictions.ilike("middleName", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("lastName")) {
        	criteria.add(Restrictions.ilike("lastName", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("title")) {
        	criteria.add(Restrictions.ilike("title", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("gender")) {
        	criteria.add(Restrictions.ilike("gender", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("addresses.line1")) {
        	criteria.createAlias("addresses", "addresses")
        			.add(Restrictions.ilike("addresses.line1", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("addresses.line2")) {
        	criteria.createAlias("addresses", "addresses")
        			.add(Restrictions.ilike("addresses.line2", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("addresses.city")) {
        	criteria.createAlias("addresses", "addresses")
        			.add(Restrictions.ilike("addresses.city", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("addresses.country")) {
        	criteria.createAlias("addresses", "addresses")
        			.add(Restrictions.ilike("addresses.country", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("tasks.name")) {
        	criteria.createAlias("tasks", "tasks")
        			.add(Restrictions.ilike("tasks.name", searchTerm, MatchMode.ANYWHERE));
        }
        else if(searchCriteria.equalsIgnoreCase("tasks.description")) {
        	criteria.createAlias("tasks", "tasks")
        			.add(Restrictions.ilike("tasks.description", searchTerm, MatchMode.ANYWHERE));
        }

        List employees = criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();

        return employees;
    }

    /**
     * Creates the session factory.
     *
     * @return the session factory
     */
    public static SessionFactory createSessionFactory() {
    	
    	Configuration configuration = new Configuration();
    	configuration.configure();
    	serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
    	sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    	
    	return sessionFactory;
    }
}