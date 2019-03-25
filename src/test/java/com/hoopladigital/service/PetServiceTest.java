package com.hoopladigital.service;

import com.hoopladigital.bean.Pet;
import com.hoopladigital.mapper.PetMapper;
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

public class PetServiceTest extends AbstractTest {

	@Mock
	private PetMapper petMapper;
	private PetService petService;

	@Before
	public void beforePetServiceTest() {
		petService = new PetService(petMapper);
	}

	@Test
	public void should_get_pet_list() {

		// setup
		final List<Pet> expected = Collections.emptyList();
		when(petMapper.getPetList(2L)).thenReturn(expected);

		// run test
		final List<Pet> actual = petService.getPetList(2L);

		// verify mocks / capture values
		verify(petMapper).getPetList(2L);
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
		when(petMapper.getPet(2L, 1L)).thenReturn(expected);

		// run test
		final Pet actual = petService.getPet(2L, 1L);

		// verify mocks / capture values
		verify(petMapper).getPet(2L, 1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		assertEquals(expected, actual);
	}

	@Test
	public void should_create_pet_and_set_person_id() throws Exception {

		// setup
		final Pet newRecord = new Pet();
		newRecord.setName("Boomer");

		final Pet expected = new Pet();
		expected.setId(1L);
		expected.setPersonId(2L);
		expected.setName("Boomer");
		when(petMapper.insertPet(newRecord)).thenAnswer(invocation -> {
			((Pet)invocation.getArguments()[0]).setId(1L); //simulate the side effect of auto-generated key being set
			return 1;
		});

		// run test
		final Pet actual = petService.createPet(2L, newRecord);

		// verify mocks / capture values
		verify(petMapper).insertPet(newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		beanTestHelper.diffBeans(expected, actual);
	}

	@Test
	public void should_throw_exc_if_create_pet_failed() {

		// setup
		final Pet newRecord = new Pet();
		newRecord.setPersonId(2L);
		newRecord.setName("Boomer");

		when(petMapper.insertPet(newRecord)).thenReturn(0);

		// run test
		try {
			petService.createPet(2L, newRecord);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(RuntimeException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(petMapper).insertPet(newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_update_pet_and_set_id() throws Exception {

		// setup
		final Pet newRecord = new Pet();
		newRecord.setPersonId(2L);
		newRecord.setName("Boomer");

		final Pet expected = new Pet();
		expected.setId(1L);
		expected.setPersonId(2L);
		expected.setName("Boomer");
		when(petMapper.updatePet(any(Pet.class))).thenReturn(1);

		// run test
		final Pet actual = petService.updatePet(1L, newRecord);

		// verify mocks / capture values
		ArgumentCaptor<Pet> petCaptor = ArgumentCaptor.forClass(Pet.class);
		verify(petMapper).updatePet(petCaptor.capture());
		verifyNoMoreInteractions(allDeclaredMocks(this));

		// assert results
		beanTestHelper.diffBeans(expected, actual);
		beanTestHelper.diffBeans(expected, petCaptor.getValue());
	}

	@Test
	public void should_throw_exc_if_update_pet_failed() {

		// setup
		final Pet newRecord = new Pet();
		newRecord.setId(1L);
		newRecord.setPersonId(2L);
		newRecord.setName("Boomer");

		when(petMapper.updatePet(newRecord)).thenReturn(0);

		// run test
		try {
			petService.updatePet(1L, newRecord);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(RuntimeException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(petMapper).updatePet(newRecord);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_delete_pet() {

		// setup
		when(petMapper.deletePet(1L)).thenReturn(1);

		// run test
		petService.deletePet(1L);

		// verify mocks / capture values
		verify(petMapper).deletePet(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}

	@Test
	public void should_throw_exc_if_delete_pet_failed() {

		// setup
		when(petMapper.deletePet(1L)).thenReturn(0);

		// run test
		try {
			petService.deletePet(1L);
			fail("Should have thrown an exception");
		} catch (Exception e) {
			assertEquals(RuntimeException.class, e.getClass());
		}

		// verify mocks / capture values
		verify(petMapper).deletePet(1L);
		verifyNoMoreInteractions(allDeclaredMocks(this));
	}
}
