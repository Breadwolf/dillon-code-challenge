package com.hoopladigital.mapper;

import com.hoopladigital.bean.Pet;

import java.util.List;

public interface PetMapper {
	List<Pet> getPetList(Long personId);
	Pet getPet(Long personId, Long petId);
	int insertPet(Pet pet);
	int updatePet(Pet pet);
	int deletePet(Long petId);
}

