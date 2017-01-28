package drools.sample.regles.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import drools.sample.model.Contrat;
import drools.sample.model.Enfant;
import drools.sample.model.Message;
import drools.sample.regles.test.core.RulesTestUnit;

public class VerificationPresenceContratTest extends RulesTestUnit {

	@Before
	public void setUp() {
		super.setUp();
		
		addRessource("workflow.bpmn");
		addRessource("verification_presence_contrat.drl");
	}

	@Test
	public void presenceDunContrat() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		addFact(new Contrat(enfant));
		
		runTest();
		
		Assert.assertTrue(getResultsOf(Message.class).isEmpty());
	}
	
	@Test
	public void absenceDeContrat() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		
		runTest();
		
		Assert.assertFalse(getResultsOf(Message.class).isEmpty());
	}

}
