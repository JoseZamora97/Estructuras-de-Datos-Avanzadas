package practicas.practica4.usecase;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class Flight {

    private String company;
    private int flightCode;
    private LocalDate date;
    private LocalDateTime time;

    private String origin;
    private String destination;
    private int delay;
    private int capacity;

    private Map<String, String> properties;

    public Flight() {
        properties = new ConcurrentHashMap<>();
    }

    public Flight(String company, int flightCode, int year, int month, int day) {
        this.company = company;
        this.flightCode = flightCode;
        this.date = LocalDate.of(year, month, day);
        properties = new ConcurrentHashMap<>();
    }

    public Flight copy() {
        Flight copy = new Flight();
        copy.setCompany(company);
        copy.setFlightCode(flightCode);
        copy.setDate(getYear(), getMonth(), getDay());
        return copy;
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(int flightCode){
        this.flightCode = flightCode;
    }

    public void setDate(int year, int month, int day) {
        this.date = LocalDate.of(year, month, day);
    }

    public int getYear() {
        return this.date.getYear();
    }

    public int getMonth() {
        return this.date.getMonthValue();
    }

    public int getDay() {
        return this.date.getDayOfMonth();
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return getFlightCode() == flight.getFlightCode() &&
                getCompany().equals(flight.getCompany());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCompany(), getFlightCode(), date);
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
        company = f.company;
        flightCode = f.flightCode;
        date = f.date;
        time = f.time;
        origin = f.origin;
        destination = f.destination;
        delay = f.delay;
        capacity = f.capacity;
        properties = f.properties;
    }
}
