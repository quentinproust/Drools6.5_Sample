package drools.sample.regles.test;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import drools.sample.model.*;
import drools.sample.regles.test.core.RulesTestUnit;

public class VerificationJourDeGardeEnDoubleTest extends RulesTestUnit {

	@Before
	public void setUp() {
		super.setUp();
		
		addRessource("workflow.bpmn");
		addRessource("verification_jour_de_garde_en_double.drl");
	}

	@Test
	public void aucunJourDeGardeEnDouble() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		addFact(new JourDeGarde(enfant, LocalDate.parse("2017-01-27")));
		addFact(new JourDeGarde(enfant, LocalDate.parse("2017-01-26")));
		addFact(new JourDeGarde(enfant, LocalDate.parse("2017-02-26")));
		addFact(new JourDeGarde(enfant, LocalDate.parse("2016-01-26")));
			
		runTest();
		
		Assert.assertTrue(getResultsOf(Message.class).isEmpty());
	}

	@Test
	public void presenceJourDeGardeEnDouble() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		addFact(new JourDeGarde(enfant, LocalDate.parse("2017-01-27")));
		addFact(new JourDeGarde(enfant, LocalDate.parse("2017-01-27")));
			
		runTest();
		
		Assert.assertFalse(getResultsOf(Message.class).isEmpty());
	}
}
