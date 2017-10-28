package nl.simeonvandersteen.mindthedelay.domain;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Journey {

    private final LocalDate date;
    private final LocalTime start;
    private final LocalTime end;

    private final String origin;
    private final String destination;

    public Journey(LocalDate date, LocalTime start, LocalTime end, String origin, String destination) {
        this.date = date;
        this.start = start;
        this.end = end;
        this.origin = origin;
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public Duration getDuration() {
        return Duration.between(start, end);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Journey journey = (Journey) o;

        if (!date.equals(journey.date)) return false;
        if (!start.equals(journey.start)) return false;
        if (!end.equals(journey.end)) return false;
        if (!origin.equals(journey.origin)) return false;
        return destination.equals(journey.destination);
    }

    @Override
    public int hashCode() {
        int result = date.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + end.hashCode();
        result = 31 * result + origin.hashCode();
        result = 31 * result + destination.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("Start: %s\n" +
                        "Finish: %s\n" +
                        "Date: %s\n" +
                        "Time: %s - %s\n" +
                        "Duration: %s",
                origin, destination,
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                start.toString(), end.toString(),
                LocalTime.MIN.plus(getDuration()).toString()
        );
    }
}
