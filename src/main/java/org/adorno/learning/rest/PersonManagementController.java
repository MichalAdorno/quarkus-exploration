package org.adorno.learning.rest;

import io.quarkus.panache.common.Parameters;
import org.adorno.learning.dao.PersonRepository;
import org.adorno.learning.domain.Person;
import org.adorno.learning.domain.Status;
import org.adorno.learning.dto.PersonDTO;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;

@Path("/personmgmt")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Transactional
public class PersonManagementController {

    @Inject
    PersonRepository personRepository;
    @Inject
    Validator validator;

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
    public Response createPerson(final PersonDTO newPersonDTO) {

        final Set<ConstraintViolation<PersonDTO>> violations = validator.validate(newPersonDTO);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        final Person newPerson = new Person();
        newPerson.setBirth(newPersonDTO.getBirth());
        newPerson.setName(newPersonDTO.getName());
        newPerson.setStatus(newPersonDTO.getStatus());

        Person.persist(newPerson);
        final Person createdPerson = Person.find(
                "name = :name and status = :status",
                Parameters.with("name", newPersonDTO.getName())
                        .and("status", newPersonDTO.getStatus())
                        .map())
                .firstResult();

        return Response.accepted(createdPerson).build();
    }

}
