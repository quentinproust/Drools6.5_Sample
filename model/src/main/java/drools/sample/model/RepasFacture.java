package drools.sample.model;

public class RepasFacture {

   private JourDeGarde jourDeGarde;
   private Periode periode;
   private float montant;

   public RepasFacture(JourDeGarde jourDeGarde, Periode periode, float montant) {
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

   public float getMontant() {
      return montant;
   }

   @Override
   public String toString() {
      return "RepasFacture{" +
            "jourDeGarde=[" + jourDeGarde.getEnfant() + " - " + jourDeGarde.getDate() + "]" +
            ", periode=" + periode +
            ", montant=" + montant +
            '}';
   }
}
