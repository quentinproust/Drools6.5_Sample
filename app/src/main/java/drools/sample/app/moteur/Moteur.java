package drools.sample.app.moteur;

import java.util.List;
import java.util.stream.Collectors;

import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import drools.sample.app.moteur.listener.SimpleLogAgendaListener;
import drools.sample.app.moteur.listener.SimpleLogWorkingMemoryListener;
import drools.sample.app.moteur.listener.StopExecutionAgendaListener;

public class Moteur {

	private static final Logger logger = LoggerFactory
			.getLogger(Moteur.class);
	
	private KieBase kieBase;
	private KieContainer kContainer;

	public Moteur() {
		KieServices kieServices = KieServices.Factory.get();
		kContainer = kieServices.getKieClasspathContainer();
		kieBase = kContainer.getKieBase();
	}
	
	public Session createSession() {
		KieSession kieSession = kContainer.newKieSession();
		kieSession.addEventListener(new StopExecutionAgendaListener());
		kieSession.addEventListener(new SimpleLogAgendaListener());
		kieSession.addEventListener(new SimpleLogWorkingMemoryListener());
		
		List<String> processes = kieBase.getProcesses().stream()
				.map(p -> p.getId())
				.collect(Collectors.toList());
		return new Session(kieSession, processes);
	}
	
	public void logMoteurInformations() {
		logger.info("Available kieBases : ");
		for (String n : kContainer.getKieBaseNames()) {
			logger.info("\t" + n);
		}

		logger.info("KBaseSimulateur ::>");
		for (KiePackage p : kieBase.getKiePackages()) {
			logger.info("KieBase - package : " + p.getName());
			logger.info("\trules : ");
			for (Rule r : p.getRules()) {
				logger.info("\t\t" + r.getName());
			}

			logger.info("\tprocess : ");
			for (org.kie.api.definition.process.Process pr : p.getProcesses()) {
				logger.info("\t\t" + pr.getName());
			}
		}
	}

}
