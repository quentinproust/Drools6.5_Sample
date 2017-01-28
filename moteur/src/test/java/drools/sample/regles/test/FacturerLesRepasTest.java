package drools.sample.regles.test;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import drools.sample.model.Enfant;
import drools.sample.model.JourDeGarde;
import drools.sample.model.Periode;
import drools.sample.model.RepasFacture;
import drools.sample.regles.test.core.RulesTestUnit;

public class FacturerLesRepasTest extends RulesTestUnit {

	@Before
	public void setUp() {
		super.setUp();
		
		addRessource("workflow.bpmn");
		addRessource("facturer_les_repas.xls");
	}

	@Test
	public void calculerRepasDuMatin() {
		Enfant enfant = new Enfant("Toto");
		addFact(enfant);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-02-08"));
		jourDeGarde.addPeriode(new Periode("07:00", "08:00"));
		addFact(jourDeGarde);
		
		runTest();
		
		RepasFacture repas = getSingleResultOf(RepasFacture.class);
		Assert.assertEquals(new Periode("07:00", "07:45"), repas.getPeriode());
		Assert.assertEquals(2.50f, repas.getMontant(), 0.01f);
	}
	
	@Test
	public void calculerRepasDuMidi() {
		Enfant enfant = new Enfant("Toto");
		addFact(enfant);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-02-08"));
		jourDeGarde.addPeriode(new Periode("11:30", "13:15"));
		addFact(jourDeGarde);
		
		runTest();

		RepasFacture repas = getSingleResultOf(RepasFacture.class);
		Assert.assertEquals(new Periode("12:00", "13:45"), repas.getPeriode());
		Assert.assertEquals(5.50f, repas.getMontant(), 0.01f);
	}
	
	public void calculerRepasDuSoir() {
		Enfant enfant = new Enfant("Toto");
		addFact(enfant);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-02-08"));
		jourDeGarde.addPeriode(new Periode("16:30", "21:15"));
		addFact(jourDeGarde);
		
		runTest();

		RepasFacture repas = getSingleResultOf(RepasFacture.class);
		Assert.assertEquals(new Periode("19:00","20:45"), repas.getPeriode());
		Assert.assertEquals(5.00f, repas.getMontant(), 0.01f);
	}
}
