/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author zakesekresa
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Person {

    private static final String FULLNAMEDELIMITER = " ";

    @XmlTransient
    private int id;

    public void setId(int id) {
        this.id = id;
    }
    @XmlElement(name = "firstname")
    private String firstName;
    @XmlElement(name = "lastname")
    private String lastName;

    public Person() {
    }

    public Person(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static Person parsePersonFromString(String fullName) {
        fullName = fullName.trim();
        String firstName = " ";
        String lastName = " ";
        int idx = fullName.lastIndexOf(FULLNAMEDELIMITER);
        if (idx == -1) {
            firstName = fullName;

        } else {
            firstName = fullName.substring(0, idx);
            lastName = fullName.substring(idx + 1);
        }
        return new Person(
                firstName.trim(),
                lastName.trim()
        );
    }

    public static List<Person> parsePersonsFromLine(String line, String delimiter) {

        List<Person> persons = new ArrayList<>();
        String[] personsArray = line.split(delimiter);

        for (String strTemp : personsArray) {
            persons.add(parsePersonFromString(strTemp));
        }
        return persons;
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

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

}
