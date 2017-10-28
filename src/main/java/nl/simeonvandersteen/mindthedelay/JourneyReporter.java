package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.Journey;

public class JourneyReporter {

    public void report(Journey journey) {
        System.out.println(String.format("Delayed journey:\n%s\n", journey.toString()));
    }
}
