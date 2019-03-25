package com.hoopladigital.mapper;

import com.hoopladigital.bean.Person;

import java.util.List;

public interface PersonMapper {

	//personally would prefer using annotations here, but as instructed will follow established pattern
	//and configure in XML, since getPersonList() already configured that way
	List<Person> getPersonList();
	Person getPerson(Long personId);
	int insertPerson(Person person);
	int updatePerson(Person person);
	int deletePerson(Long personId);
}

