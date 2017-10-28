package nl.simeonvandersteen.mindthedelay;

import com.google.common.collect.ImmutableList;
import nl.simeonvandersteen.mindthedelay.domain.Journey;
import org.junit.Test;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class JourneyParserTest {


    @Test
    public void itParsesJourneyHistory() throws Exception {
        File journeyHistory = new File(getClass().getClassLoader().getResource("journey-history.csv").getFile());

        Journey journey1 = new Journey(
                LocalDate.of(2017, 10, 6),
                LocalTime.of(9, 27), LocalTime.of(10, 02),
                "Heathrow Terminal 5 [London Underground]",
                "Oxford Circus");
        Journey journey2 = new Journey(
                LocalDate.of(2017, 10, 2),
                LocalTime.of(19, 55), LocalTime.of(20, 17),
                "Wandsworth Town [National Rail]",
                "Stockwell");

        JourneyParser journeyParser = new JourneyParser(journeyHistory);

        List<Journey> journeys = journeyParser.parse();

        assertEquals(ImmutableList.of(journey1, journey2), journeys);
    }

    @Test(expected = RuntimeException.class)
    public void itThrowsIfFileNotFound() throws Exception {
        File journeyHistory = new File("whatever");

        JourneyParser journeyParser = new JourneyParser(journeyHistory);

        journeyParser.parse();
    }
}