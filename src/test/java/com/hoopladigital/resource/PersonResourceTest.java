package com.hoopladigital.resource;

import com.hoopladigital.bean.Person;
import com.hoopladigital.service.PersonService;
import com.hoopladigital.test.AbstractTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.List;

import static com.hoopladigital.test.MockHelper.allDeclaredMocks;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PersonResourceTest extends AbstractTest {

	@Mock
	private PersonService personService;

	@Mock
	private PetResource petResource;

	private PersonResource personResource;

	@Before
	public void beforePersonResourceTest() {
		personResource = new PersonResource(personService, petResource);
	}


	@Test
	public void should_get_person_list() {

		// setup
		final Person george = new Person();
		george.setId(1L);
		george.setFirstName("George");
		george.setLastName("Washington");
		final List<Person> expected = Arrays.asList(george);
		when(personService.getPersonList()).thenReturn(expected);

		// run test
		final List<Person> actual = personResource.getPersonList();

		// verify mocks / capture values
		verify(personService).getPersonList();
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
		when(personService.getPerson(1L)).thenReturn(expected);

		// run test
		final Person actual = personResource.getPerson(1L);

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_throw_not_found_exc_for_missing_get_person() {

		// setup
		when(personService.getPerson(1L)).thenReturn(null);

		// run test
		try {
			personResource.getPerson(1L);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(NotFoundException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_create_person() {

		// setup
		final Person newRecord = new Person();
		newRecord.setFirstName("Jim");
		newRecord.setLastName("Jimmington");

		final Person expected = new Person();
		expected.setId(1L);
		expected.setFirstName("Jim");
		expected.setLastName("Jimmington");
		when(personService.createPerson(newRecord)).thenReturn(expected);

		// run test
		final Person actual = personResource.createPerson(newRecord);

		// verify mocks / capture values
		verify(personService).createPerson(newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_update_person() {

		// setup
		final Person newRecord = new Person();
		newRecord.setFirstName("Jim");
		newRecord.setLastName("Jimmington");

		final Person oldRecord = new Person();
		oldRecord.setId(1L);
		oldRecord.setFirstName("George");
		oldRecord.setLastName("Washington");

		final Person expected = new Person();
		expected.setId(1L);
		expected.setFirstName("Jim");
		expected.setLastName("Jimmington");
		when(personService.getPerson(1L)).thenReturn(oldRecord);
		when(personService.updatePerson(1L, newRecord)).thenReturn(expected);

		// run test
		final Person actual = personResource.updatePerson(1L, newRecord);

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verify(personService).updatePerson(1L, newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_update_person_matching_ids() {

		// setup
		final Person newRecord = new Person();
		newRecord.setId(1L);
		newRecord.setFirstName("Jim");
		newRecord.setLastName("Jimmington");

		final Person oldRecord = new Person();
		oldRecord.setId(1L);
		oldRecord.setFirstName("George");
		oldRecord.setLastName("Washington");

		final Person expected = new Person();
		expected.setId(1L);
		expected.setFirstName("Jim");
		expected.setLastName("Jimmington");
		when(personService.getPerson(1L)).thenReturn(oldRecord);
		when(personService.updatePerson(1L, newRecord)).thenReturn(expected);

		// run test
		final Person actual = personResource.updatePerson(1L, newRecord);

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verify(personService).updatePerson(1L, newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_throw_not_found_exc_for_missing_update_person() {

		// setup
		final Person newRecord = new Person();
		newRecord.setFirstName("Jim");
		newRecord.setLastName("Jimmington");

		when(personService.getPerson(1L)).thenReturn(null);

		// run test
		try {
			personResource.updatePerson(1L, newRecord);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(NotFoundException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_throw_bad_request_exc_for_mismatched_id_update_person() {

		// setup
		final Person newRecord = new Person();
		newRecord.setId(2L);
		newRecord.setFirstName("Jim");
		newRecord.setLastName("Jimmington");

		// run test
		try {
			personResource.updatePerson(1L, newRecord);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(BadRequestException.class, e.getClass());
		}

		// verify mocks / capture values
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_delete_person() {

		// setup
		final Person oldRecord = new Person();
		oldRecord.setId(1L);
		oldRecord.setFirstName("George");
		oldRecord.setLastName("Washington");

		when(personService.getPerson(1L)).thenReturn(oldRecord);

		// run test
		personResource.deletePerson(1L);

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verify(personService).deletePerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_throw_not_found_exc_for_missing_delete_person() {

		// setup
		when(personService.getPerson(1L)).thenReturn(null);

		// run test
		try {
			personResource.deletePerson(1L);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(NotFoundException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_return_pet_resource() {

		// setup
		final Person personRecord = new Person();
		personRecord.setId(1L);
		personRecord.setFirstName("George");
		personRecord.setLastName("Washington");
		when(personService.getPerson(1L)).thenReturn(personRecord);

		// run test
		PetResource actualPetResource = personResource.getPetResource(1L);

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		//assert results
		assertEquals(this.petResource, actualPetResource);
	}

	@Test
	public void should_throw_not_found_exc_for_missing_get_pet_resource() {

		// setup
		when(personService.getPerson(1L)).thenReturn(null);

		// run test
		try {
			personResource.getPetResource(1L);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(NotFoundException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(personService).getPerson(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}
}
