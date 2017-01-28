package drools.sample.app.moteur.listener;

import org.kie.api.event.rule.AfterMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.runtime.ClassObjectFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import drools.sample.model.Message;

public class StopExecutionAgendaListener extends DefaultAgendaEventListener {
	private static final Logger logger = LoggerFactory
			.getLogger(StopExecutionAgendaListener.class);
	
	@Override
	public void afterMatchFired(AfterMatchFiredEvent event) {
		if (!event.getKieRuntime()
				.getObjects(new ClassObjectFilter(Message.class))
				.isEmpty()) {
			logger.info("Stopping execution !");
			event.getKieRuntime().halt();
			event.getKieRuntime().getAgenda().clear();
		}
	}
}
