package com.victor.learn.algafoodapi.domain.model;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType
public class Customer {

    private String firstName;
    private String last_name;
    private int id;

    public Customer() {
    }

    public Customer(String firstName, String last_name, int id) {
        this.firstName = firstName;
        this.last_name = last_name;
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

}
