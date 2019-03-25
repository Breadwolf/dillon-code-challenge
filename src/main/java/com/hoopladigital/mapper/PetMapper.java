package com.hoopladigital.mapper;

import com.hoopladigital.bean.Pet;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetMapper {
	List<Pet> getPetList(Long personId);
	Pet getPet(@Param("personId") Long personId, @Param("petId") Long petId);
	int insertPet(Pet pet);
	int updatePet(Pet pet);
	int deletePet(Long petId);
}

