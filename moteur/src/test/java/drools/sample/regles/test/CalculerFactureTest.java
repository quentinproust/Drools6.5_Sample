package drools.sample.regles.test;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import drools.sample.model.*;
import drools.sample.regles.test.core.RulesTestUnit;

public class CalculerFactureTest extends RulesTestUnit {

	@Before
	public void setUp() {
		super.setUp();
		
		addRessource("workflow.bpmn");
		addRessource("calculer_facture.drl");
	}

	@Test
	public void calculerFactureAvecHeuresEtRepas() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-01-26"));
		addFact(jourDeGarde);
		addFact(new ElementFacture(null, jourDeGarde, new Periode("06:00", "08:30"), 15.0f));
		addFact(new ElementFacture(null, jourDeGarde, new Periode("12:15", "13:45"), 18.0f));
		addFact(new RepasFacture(jourDeGarde, new Periode("06:00", "08:30"), 1.25f));
		addFact(new RepasFacture(jourDeGarde, new Periode("12:30", "13:30"), 2.25f));
		
		JourDeGarde jourDeGarde2 = new JourDeGarde(enfant, LocalDate.parse("2017-01-27"));
		addFact(jourDeGarde2);
		addFact(new ElementFacture(null, jourDeGarde2, new Periode("05:30", "08:30"), 18.5f));
		addFact(new ElementFacture(null, jourDeGarde2, new Periode("12:15", "13:45"), 18.0f));
		addFact(new RepasFacture(jourDeGarde2, new Periode("06:00", "08:30"), 1.25f));
		addFact(new RepasFacture(jourDeGarde2, new Periode("12:30", "13:30"), 2.25f));
		
		runTest();
		
		Assert.assertEquals(76.5f, getSingleResultOf(Facture.class).getPrixFinal(), 0.01f);
	}

	@Test
	public void calculerFactureAvecHeuresEtSansRepas() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-01-26"));
		addFact(jourDeGarde);
		addFact(new ElementFacture(null, jourDeGarde, new Periode("06:00", "08:30"), 15.0f));
		addFact(new ElementFacture(null, jourDeGarde, new Periode("12:15", "13:45"), 18.0f));
		
		JourDeGarde jourDeGarde2 = new JourDeGarde(enfant, LocalDate.parse("2017-01-27"));
		addFact(jourDeGarde2);
		addFact(new ElementFacture(null, jourDeGarde2, new Periode("05:30", "08:30"), 18.5f));
		addFact(new ElementFacture(null, jourDeGarde2, new Periode("12:15", "13:45"), 18.0f));
		
		runTest();
		
		Assert.assertEquals(69.5f, getSingleResultOf(Facture.class).getPrixFinal(), 0.01f);
	}
	
}
