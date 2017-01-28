package drools.sample.model;

public class ElementFacture {

	private JourDeGarde jourDeGarde;
	private Periode periode;
	private TauxHoraire tauxHoraire;
	private float montant;

	public ElementFacture(TauxHoraire tauxHoraire, JourDeGarde jourDeGarde,
			Periode periode) {
		this.tauxHoraire = tauxHoraire;
		this.jourDeGarde = jourDeGarde;
		this.periode = periode;
	}

	public ElementFacture(TauxHoraire tauxHoraire, JourDeGarde jourDeGarde,
			Periode periode, float montant) {
		this.tauxHoraire = tauxHoraire;
		this.jourDeGarde = jourDeGarde;
		this.periode = periode;
		this.montant = montant;
	}

	public JourDeGarde getJourDeGarde() {
		return jourDeGarde;
	}

	public Periode getPeriode() {
		return periode;
	}

	public TauxHoraire getTauxHoraire() {
		return tauxHoraire;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	@Override
	public String toString() {
		return "ElementFacture{" + "jourDeGarde=[" + jourDeGarde.getEnfant()
				+ " - " + jourDeGarde.getDate() + "]" + ", periode=" + periode
				+ ", tauxHoraire=" + tauxHoraire + ", montant=" + montant + '}';
	}
}
