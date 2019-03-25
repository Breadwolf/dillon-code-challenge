package com.hoopladigital.service;

import com.hoopladigital.bean.Person;
import com.hoopladigital.mapper.PersonMapper;
import com.hoopladigital.test.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PersonServiceTest extends AbstractTest {

	@Mock
	private PersonMapper personMapper;
	private PersonService personService;

	@Before
	public void beforePersonServiceTest() {
		personService = new PersonService(personMapper);
	}

	@Test
	public void should_get_person_list() {

		// setup
		final List<Person> expected = Collections.emptyList();
		when(personMapper.getPersonList()).thenReturn(expected);

		// run test
		final List<Person> actual = personService.getPersonList();

		// verify mocks / capture values
		verify(personMapper).getPersonList();
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);

	}

	@Test
	public void should_get_person() {

		// setup
		final Person expected = new Person();
		expected.setId(1L);
		expected.setFirstName("George");
		expected.setLastName("Washington");
		when(personMapper.getPerson(1L)).thenReturn(expected);

		// run test
		final Person actual = personService.getPerson(1L);

		// verify mocks / capture values
		verify(personMapper).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_create_person() throws Exception {

		// setup
		final Person newRecord = new Person();
		newRecord.setFirstName("George");
		newRecord.setLastName("Washington");

		final Person expected = new Person();
		expected.setId(1L);
		expected.setFirstName("George");
		expected.setLastName("Washington");
		when(personMapper.insertPerson(newRecord)).thenAnswer(invocation -> {
			((Person)invocation.getArguments()[0]).setId(1L); //simulate the side effect of auto-generated key being set
			return 1;
		});

		// run test
		final Person actual = personService.createPerson(newRecord);

		// verify mocks / capture values
		verify(personMapper).insertPerson(newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		beanTestHelper.diffBeans(expected, actual);
	}

	@Test
	public void should_throw_exc_if_create_person_failed() {

		// setup
		final Person newRecord = new Person();
		newRecord.setFirstName("George");
		newRecord.setLastName("Washington");

		when(personMapper.insertPerson(newRecord)).thenReturn(0);

		// run test
		try {
			personService.createPerson(newRecord);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(RuntimeException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(personMapper).insertPerson(newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_update_person_and_set_id() throws Exception {

		// setup
		final Person newRecord = new Person();
		newRecord.setFirstName("Jim");
		newRecord.setLastName("Jimmington");

		final Person expected = new Person();
		expected.setId(1L);
		expected.setFirstName("Jim");
		expected.setLastName("Jimmington");
		when(personMapper.updatePerson(any(Person.class))).thenReturn(1);

		// run test
		final Person actual = personService.updatePerson(1L, newRecord);

		// verify mocks / capture values
		ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
		verify(personMapper).updatePerson(personCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		beanTestHelper.diffBeans(expected, actual);
		beanTestHelper.diffBeans(expected, personCaptor.getValue());
	}

	@Test
	public void should_throw_exc_if_update_person_failed() {

		// setup
		final Person newRecord = new Person();
		newRecord.setId(1L);
		newRecord.setFirstName("George");
		newRecord.setLastName("Washington");

		when(personMapper.updatePerson(newRecord)).thenReturn(0);

		// run test
		try {
			personService.updatePerson(1L, newRecord);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(RuntimeException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(personMapper).updatePerson(newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_delete_person() {

		// setup
		when(personMapper.deletePerson(1L)).thenReturn(1);

		// run test
		personService.deletePerson(1L);

		// verify mocks / capture values
		verify(personMapper).deletePerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_throw_exc_if_delete_person_failed() {

		// setup
		when(personMapper.deletePerson(1L)).thenReturn(0);

		// run test
		try {
			personService.deletePerson(1L);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(RuntimeException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(personMapper).deletePerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}
}
