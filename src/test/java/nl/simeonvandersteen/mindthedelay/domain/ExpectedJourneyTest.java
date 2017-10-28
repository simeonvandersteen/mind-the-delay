package nl.simeonvandersteen.mindthedelay.domain;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class ExpectedJourneyTest {

    private ExpectedJourney expectedJourney = new ExpectedJourney("a", "b", 1);

    @Test
    public void itMatchesSameJourney() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.now(), LocalTime.now(), "a", "b");

        assertThat(expectedJourney.matches(journey), is(true));
    }

    @Test
    public void itMatchesJourneyInReverseDirection() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.now(), LocalTime.now(), "b", "a");

        assertThat(expectedJourney.matches(journey), is(true));
    }

    @Test
    public void itDoesNotMatchJourneyFromOtherOrigin() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.now(), LocalTime.now(), "a", "c");

        assertThat(expectedJourney.matches(journey), is(false));
    }

    @Test
    public void itDoesNotMatchJourneyFromOtherDestination() throws Exception {
        Journey journey = new Journey(LocalDate.now(), LocalTime.now(), LocalTime.now(), "z", "b");

        assertThat(expectedJourney.matches(journey), is(false));
    }
}