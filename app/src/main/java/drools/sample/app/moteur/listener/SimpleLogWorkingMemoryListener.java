package drools.sample.app.moteur.listener;

import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLogWorkingMemoryListener implements RuleRuntimeEventListener {
	private static final Logger logger = LoggerFactory
			.getLogger(SimpleLogWorkingMemoryListener.class);
	
	@Override
	public void objectInserted(ObjectInsertedEvent event) {
		logger.info("\t(+) " + event.getObject());
	}

	@Override
	public void objectUpdated(ObjectUpdatedEvent event) {
		logger.info("\t(~) " + event.getObject());
	}

	@Override
	public void objectDeleted(ObjectDeletedEvent event) {
		logger.info("\t(-) " + event.getOldObject());
	}
}
