package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.ExpectedJourney;
import nl.simeonvandersteen.mindthedelay.domain.Journey;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ExpectedJourneyProviderTest {

    public static final LocalTime SOME_TIME = LocalTime.NOON;
    private Journey journey;

    private ExpectedJourneyProvider underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new ExpectedJourneyProvider();

        journey = new Journey(LocalDate.now(), SOME_TIME, SOME_TIME.plusMinutes(10), "a", "b");
    }

    @Test
    public void itReturnsExpectedJourney() throws Exception {
        Journey someOtherJourney = new Journey(LocalDate.now(), SOME_TIME, SOME_TIME.plusMinutes(15), "b", "c");

        underTest.analyse(someOtherJourney);
        underTest.analyse(journey);

        Optional<ExpectedJourney> expectedJourney = underTest.getExpectedJourney(journey);

        assertThat(expectedJourney.isPresent(), is(true));
        assertThat(expectedJourney.get().matches(journey), is(true));
        assertThat(expectedJourney.get().getDuration().toMinutes(), is(10L));
    }

    @Test
    public void itReturnsShortestExpectedJourney() throws Exception {
        Journey shorterJourney = new Journey(LocalDate.now(), SOME_TIME, SOME_TIME.plusMinutes(5), "a", "b");

        underTest.analyse(journey);
        underTest.analyse(shorterJourney);

        Optional<ExpectedJourney> expectedJourney = underTest.getExpectedJourney(journey);

        assertThat(expectedJourney.isPresent(), is(true));
        assertThat(expectedJourney.get().matches(journey), is(true));
        assertThat(expectedJourney.get().getDuration().toMinutes(), is(5L));
    }

    @Test
    public void itParsesExpectedJourneyTimesConfig() throws Exception {
        File configFile = new File(getClass().getClassLoader().getResource("expected-journey-times.json").getFile());

        ExpectedJourneyProvider expectedJourneyProvider = ExpectedJourneyProvider.fromJson(configFile);
        Optional<ExpectedJourney> expectedJourney = expectedJourneyProvider.getExpectedJourney(journey);

        assertThat(expectedJourney.isPresent(), is(true));
        assertThat(expectedJourney.get().matches(journey), is(true));
        assertThat(expectedJourney.get().getDuration().toMinutes(), is(10L));
    }

    @Test(expected = RuntimeException.class)
    public void itThrowsIfIncorrectSyntaxInConfigFile() throws Exception {

        ExpectedJourneyProvider.fromJson(new File("no-json"));
    }

}