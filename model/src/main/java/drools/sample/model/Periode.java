package drools.sample.model;

import java.time.Duration;
import java.time.LocalTime;
public class Periode {

	private LocalTime debut, fin;

	public Periode(String debut, String fin) {
		this(LocalTime.parse(debut), LocalTime.parse(fin));
	}
	
	public Periode(LocalTime debut, LocalTime fin) {
		this.debut = debut;
		this.fin = fin;
	}

	public LocalTime getDebut() {
		return debut;
	}

	public LocalTime getFin() {
		return fin;
	}

   public float getNombreHeures() {
      return ((float) Duration.between(debut, fin).toMinutes()) / 60f;
   }

   public boolean chevauche(Periode p) {
      return p.debut.isBefore(fin) && debut.isBefore(p.fin);
   }

   public boolean inclus(Periode p) {
      return debut.isBefore(p.debut) && fin.isAfter(p.fin);
   }

   public Periode getPeriodeContenue(Periode p) {
      return new Periode(
            debut.isBefore(p.debut) ? p.debut : debut,
            fin.isAfter(p.fin) ? p.fin : fin);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof Periode)) return false;

      Periode periode = (Periode) o;

      if (!debut.equals(periode.debut)) return false;
      if (!fin.equals(periode.fin)) return false;

      return true;
   }

   @Override
   public int hashCode() {
      int result = debut.hashCode();
      result = 31 * result + fin.hashCode();
      return result;
   }

   @Override
	public String toString() {
		return "Periode [" + debut + " -> " + fin + "]";
	}

}
