package drools.sample.model;

public class Enfant {

	private String nom;

	public Enfant(String nom) {
		this.nom = nom;
	}

	public String getNom() {
		return this.nom;
	}

	@Override
	public String toString() {
		return "Enfant : " + nom;
	}

}
