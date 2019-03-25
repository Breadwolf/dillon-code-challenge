package com.hoopladigital.resource;

import com.hoopladigital.bean.Person;
import com.hoopladigital.service.PersonService;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/people")
public class PersonResource {

	private final PersonService personService;
	private final PetResource petResource;

	@Inject
	public PersonResource(final PersonService personService, final PetResource petResource) {
		this.personService = personService;
		this.petResource = petResource;
	}

	@GET
	@Produces("application/json")
	public List<Person> getPersonList() {
		return personService.getPersonList();
	}

	@GET
	@Path("{personId}")
	@Produces("application/json")
	public Person getPerson(@PathParam("personId") final Long personId) {
		final Person person = personService.getPerson(personId);
		if (person == null) {
			throw new NotFoundException("Invalid person id " + personId);
		} else {
			return person;
		}
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Person createPerson(Person person) {
		//making an assumption that we can ignore the id field of the incoming json, since we expect to create a brand new entity
		return personService.createPerson(person);
	}

	@PUT
	@Path("{personId}")
	@Consumes("application/json")
	@Produces("application/json")
	public Person updatePerson(@PathParam("personId") final Long personId, Person person) {

		//we could just ignore the incoming id (like in create), but I feel that this mismatch is more serious and may be
		//a bug on the client side. They are likely trying to update the wrong record with the wrong data
		if (person.getId() != null && person.getId() != personId) {
			throw new BadRequestException("URL path person id " + personId + " does not match payload person id " + person.getId());
		}

		//this is extra overhead to fulfill the 404 requirement. update will tell us that 0 rows were affected if id
		//is not found, but it may also fail for other reasons
		if (personService.getPerson(personId) == null) {
			throw new NotFoundException("Person id " + personId + " not found");
		}

		return personService.updatePerson(personId, person);
	}

	@DELETE
	@Path("{personId}")
	public void deletePerson(@PathParam("personId") final Long personId) {
		if (personService.getPerson(personId) == null) {
			throw new NotFoundException("Person id " + personId + " not found");
		}

		personService.deletePerson(personId);
	}

	@Path("{personId}/pets")
	public PetResource getPetResource(@PathParam("personId") final Long personId) {
		if (personService.getPerson(personId) == null) {
			throw new NotFoundException("Person id " + personId + " not found");
		}
		return petResource;
	}
}
