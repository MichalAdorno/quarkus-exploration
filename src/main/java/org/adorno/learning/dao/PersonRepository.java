package org.adorno.learning.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.adorno.learning.domain.Person;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepository implements PanacheRepositoryBase<Person,Integer> {
    //...
}