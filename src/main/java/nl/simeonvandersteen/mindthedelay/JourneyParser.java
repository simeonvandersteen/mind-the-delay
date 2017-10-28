package nl.simeonvandersteen.mindthedelay;

import nl.simeonvandersteen.mindthedelay.domain.Journey;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class JourneyParser {

    private final File journeyHistory;

    public JourneyParser(File journeyHistory) {
        this.journeyHistory = journeyHistory;
    }

    public List<Journey> parse() {
        List<Journey> journeys = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(journeyHistory))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Journey journey;

                if ((journey = parseJourney(line)) != null) {
                    journeys.add(journey);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Loading journey history failed: " + e.getMessage());
        }

        return journeys;
    }

    private Journey parseJourney(String line) {
        String[] fields = line.split(",");

        LocalDate date;
        LocalTime start;
        LocalTime end;

        try {
            date = LocalDate.parse(fields[0], DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            start = LocalTime.parse(fields[1]);
            end = LocalTime.parse(fields[2]);
        } catch (DateTimeParseException e) {
            return null;
        }

        String[] fromTo = fields[3].substring(1, fields[3].length() - 1).split(" to ");

        if (fromTo.length < 2) {
            return null;
        }

        return new Journey(date, start, end, fromTo[0], fromTo[1]);
    }
}
