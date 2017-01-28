package drools.sample.app;

import java.io.IOException;

import drools.sample.app.moteur.Moteur;
import drools.sample.app.moteur.Session;
import drools.sample.model.Calcul;

public class SimulateurFacturation {
	
	private Moteur moteur = new Moteur();
	
	public static void main(String[] args) throws IOException {
		new SimulateurFacturation().run();
	}

	private void run() throws IOException {
		Calcul calcul = new Calcul().builder().pourEnfant("Toto")
				.avecTauxHoraire("05:00", "20:00", 3.0f) // jour
				.avecTauxHoraire("20:00", "05:00", 4.35f) // nuit
				.garderLe("2017-01-12")
					.de("06:00").a("09:00")
					.de("18:00").a("20:00")
				.etGarderLe("2017-01-13")
					.de("06:00").a("09:00")
					.de("18:00").a("20:00")
				.build();
		Session session = moteur.createSession();
		session.insertFacts(calcul);
		session.run();
		
		if (session.hasErrors()) {
			System.err.println("Erreurs dans la facturation : ");
			session.getMessages().forEach(m -> System.err.println("\t - " + m));
		} else {
			System.out.println("Facturation des jours de garde : ");
			session.getFactures().forEach(f -> System.out.print("\t - " + f));
		}
	}

}
