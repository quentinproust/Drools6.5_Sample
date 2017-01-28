package drools.sample.regles.test;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import drools.sample.model.*;
import drools.sample.regles.test.core.RulesTestUnit;

public class VerificationPeriodeEnDoubleTest extends RulesTestUnit {

	@Before
	public void setUp() {
		super.setUp();
		
		addRessource("workflow.bpmn");
		addRessource("verification_periode_en_double.drl");
	}

	@Test
	public void aucunePeriodeEnDouble() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		
		JourDeGarde jourDeGarde1 = new JourDeGarde(enfant, LocalDate.parse("2017-01-27"));
		jourDeGarde1.addPeriode(new Periode("06:00", "12:00"));
		jourDeGarde1.addPeriode(new Periode("13:00", "15:00"));
		addFact(jourDeGarde1);
		
		JourDeGarde jourDeGarde2 = new JourDeGarde(enfant, LocalDate.parse("2017-01-26"));
		jourDeGarde2.addPeriode(new Periode("06:00", "12:00"));
		jourDeGarde2.addPeriode(new Periode("13:00", "15:00"));
		addFact(jourDeGarde2);
		
		runTest();
		
		Assert.assertTrue(getResultsOf(Message.class).isEmpty());
	}

	@Test
	public void presencePeriodeInclusDansUneAutre() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-01-27"));
		jourDeGarde.addPeriode(new Periode("06:00", "12:00"));
		jourDeGarde.addPeriode(new Periode("08:00", "09:00"));
		addFact(jourDeGarde);
			
		runTest();
		
		Assert.assertFalse(getResultsOf(Message.class).isEmpty());
	}
	
	@Test
	public void presencePeriodeChevauchantDansUneAutre() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-01-27"));
		jourDeGarde.addPeriode(new Periode("06:00", "12:00"));
		jourDeGarde.addPeriode(new Periode("11:00", "15:00"));
		addFact(jourDeGarde);
			
		runTest();
		
		Assert.assertFalse(getResultsOf(Message.class).isEmpty());
	}
	
}
