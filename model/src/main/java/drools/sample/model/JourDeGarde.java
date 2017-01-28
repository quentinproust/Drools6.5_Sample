package drools.sample.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class JourDeGarde {

	private Enfant enfant;
	private LocalDate date;
	private List<Periode> periodes = new ArrayList<>(); 

	public JourDeGarde(Enfant enfant, LocalDate date) {
		this.enfant = enfant;
		this.date = date;
	}

	public boolean garderEntre(Periode periode) {
		return periodes.stream().anyMatch(p -> p.chevauche(periode));
	}
	
	public Enfant getEnfant() {
		return enfant;
	}

	public LocalDate getDate() {
		return date;
	}

	public void addPeriode(Periode periode) {
		this.periodes.add(periode);
	}

	public List<Periode> getPeriodes() {
		return periodes;
	}

	@Override
	public String toString() {
		List<String> ps = periodes.stream().map(Periode::toString).collect(Collectors.toList());
		return "JourDeGarde [" + enfant + " : " + date + "] : " + 
			String.join(", ", ps);
	}

}
