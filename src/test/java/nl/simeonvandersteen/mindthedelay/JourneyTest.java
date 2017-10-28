package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.Journey;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class JourneyTest {

    @Test
    public void itCalculatesDuration() throws Exception {
        LocalTime start = LocalTime.of(11, 11);
        LocalTime end = LocalTime.of(13, 12);

        Journey underTest = new Journey(LocalDate.now(), start, end, "bla", "bla");

        assertThat(underTest.getDuration(), is(Duration.between(start, end)));
    }
}