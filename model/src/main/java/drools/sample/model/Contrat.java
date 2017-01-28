package drools.sample.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Contrat {

   private Enfant enfant;
   private List<TauxHoraire> tauxHoraires = new ArrayList<>();

   public Contrat(Enfant enfant) {
      this.enfant = enfant;
   }

   public void addFacturation(Periode p, float tauxHoraire) {
      tauxHoraires.add(new TauxHoraire(this, p, tauxHoraire));
   }

   public List<TauxHoraire> getTauxHoraires() {
      return tauxHoraires;
   }

   public Enfant getEnfant() {
      return enfant;
   }

   @Override
   public String toString() {
      List<String> ts = tauxHoraires.stream().map(TauxHoraire::toString).collect(Collectors.toList());
      return "Contrat{" +
            "enfant=" + enfant +
            ", tauxHoraires=" + String.join(", ", ts) +
            '}';
   }
}
