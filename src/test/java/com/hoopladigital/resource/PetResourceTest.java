package com.hoopladigital.resource;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.bean.Pet;
import com.hoopladigital.service.PetService;
import com.hoopladigital.service.PetService;
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
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class PetResourceTest extends AbstractTest {

	@Mock
	private PetService petService;

	private PetResource petResource;

	@Before
	public void beforePetResourceTest() {
		petResource = new PetResource(petService);
	}


	@Test
	public void should_get_pet_list() {

		// setup
		final Pet boomer = new Pet();
		boomer.setId(1L);
		boomer.setPersonId(2L);
		boomer.setName("Boomer");
		final List<Pet> expected = Arrays.asList(boomer);
		when(petService.getPetList(2L)).thenReturn(expected);

		// run test
		final List<Pet> actual = petResource.getPetList(2L);

		// verify mocks / capture values
		verify(petService).getPetList(2L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_get_pet() {

		// setup
		final Pet expected = new Pet();
		expected.setId(1L);
		expected.setPersonId(2L);
		expected.setName("Boomer");
		when(petService.getPet(2L, 1L)).thenReturn(expected);

		// run test
		final Pet actual = petResource.getPet(2L, 1L);

		// verify mocks / capture values
		verify(petService).getPet(2L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_throw_not_found_exc_for_missing_get_pet() {

		// setup
		when(petService.getPet(2L, 1L)).thenReturn(null);

		// run test
		try {
			petResource.getPet(2L, 1L);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(NotFoundException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(petService).getPet(2L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_create_pet() {

		// setup
		final Pet newRecord = new Pet();
		newRecord.setPersonId(2L);
		newRecord.setName("Boomer");

		final Pet expected = new Pet();
		expected.setId(1L);
		expected.setPersonId(2L);
		expected.setName("Boomer");
		when(petService.createPet(2L, newRecord)).thenReturn(expected);

		// run test
		final Pet actual = petResource.createPet(2L, newRecord);

		// verify mocks / capture values
		verify(petService).createPet(2L, newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_update_pet() {

		// setup
		final Pet newRecord = new Pet();
		newRecord.setPersonId(2L);
		newRecord.setName("Barker");

		final Pet oldRecord = new Pet();
		oldRecord.setId(1L);
		oldRecord.setPersonId(2L);
		oldRecord.setName("Boomer");

		final Pet expected = new Pet();
		expected.setId(1L);
		expected.setPersonId(2L);
		expected.setName("Barker");
		when(petService.getPet(2L, 1L)).thenReturn(oldRecord);
		when(petService.updatePet(1L, newRecord)).thenReturn(expected);

		// run test
		final Pet actual = petResource.updatePet(2L, 1L, newRecord);

		// verify mocks / capture values
		verify(petService).getPet(2L, 1L);
		verify(petService).updatePet(1L, newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_throw_not_found_exc_for_missing_update_pet() {

		// setup
		final Pet newRecord = new Pet();
		newRecord.setPersonId(2L);
		newRecord.setName("Barker");

		when(petService.getPet(2L, 1L)).thenReturn(null);

		// run test
		try {
			petResource.updatePet(2L, 1L, newRecord);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(NotFoundException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(petService).getPet(2L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_delete_pet() {

		// setup
		final Pet oldRecord = new Pet();
		oldRecord.setId(1L);
		oldRecord.setPersonId(2L);
		oldRecord.setName("Boomer");

		when(petService.getPet(2L, 1L)).thenReturn(oldRecord);

		// run test
		petResource.deletePet(2L, 1L);

		// verify mocks / capture values
		verify(petService).getPet(2L, 1L);
		verify(petService).deletePet(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_throw_not_found_exc_for_missing_delete_pet() {

		// setup
		when(petService.getPet(2L, 1L)).thenReturn(null);

		// run test
		try {
			petResource.deletePet(2L, 1L);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(NotFoundException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(petService).getPet(2L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}
}
