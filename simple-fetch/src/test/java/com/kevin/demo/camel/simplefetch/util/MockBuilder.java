package com.kevin.demo.camel.simplefetch.util;

import com.kevin.demo.camel.simplefetch.model.jaxb.UserXml;

public class MockBuilder {

    public static UserXml buildUserXml(String[] p) {
        UserXml u = new UserXml();

        u.setWhatever(p[0]);
        u.setFirstName(p[1]);
        u.setLastName(p[2]);
        u.setBirthDate(p[3]);
        u.setEmail(p[4]);

        return u;
    }
}
