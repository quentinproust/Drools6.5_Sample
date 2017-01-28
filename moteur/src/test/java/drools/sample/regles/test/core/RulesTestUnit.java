package drools.sample.regles.test.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Before;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.builder.model.KieSessionModel;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RulesTestUnit {

	private static final Logger logger = LoggerFactory.getLogger(RulesTestUnit.class);
	
	private static final String ERROR_NOT_INITIALISED = "The test was not properly initialised. You need to override setUp. "
			+ "Please add : @Before public void setUp() { super.setUp(); addRessource(\"rule.drl\"); addRessource(\"decisiontable.xls\"); ... }";
	
	private KieFileSystem kieFileSystem;
	private KieServices kieServices;

	private List<Object> facts;
	
	private Collection<? extends Object> resultSession;
	
	@Before
	public void setUp() {
		facts = new ArrayList<>();
		
		kieServices = KieServices.Factory.get();
		KieModuleModel kieModuleModel = kieServices.newKieModuleModel();

		KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel("KBaseForTest").setDefault(true);

		kieBaseModel.newKieSessionModel("KSessionForTest").setDefault(true)
				.setType(KieSessionModel.KieSessionType.STATEFUL);

		kieFileSystem = kieServices.newKieFileSystem();
		kieFileSystem.writeKModuleXML(kieModuleModel.toXML());
	}
	
	private void checkInitialised() {
		Assert.assertNotNull(ERROR_NOT_INITIALISED, kieServices);
		Assert.assertNotNull(ERROR_NOT_INITIALISED, kieFileSystem);
	}
	
	protected String getResourceDefaultPackage() {
		return "drools/sample/regles/";
	}
	
	protected void addRessource(String ressource) {
		checkInitialised();
		
		kieFileSystem.write(kieServices.getResources().newClassPathResource(getResourceDefaultPackage() + ressource));
	}
	
	protected void addFact(Object o) {
		facts.add(o);
	}
	
	protected void runTest() {
		kieServices.newKieBuilder(kieFileSystem).buildAll();
		KieContainer kieContainer = kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

		logger.info("Available kieBases : ");
		for (String n : kieContainer.getKieBaseNames()) {
			logger.info("\t" + n);
		}
		
		logger.info("::>");
		KieBase kieBase = kieContainer.getKieBase();
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
		
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.addEventListener(new DebugAgendaEventListener());
		kieSession.addEventListener(new DebugRuleRuntimeEventListener());
		
		for (Object o : facts) {
			kieSession.insert(o);
		}
			
		for (org.kie.api.definition.process.Process p : kieBase.getProcesses()) {
			logger.info("Starting process #" + p.getId() + " (" + p.getName() + ")");
			kieSession.startProcess(p.getId());
		}
		
		kieSession.fireAllRules();
		
		resultSession = kieSession.getObjects();
	}
	
	protected Collection<? extends Object> getResultSession() {
		Assert.assertNotNull("The test has not run yet. Please call runTest() before.", resultSession);
		return resultSession;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> List<T> getResultsOf(Class<T> laClasse) {
		return getResultSession().stream()
				.filter(p -> p.getClass().equals(laClasse))
				.map(p -> (T) p)
				.collect(Collectors.toList());
	}
	
	protected <T> T getSingleResultOf(Class<T> laClasse) {
		List<T> rs = getResultsOf(laClasse);
		if (rs.isEmpty()) {
			Assert.fail("Aucun élément de type " + laClasse.getName() + " n'a été trouvé.");
		} else if (rs.size() > 1) {
			Assert.fail(rs.size() + " éléments de type " + laClasse.getName() + " ont été trouvé.");
		}
		return rs.get(0);
	}
}
