package drools.sample.regles.test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import drools.sample.model.*;
import drools.sample.regles.test.core.RulesTestUnit;

public class DecoupagePeriodeSelonTauxHoraireTest extends RulesTestUnit {

	@Before
	public void setUp() {
		super.setUp();
		
		addRessource("workflow.bpmn");
		addRessource("decoupage_periode_selon_taux_horaire.drl");
	}

	@Test
	public void decouperPeriodes() {
		Enfant enfant = new Enfant("enfant");
		addFact(enfant);
		
		Contrat contrat = new Contrat(enfant);
		contrat.addFacturation(new Periode("06:15", "07:25"), 1.25f);
		contrat.addFacturation(new Periode("07:25", "09:25"), 1.75f);
		addFact(contrat);
		contrat.getTauxHoraires().forEach(x -> addFact(x));
		
		JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse("2017-01-26"));
		jourDeGarde.addPeriode(new Periode("05:15", "10:25"));
		addFact(jourDeGarde);
			
		runTest();
		
		List<ElementFacture> elements = getResultsOf(ElementFacture.class);
		Assert.assertEquals(2, elements.size());
		Collections.sort(elements, (a, b) -> a.getPeriode().getDebut().compareTo(b.getPeriode().getDebut()));
		Assert.assertEquals(new Periode("06:15", "07:25"), elements.get(0).getPeriode());
		Assert.assertEquals(new Periode("07:25", "09:25"), elements.get(1).getPeriode());
	}
	
}
