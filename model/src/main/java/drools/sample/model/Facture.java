package drools.sample.model;

public class Facture {

   private Enfant enfant;
	private float prixFinal;

   public Facture(Enfant enfant, float prixFinal) {
      this.enfant = enfant;
      this.prixFinal = prixFinal;
   }

   public Enfant getEnfant() {
      return enfant;
   }

   public float getPrixFinal() {
      return prixFinal;
   }

   @Override
	public String toString() {
		return "Facture [" + enfant + "] : " + prixFinal;
	}

}
