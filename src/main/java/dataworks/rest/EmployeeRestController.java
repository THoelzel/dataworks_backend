package dataworks.rest;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.hibernate.sql.JoinType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dataworks.hibernate.Employee;

@RestController
@CrossOrigin
public class EmployeeRestController {

	private static SessionFactory sessionFactory;
	private static ServiceRegistry serviceRegistry;

	public EmployeeRestController() {
		createSessionFactory();
	}

    @RequestMapping("/employee/{uuid}")
    public Employee getEmployee(@PathVariable String uuid) {
    	
        Session session = sessionFactory.openSession();
        
        Employee employee = (Employee) session.createCriteria(Employee.class)
        		.add(Restrictions.eq("id", uuid))
        		.list().get(0);
        
        session.close();
        
        return employee;
    }

    @RequestMapping("/employee")
    public List getEmployees() {
    	
        Session session = sessionFactory.openSession();

        List employees = session.createCriteria(Employee.class).list();
        
        session.close();

        return employees;
    }

    @RequestMapping("/employee/search/all/{searchTerm}")
    public List searchForEmployee(@PathVariable String searchTerm) {

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

        session.close();
        
        return employees;
    }

    @RequestMapping("/employee/search/{searchCriteria}/{searchTerm}")
    public List searchForEmployee(@PathVariable String searchCriteria, @PathVariable String searchTerm) {

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

        session.close();

        return employees;
    }

    public static SessionFactory createSessionFactory() {
    	
    	Configuration configuration = new Configuration();
    	configuration.configure();
    	serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
    	sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    	
    	return sessionFactory;
    }

    @RequestMapping("/running")
    public boolean isRunning() {
        return true;
    }
}