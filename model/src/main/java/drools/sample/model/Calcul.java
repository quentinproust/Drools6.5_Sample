package drools.sample.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Calcul {

   private Enfant enfant;
   private Contrat contrat;
   private List<JourDeGarde> joursDeGarde = new ArrayList<>();

   public Enfant getEnfant() {
      return enfant;
   }

   public Contrat getContrat() {
      return contrat;
   }

   public List<JourDeGarde> getJoursDeGarde() {
      return joursDeGarde;
   }

   public CalculBuilder builder() {
      return new CalculBuilder();
   }

   public class CalculBuilder {

      public CalculBuilder pourEnfant(String nom) {
         enfant = new Enfant(nom);
         return this;
      }

      public CalculBuilder avecTauxHoraire(String debut, String fin, float montant) {
         if (contrat == null) {
            contrat = new Contrat(enfant);
         }
         contrat.addFacturation(new Periode(LocalTime.parse(debut), LocalTime.parse(fin)), montant);
         return this;
      }

      public JourDeGardeBuilder garderLe(String jour) {
         JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse(jour));
         joursDeGarde.add(jourDeGarde);
         return new JourDeGardeBuilder(jourDeGarde);
      }
   }

   public class JourDeGardeBuilder {
      private JourDeGarde jourDeGarde;
      private LocalTime debut;
      private JourDeGardeBuilder(JourDeGarde j) {
         jourDeGarde = j;
      }

      public JourDeGardeBuilder de(String heureDebut) {
         debut = LocalTime.parse(heureDebut);
         return this;
      }

      public JourDeGardeBuilder a(String heureFin) {
         jourDeGarde.addPeriode(new Periode(debut, LocalTime.parse(heureFin)));
         debut = null;
         return this;
      }

      public JourDeGardeBuilder etGarderLe(String jour) {
         JourDeGarde jourDeGarde = new JourDeGarde(enfant, LocalDate.parse(jour));
         joursDeGarde.add(jourDeGarde);
         return new JourDeGardeBuilder(jourDeGarde);
      }

      public Calcul build() {
         return Calcul.this;
      }
   }

}
