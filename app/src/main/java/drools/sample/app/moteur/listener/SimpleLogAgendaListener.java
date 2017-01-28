package drools.sample.app.moteur.listener;

import org.kie.api.event.rule.BeforeMatchFiredEvent;
import org.kie.api.event.rule.DefaultAgendaEventListener;
import org.kie.api.event.rule.RuleFlowGroupActivatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLogAgendaListener extends DefaultAgendaEventListener {
	private static final Logger logger = LoggerFactory
			.getLogger(SimpleLogAgendaListener.class);
	
	@Override
	public void beforeRuleFlowGroupActivated(
			RuleFlowGroupActivatedEvent event) {
		logger.info("Entering rule flow "
				+ event.getRuleFlowGroup().getName());
	}

	@Override
	public void beforeMatchFired(BeforeMatchFiredEvent event) {
		logger.info("=> " + event.getMatch().getRule().getName());
	}
}
