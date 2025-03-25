package com.lavalliere.daniel.spring;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class HibernateApplication {

    //  The main contract here is the creation of Session instances.
    //  Usually an application has a single SessionFactory instance
    //  and threads servicing client requests obtain Session instances
    //  from this factory. The internal state of a SessionFactory is immutable.
    //  Once it is created this internal state is set. This internal state includes
    //  all of the metadata about Object/Relational Mapping.
    private static SessionFactory factory;

    // Uses to Create a SessionFactory using the properties and mappings in this configuration.
    // Also create/retrieve an object-oriented view of the configuration properties
    private static ServiceRegistry registry;

    // A Scanner breaks its input into tokens using a delimiter pattern, which by default matches whitespace.
    // The resulting tokens may then be converted into values of different types using the various next methods.
    private static Scanner in = new Scanner(System.in);

    // Method to CREATE an employee in the database
    public Integer addEmployee(){
        System.out.println("Enter first name: ");
        String fname = in.nextLine();
        System.out.println("Enter last name: ");
        String lname = in.nextLine();
        System.out.println("Enter cell: ");
        String cell = in.nextLine();
        System.out.println("Enter home phone: ");
        String hPhone = in.nextLine();
        System.out.println("Enter salary: ");
        int salary = in.nextInt();
        in.nextLine();
        HashSet hs = new HashSet();
        hs.add(new Phone(cell));
        hs.add(new Phone(hPhone));

        // Open a session based on the current configuration
        Session session = factory.openSession();

        // Create a transaction to used for all operations
        Transaction tx = null;

        Integer employeeID = null;
        try{
            // Begin the transaction
            tx = session.beginTransaction();

            // create a new instance of Employee based on what the user entered
            Employee employee = new Employee(fname, lname, salary);
            employee.setPhones(hs);

            // Save the message to the database. The created id is returned
            employeeID = (Integer) session.save(employee);

            // Commit to make sure we do not loose the save of the user's new message
            tx.commit();

        }catch (HibernateException e) {
            // If any error occured, rollback if we had created the transactions
            if (tx!=null) tx.rollback();
            e.printStackTrace();

        }finally {
            // Always close the session in any case (success/errors)
            session.close();
        }
        return employeeID;
    }

    // Method to print all the employees
    public void listEmployees( ){

        // Open a session based on the current configuration
        Session session = factory.openSession();

        Transaction tx = null;
        try{
            // Begin the transaction
            tx = session.beginTransaction();

            // Get all the employess from db and return it as a list
            List employees = session.createQuery("FROM Employee").list();

            // Iterate through all the employees and print them
            for (Iterator iterator1 = employees.iterator(); iterator1.hasNext();){
                Employee employee = (Employee) iterator1.next();
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
                Set phoneNums = employee.getPhones();
                for (Iterator iterator2 =
                     phoneNums.iterator(); iterator2.hasNext();){
                    Phone phoneNum = (Phone) iterator2.next();
                    System.out.println("Phone: " + phoneNum.getPhoneNumber());
                }
            }

            // This is to prevent tale data when reading
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }
    }

    // Method to UPDATE salary for an employee
    public void updateEmployee(Integer EmployeeID, int salary ){

        // Open a session based on the current configuration
        Session session = factory.openSession();

        Transaction tx = null;
        try{
            // Begin the transaction
            tx = session.beginTransaction();

            // Get a specific employee and update it
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);
            employee.setSalary( salary );

            // Save the employee to the database. The created id is returned
            session.update(employee);

            // Commit to make sure we do not loose the save of the user's new employee
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }
    }

    // Method to DELETE an employee from the records
    public void deleteEmployee(Integer EmployeeID){

        // Open a session based on the current configuration
        Session session = factory.openSession();

        Transaction tx = null;
        try{
            // Begin the transaction
            tx = session.beginTransaction();

            // Get a specific employee to delete
            Employee employee = (Employee)session.get(Employee.class, EmployeeID);

            // Delete the employee to the database.
            session.delete(employee);

            // Commit to make sure we do not loose the delet of the user's new employee
            tx.commit();

        }catch (HibernateException e) {
            if (tx!=null) tx.rollback();

            // Get error if try to delete not existing (BUT MYSQL WOULD NOT !!)
            // Exception in thread "main" java.lang.IllegalArgumentException: attempt to create delete event with null entity
            e.printStackTrace();

        }finally {
            session.close();
        }
    }

    // For infomration only
    public void sampleHQLQuery() {

        Session session = factory.openSession();

        // An example using Criterias to query
        // CriteriaQuery<Employee> cq = session.getCriteriaBuilder().createQuery(Employee.class);
        // cq.from(Employee.class);
        // List<Employee> employees = session.createQuery(cq).getResultList();

        // NOTE: the following changed between release 5 and the previous release of Hibernate
        // An example using Criterias to query with restrictions to query results
        // CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        // CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        // Root<User> root = criteriaQuery.from(User.class);
        // criteriaQuery.select(root);
        // criteriaQuery.where(criteriaBuilder.equal(root.get(property), constraintValue));
        // Query<SELECTVALUETYPE> query = session.createQuery(criteriaQuery);

        try{
            // HQL Examples
            // You can specify the entire query if required but be carefull, normally we assume all the columns are returned
            // so that is why we assign it to our Employee object but if getting just the name, for example, then you would
            // have a list of string, not objects
            Query query = session.createQuery("from Employee as e where e.firstName " +
                                                 "like 'S%' and salary > 10000");

            // Example query using named parameters
            // String hql = "from employee where salary > :salary";
            // query = session.createQuery(hql);
            // query.setInteger("salary", 90000);

            // You could also use: int val = (int)query.uniqueResult(); when expecting a single value (as int in this case)
            List employees = query.list();



            for (Iterator iterator = employees.iterator(); iterator.hasNext();){
                // Do something
            }

        }catch (HibernateException e) {
            e.printStackTrace();

        }finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        try{
            // factory = new Configuration().configure().buildSessionFactory();

            // Setting up the configuration to connect to the rdbms databse
            // Using the mappings and properties specified in an application resource
            // named hibernate.cfg.xml.

            // NOTE: It is important to add all the resource configuration here as they are not
            //       correctly detected (at least with the IntelliJ setup) if hibernate is left
            //       to scan these files itself
            Configuration configuration = new Configuration().addResource("Employee.hbm.xml").configure();

            // Apply a groups of incoming setting values.
            registry = new StandardServiceRegistryBuilder().applySettings(
                    configuration.getProperties()).build();

            // Instantiate a new SessionFactory object, using the registry we just created
            factory = configuration.buildSessionFactory(registry);

        }catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        // Create an instance of our main application class
        HibernateApplication app = new HibernateApplication();
        String more = "yes";
        Integer empID=0;

        // Add few employee records in database
        while(more.charAt(0) == 'y' || more.charAt(0) =='Y')
        {
            empID = app.addEmployee();
            System.out.println("More employees? (y/n)");

            // We have to use in.nextLine because I'm reading in the salary as an integer.
            // And what happens when you do that is that it leaves the in-line (ie: CRLF) symbol
            // in the buffer and if I don't do in.nextLine, it's going to cause a problem because
            // that's going to be read (ie: CRLF) as the next first name or as the value for whether or not to continue.
            more = in.nextLine();
        }

        // Update employee's records
        app.updateEmployee(1, 95000);

        // Delete an employee from the database
        app.deleteEmployee(2);

        // List all the employees
        app.listEmployees();

        // Close the secciong builder to clean everything up
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
