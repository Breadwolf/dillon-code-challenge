package com.hoopladigital.resource;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PetService;

import javax.inject.Inject;
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

@Path("/")
public class PetResource {

	private final PetService petService;

	@Inject
	public PetResource(final PetService petService) {
		this.petService = petService;
	}

	@GET
	@Produces("application/json")
	public List<Pet> getPetList(@PathParam("personId") final Long personId) {
		return petService.getPetList(personId);
	}

	@GET
	@Path("{petId}")
	@Produces("application/json")
	public Pet getPet(@PathParam("personId") final Long personId, @PathParam("petId") final Long petId) {
		Pet pet = petService.getPet(personId, petId);
		if (pet == null) {
			throw new NotFoundException("Combination of person id " + personId + " and pet id " + petId + " not found");
		}

		return pet;
	}

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Pet createPet(@PathParam("personId") final Long personId, Pet pet) {
		return petService.createPet(personId, pet);
	}

	@PUT
	@Path("{petId}")
	@Consumes("application/json")
	@Produces("application/json")
	public Pet updatePet(@PathParam("personId") final Long personId, @PathParam("petId") final Long petId, Pet pet) {
		if (petService.getPet(personId, petId) == null) {
			throw new NotFoundException("Combination of person id " + personId + " and pet id " + petId + " not found");
		}

		//this makes it possible to assign pets to new owners by changing their personId. If that's not intended
		//then would need some extra validation to make sure it matches personId supplied in path
		return petService.updatePet(petId, pet);
	}

	@DELETE
	@Path("{petId}")
	public void deletePet(@PathParam("personId") final Long personId, @PathParam("petId") final Long petId) {
		if (petService.getPet(personId, petId) == null) {
			throw new NotFoundException("Combination of person id " + personId + " and pet id " + petId + " not found");
		}

		petService.deletePet(petId);
	}
}
