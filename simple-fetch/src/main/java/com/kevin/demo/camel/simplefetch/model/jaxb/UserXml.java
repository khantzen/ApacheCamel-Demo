package com.kevin.demo.camel.simplefetch.model.jaxb;

import javax.xml.bind.annotation.*;

/**
 * Created by khantzen on 14/08/17.
 */
@XmlRootElement(name="info")
@XmlType(propOrder = {
        "whatever",
        "firstName",
        "lastName",
        "birthDate",
        "email"
})
@XmlAccessorType(XmlAccessType.FIELD)
public class UserXml {

    @XmlAttribute(name="whatever")
    private String whatever;

    @XmlElement(name="firstname")
    private String firstName;

    @XmlElement(name="lastname")
    private String lastName;

    @XmlElement(name="birthdate")
    private String birthDate;

    @XmlElement(name="email")
    private String email;

    public UserXml() {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
