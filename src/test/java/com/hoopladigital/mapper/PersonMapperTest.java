package com.hoopladigital.mapper;

import com.hoopladigital.bean.Person;
import com.hoopladigital.test.AbstractMapperTest;
import org.junit.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.Assert.*;

public class PersonMapperTest extends AbstractMapperTest {

	@Inject
	private PersonMapper personMapper;

	@Test
	public void should_get_person_list() throws Exception {

		// setup
		final Person george = new Person();
		george.setId(1L);
		george.setFirstName("George");
		george.setLastName("Washington");

		// run test
		final List<Person> personList = personMapper.getPersonList();

		// verify mocks / capture values

		// assert results
		assertEquals(10, personList.size());
		beanTestHelper.diffBeans(george, personList.get(0));
	}

	@Test
	public void should_get_person() throws Exception {

		// setup
		final Person george = new Person();
		george.setId(1L);
		george.setFirstName("George");
		george.setLastName("Washington");

		// run test
		final Person person = personMapper.getPerson(1L);

		// verify mocks / capture values

		// assert results
		beanTestHelper.diffBeans(george, person);
	}

	@Test
	public void should_insert_person_and_set_id() throws Exception {

		// setup
		final Person mrBean = new Person();
		mrBean.setId(1L); //should get overwritten
		mrBean.setFirstName("Rowan");
		mrBean.setLastName("Atkinson");

		// run test
		final int recordCount = personMapper.insertPerson(mrBean);

		// verify mocks / capture values
		Person queriedPerson = personMapper.getPerson(11L);

		// assert results
		assertEquals(1, recordCount);
		assertEquals((Long)11L, mrBean.getId());
		beanTestHelper.diffBeans(mrBean, queriedPerson);
	}

	@Test
	public void should_update_person() throws Exception{

		// setup
		final Person formerGeorge = new Person();
		formerGeorge.setId(1L);
		formerGeorge.setFirstName("Jim");
		formerGeorge.setLastName("Jimmington");

		// run test
		final int recordCount = personMapper.updatePerson(formerGeorge);
		// verify mocks / capture values
		Person queriedPerson = personMapper.getPerson(1L);

		// assert results
		assertEquals(1, recordCount);
		beanTestHelper.diffBeans(formerGeorge, queriedPerson);
	}

	@Test
	public void should_delete_person() throws Exception{

		// setup

		// run test
		final int recordCount = personMapper.deletePerson(1L);
		// verify mocks / capture values
		Person queriedPerson = personMapper.getPerson(1L);

		// assert results
		assertEquals(1, recordCount);
		assertNull(queriedPerson);
	}
}
