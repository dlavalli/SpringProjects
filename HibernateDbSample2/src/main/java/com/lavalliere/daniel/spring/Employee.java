package com.lavalliere.daniel.spring;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int salary;

    // IMPORTANT : The name of properties must match those mentionned in the Employee.hbm.xml file
    //             and you must have a getter and setter (since it is a bean) matching the
    //             property name
    //             ELSE you will get the error:   Failed to create sessionFactory object.org.hibernate.MappingException:
    //                                            Could not get constructor for org.hibernate.persister.entity.SingleTableEntityPersister


    // This is to represent the relation with Phone table
    // Where we may have several phone numbers for a given employee
    private Set phones;

    public Employee() {
    }

    public Employee(String firstName, String lastName, int salary)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }


    public Set getPhones() {
        return phones;
    }
    public void setPhones(Set phones) {
        this.phones = phones;
    }
}
