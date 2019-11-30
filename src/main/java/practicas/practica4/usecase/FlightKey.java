package practicas.practica4.usecase;

import java.time.LocalDate;
import java.util.Objects;

public class FlightKey {

    protected String company;
    protected int flightCode;
    protected LocalDate date;

    public FlightKey() {}

    public FlightKey(String company, int flightCode, int year, int month, int day) {
        this.company = company;
        this.flightCode = flightCode;
        this.date = LocalDate.of(year, month, day);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightKey flightKey = (FlightKey) o;
        return flightCode == flightKey.flightCode &&
                company.equals(flightKey.company) &&
                date.equals(flightKey.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(company, flightCode, date);
    }
}
