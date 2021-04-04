package com.lavalliere.daniel.spring;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

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

    public static void main(String[] args) {

        // Get a message to enter into the database:
        // A simple text scanner which can parse primitive types and strings using regular expressions.
        //
            // A Scanner breaks its input into tokens using a delimiter pattern, which by default matches whitespace.
            // The resulting tokens may then be converted into values of different types using the various next methods.
        Scanner in = new Scanner(System.in);
        String m = "";
        System.out.println("Enter a message: ");
        m = in.nextLine();

        try {
            // Setting up the configuration to connect to the rdbms databse
            // Using the mappings and properties specified in an application resource
            // named hibernate.cfg.xml.

            // NOTE: It is important to add all the resource configuration here as they are not
            //       correctly detected (at least with the IntelliJ setup) if hibernate is left
            //       to scan these files itself
            Configuration conf = new Configuration().addResource("Message.hbm.xml").configure();

            // Apply a groups of incoming setting values.
            registry = new StandardServiceRegistryBuilder().applySettings(
                    conf.getProperties()).build();

            // Instantiate a new SessionFactory object, using the registry we just created
            factory = conf.buildSessionFactory(registry);

        } catch (Throwable ex){
            // Protect against any (most likely database related) errors
            System.err.println("Failed to create session factory object"+ex);
            throw new ExceptionInInitializerError(ex);
        }

        // Open a session based on the previous configuration
        Session session = factory.openSession();

        // Create a transaction to used for all operations
        Transaction tx = null;

        // represent the message primary key
        Short msgId = null;


        try{
            // Begin the transaction
            tx = session.beginTransaction();

            // create a new instance of message based on what the user entered
            Message msg = new Message(m);

            // Save the message to the database. The created id is returned
            msgId = (Short) session.save(msg);

            // An HQL query (similar to SQL). In this case we get all
            // from the table and convert it to a list. No need to worry about ResultSets
            // as it is managed by Hibernate itself
            List messages = session.createQuery("FROM Message").list();

            // Printout all the messages from the table
            for(Iterator iterator = messages.iterator(); iterator.hasNext();) {
                // Returned is Object so need to cast it out
                Message message = (Message)iterator.next();
                System.out.println("message: "+message.getMessage());
            }

            // Commit to make sure we do not loose the save of the user's new message
            tx.commit();
        } catch(HibernateException e) {
            // If any error occured, rollback if we had created the transactions
            if (tx != null) tx.rollback();
            e.printStackTrace();

        }finally{
            // Always close the session in any case (success/errors)
            session.close();
        }

        // Close the secciong builder to clean everything up
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
