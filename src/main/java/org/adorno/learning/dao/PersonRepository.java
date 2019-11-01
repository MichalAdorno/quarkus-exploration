package org.adorno.learning.dao;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import org.adorno.learning.domain.Person;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

@ApplicationScoped
public class PersonRepository implements PanacheRepositoryBase<Person,Long> {

    @Inject
    EntityManager entityManager;

    public Person findByIdForUpdate(Long id){
        Person person = findById(id);
        /*
        lock with the PESSIMISTIC_WRITE mode type :
        this will generate a SELECT ... FOR UPDATE query
         */
        entityManager.lock(person, LockModeType.PESSIMISTIC_WRITE);
        return person;
    }
}