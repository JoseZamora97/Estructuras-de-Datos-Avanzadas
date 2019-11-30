package practicas.practica4.usecase;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Flight extends FlightKey{

    private LocalDateTime time;
    private String origin;
    private String destination;
    private int delay;
    private int capacity;

    private Map<String, String> properties;

    public Flight() {
        super();
        properties = new ConcurrentHashMap<>();
    }

    public Flight(String company, int flightCode, int year, int month, int day) {
        super(company,flightCode, year, month, day);
        properties = new ConcurrentHashMap<>();
    }

    public Flight(FlightKey f) {
        this(f.getCompany(), f.getFlightCode(), f.getYear(), f.getMonth(), f.getDay());
    }

    public Flight copy() {
        Flight copy = new Flight();
        copyFlight(this, copy);
        return copy;
    }

    private void copyFlight(Flight src, Flight dst) {
        dst.company = src.company;
        dst.flightCode = src.flightCode;
        dst.date = src.date;
        dst.time = src.time;
        dst.origin = src.origin;
        dst.destination = src.destination;
        dst.delay = src.delay;
        dst.capacity = src.capacity;
        dst.properties = src.properties;
    }

    public void setTime(int hours, int minutes) {
        this.time = this.date.atTime(hours, minutes);
    }

    public int getHours() {
        return time.getHour();
    }

    public int getMinutes() {
        return time.getMinute();
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setProperty(String attribute, String value) {
        this.properties.put(attribute, value);
    }

    public String getProperty(String attribute) {
        return this.properties.get(attribute);
    }

    public Iterable<String> getAllAttributes() {
        return this.properties.keySet();
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append(getDay()).append("-").append(getMonth()).append("-").append(getYear());
        sb.append("\t").append(getCompany()).append(getFlightCode());

        if(time != null)
            sb.append("\t").append(getHours()).append(":").append(getMinutes());

        if(origin != null)
            sb.append("\t").append(getOrigin());

        if(destination != null)
            sb.append("\t").append(getDestination());

        if(delay != 0)
            sb.append("\t" + "DELAYED (").append(getDelay()).append("min)");

        return sb.toString();
    }

    public void update(Flight f) {
        copyFlight(f, this);
    }

    public FlightKey getFlightKey() {
        return new FlightKey(this.getCompany(), this.getFlightCode(), this.getYear()
                , this.getMonth(), this.getDay());
    }
}
