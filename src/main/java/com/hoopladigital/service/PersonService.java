package com.hoopladigital.service;

import com.hoopladigital.bean.Person;
import com.hoopladigital.mapper.PersonMapper;

import javax.inject.Inject;
import java.util.List;

public class PersonService {

	private final PersonMapper personMapper;

	@Inject
	public PersonService(final PersonMapper personMapper) {
		this.personMapper = personMapper;
	}

	public List<Person> getPersonList() {
		return personMapper.getPersonList();
	}

	public Person getPerson(Long personId) {
		return personMapper.getPerson(personId);
	}

	public Person createPerson(Person person) {
		if (personMapper.insertPerson(person) > 0) {
			return person;
		} else {
			//would probably want to fail more gracefully here in the real world, maybe find out why it failed, etc.
			throw new RuntimeException("Failed to insert person record into database");
		}
	}

	public Person updatePerson(Person person) {
		if (personMapper.updatePerson(person) > 0) {
			return person;
		} else {
			throw new RuntimeException("Failed to update person record with id " + person.getId() + " in database");
		}
	}

	public void deletePerson(Long personId) {
		if (personMapper.deletePerson(personId) == 0) {
			throw new RuntimeException("Failed to delete person record with id " + personId + " from database");
		}
	}
}
