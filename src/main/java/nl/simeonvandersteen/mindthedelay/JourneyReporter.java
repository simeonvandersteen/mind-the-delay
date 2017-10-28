package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.Journey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JourneyReporter {

    private static final Logger LOG = LoggerFactory.getLogger(JourneyReporter.class);

    public void report(Journey journey) {
        LOG.info("Delayed journey: {}", journey.toString());
    }
}
