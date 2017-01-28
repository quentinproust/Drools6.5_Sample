package drools.sample.model;

public class TauxHoraire {

   private Contrat contrat;
   private Periode periode;
   private float montant;

   public TauxHoraire(Contrat contrat, Periode periode, float montant) {
      this.contrat = contrat;
      this.periode = periode;
      this.montant = montant;
   }

   public Contrat getContrat() {
      return contrat;
   }

   public Periode getPeriode() {
      return periode;
   }

   public float getMontant() {
      return montant;
   }

   @Override
   public String toString() {
      return "TauxHoraire{" +
            "contrat.enfant=" + contrat.getEnfant() +
            ", periode=" + periode +
            ", montant=" + montant +
            '}';
   }
}
