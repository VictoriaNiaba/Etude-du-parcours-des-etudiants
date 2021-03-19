package fr.univamu.epu.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import fr.univamu.epu.model.step.Step;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(GenericJpaDao.class)
class DaoIntegrationTest {
	@Autowired
	private TestEntityManager em;

	private GenericDao<Step, String> dao;

	@Autowired
	public void setDao(GenericDao<Step, String> dao) {
		this.dao = dao;
		dao.setClazz(Step.class);
	}

	@Test
	void whenAdd_thenPersistAndReturnEntity() {
		// given
		Step expected = new Step("SIN3AA", "Licence 3 Informatique");

		// when
		Step added = dao.add(expected);

		// then
		Step actual = em.find(Step.class, expected.getStep_code());
		assertThat(added).isEqualTo(expected);
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void whenAddAll_thenPersistAllEntities() {
		// given
		Collection<Step> expected = Arrays.asList(
				new Step("SIN2AA", "Licence 2 Informatique"),
				new Step("SIN3AA", "Licence 3 Informatique"));

		// when
		dao.addAll(expected);

		// then
		Collection<Step> actual = Arrays.asList(
				em.find(Step.class, "SIN2AA"),
				em.find(Step.class, "SIN3AA"));
		assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
	}

	@Test
	void whenRemove_thenRemoveEntity() {
		// given
		Step expected = new Step("SIN3AA", "Licence 3 Informatique");
		em.persistAndFlush(expected);

		// when
		dao.remove(expected.getStep_code());

		// then
		Step actual = em.find(Step.class, expected.getStep_code());
		assertThat(actual).isNull();
	}

	@Test
	void whenUpdate_thenUpdateAndReturnEntity() {
		// given
		Step expected = new Step("SIN3AA", "Licence 3 Informatique");
		em.persistAndFlush(expected);

		// when
		expected.setStep_name("Licence 3 Mathématiques");
		dao.update(expected);

		// then
		Step actual = em.find(Step.class, expected.getStep_code());
		assertThat(actual.getStep_name()).isEqualTo(expected.getStep_name());
	}

	@Test
	void givenExistingEntity_whenFind_thenReturnEntity() {
		// given
		Step expected = new Step("SIN3AA", "Licence 3 Informatique");
		em.persistAndFlush(expected);

		// when
		Step actual = dao.find(expected.getStep_code());

		// then
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void givenNoMatchingEntities_whenFind_thenReturnNull() {
		// given
		String nonMatchingEntityId = "nonMatchingEntityId";

		// when
		Step actual = dao.find(nonMatchingEntityId);

		// then
		assertThat(actual).isNull();
	}

	@Test
	void whenFindAll_thenReturnAllEntities() {
		// given
		Collection<Step> expected = Arrays.asList(
				new Step("SIN2AA", "Licence 2 Informatique"),
				new Step("SIN3AA", "Licence 3 Informatique"));
		expected.forEach(em::persist);
		em.flush();

		// when
		Collection<Step> actual = dao.findAll();

		// then
		assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
	}
}
