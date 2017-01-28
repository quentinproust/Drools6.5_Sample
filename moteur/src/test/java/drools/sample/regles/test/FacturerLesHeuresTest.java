package drools.sample.regles.test;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import drools.sample.model.Contrat;
import drools.sample.model.ElementFacture;
import drools.sample.model.Enfant;
import drools.sample.model.JourDeGarde;
import drools.sample.model.Periode;
import drools.sample.model.TauxHoraire;
import drools.sample.regles.test.core.RulesTestUnit;

public class FacturerLesHeuresTest extends RulesTestUnit {

	@Before
	public void setUp() {
		super.setUp();
		
		addRessource("workflow.bpmn");
		addRessource("facturer_les_heures.drl");
	}

	@Test
	public void calculerMontantDunElementFacture() {
		Enfant enfant = new Enfant("Toto");
		addFact(enfant);
		
		Contrat contrat = new Contrat(enfant);
		addFact(contrat);
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-02-08"));
		addFact(jourDeGarde);
		
		TauxHoraire tauxHoraire = new TauxHoraire(contrat, null, 1.5f);
		ElementFacture elementFacture = new ElementFacture(tauxHoraire, jourDeGarde, new Periode("06:00", "08:00"));
		addFact(elementFacture);
		
		runTest();
		
		Assert.assertEquals(3.0f, elementFacture.getMontant(), 0.01f);
	}
	
}
