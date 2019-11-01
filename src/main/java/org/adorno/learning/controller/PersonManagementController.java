package org.adorno.learning.controller;

import io.quarkus.panache.common.Parameters;
import org.adorno.learning.dao.PersonRepository;
import org.adorno.learning.domain.Person;
import org.adorno.learning.domain.Status;
import org.adorno.learning.dto.PersonDTO;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/personmgmt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class PersonManagementController {

    @Inject
    PersonRepository personRepository;

    @GET
    @Path("/all")
    public List<Person> getPersons() {
        return Person.listAll();
    }

    @GET
    @Path("/alive")
    public List<Person> getLivingPersons() {
        return Person.list("status", Status.ALIVE);
    }

    @GET
    @Path("/count")
    public List<Person> getLivingPersons(@QueryParam("isAlive") Boolean isAlive) {
        return isAlive
                ? Person.list("status", Status.ALIVE)
                : Person.list("status", Status.DECEASED);
    }

    @GET
    @Path("/id")
    public Person getById(@PathParam("id") Long id) {
        return personRepository.findByIdForUpdate(id);
    }

    @POST
    @Path("/create")
    public Person createPerson(PersonDTO newPersonDTO) {
        Person newPerson = new Person();
        newPerson.setBirth(newPersonDTO.getBirth());
        newPerson.setName(newPersonDTO.getName());
        newPerson.setStatus(newPersonDTO.getStatus());

        Person.persist(newPerson);
        return Person.find(
                "name = :name and status = :status",
                Parameters.with("name", newPersonDTO.getName())
                        .and("status", newPersonDTO.getStatus())
                        .map())
                .firstResult();
    }

}
