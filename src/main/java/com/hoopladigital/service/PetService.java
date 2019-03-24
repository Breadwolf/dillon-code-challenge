package com.hoopladigital.service;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.mapper.PetMapper;

import javax.inject.Inject;
import java.util.List;

public class PetService {

	private final PetMapper petMapper;

	@Inject
	public PetService(final PetMapper petMapper) {
		this.petMapper = petMapper;
	}

	public List<Pet> getPetList(Long personId) {
		return petMapper.getPetList(personId);
	}

	public Pet getPet(Long personId, Long petId) {
		return petMapper.getPet(personId, petId);
	}

	public Pet createPet(Long personId, Pet pet) {
		pet.setPersonId(personId);

		if (petMapper.insertPet(pet) > 0) {
			return pet;
		} else {
			//would probably want to fail more gracefully here in the real world, maybe find out why it failed, etc.
			throw new RuntimeException("Failed to insert pet record into database");
		}
	}

	public Pet updatePet(Pet pet) {
		if (petMapper.updatePet(pet) > 0) {
			return pet;
		} else {
			throw new RuntimeException("Failed to update pet record with id " + pet.getId() + " in database");
		}
	}

	public void deletePet(Long petId) {
		if (petMapper.deletePet(petId) == 0) {
			throw new RuntimeException("Failed to delete pet record with id " + petId + " from database");
		}
	}
}
