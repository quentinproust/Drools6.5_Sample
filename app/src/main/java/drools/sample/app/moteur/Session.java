package drools.sample.app.moteur;

import java.util.ArrayList;
import java.util.List;

import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;

import drools.sample.model.Calcul;
import drools.sample.model.Facture;
import drools.sample.model.Message;

public class Session {

	private KieSession kieSession;
	private List<String> processes;
	private List<Facture> factures = new ArrayList<>();
	private List<Message> messages = new ArrayList<>();
	
	public Session(KieSession kieSession, List<String> processes) {
		this.kieSession = kieSession;
		this.processes = processes;
	}
	
	public void insertFacts(Calcul calcul) {
		kieSession.insert(calcul.getContrat());
		calcul.getContrat().getTauxHoraires().forEach(x -> kieSession.insert(x));
		kieSession.insert(calcul.getEnfant());
		calcul.getJoursDeGarde().forEach(x -> kieSession.insert(x));
	}
	
	public void run() {
		processes.forEach(p -> kieSession.startProcess(p));
		kieSession.fireAllRules();
		
		messages.addAll(getObjects(Message.class));
		factures.addAll(getObjects(Facture.class));
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T> List<T> getObjects(Class<? extends T> laClasse) {
		return (List<T>)new ArrayList(kieSession.getObjects(new ClassObjectFilter(laClasse)));
	}
	
	public boolean hasErrors() {
		return !messages.isEmpty();
	}

	public List<Facture> getFactures() {
		return factures;
	}

	public List<Message> getMessages() {
		return messages;
	}
}
