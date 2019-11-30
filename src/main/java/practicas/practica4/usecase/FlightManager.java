package practicas.practica4.usecase;

import java.util.HashMap;
import java.util.Map;

//import practicas.practica4.HashTableMap.HashTableMapSC;
//import practicas.practica4.Map;

public class FlightManager {

    private Map<Flight, Flight> flights = new HashMap<>();
    private Map<Passenger, Flight> passengers = new HashMap<>();

    public Flight addFlight(String company, int flightCode, int year, int month, int day) {
        Flight flight = new Flight(company, flightCode, year, month, day);
        if(flights.put(flight, flight) != null)
            throw new RuntimeException("The flight already exists.");
        return flight;
    }

    public Flight getFlight(String company, int flightCode, int year, int month, int day) {
        Flight f = flights.get(new Flight(company, flightCode, year, month, day));
        if(f==null)
            throw new RuntimeException("Flight not found.");
        return f.copy();
    }

    public void updateFlight(String company, int flightCode, int year, int month, int day,
                             Flight updatedFlightInfo) {

        Flight f = flights.get(new Flight(company, flightCode, year, month, day));
        if(f==null)
            throw new RuntimeException("The flight doesn't exists and can't be updated.");

        // TODO: arreglar identificadores.
        f.update(updatedFlightInfo);
    }

    public void addPassenger(String dni, String name, String surname, Flight flight) {
        Passenger p = new Passenger();
        p.setDNI(dni);
        p.setName(name);
        p.setSurname(surname);

        passengers.put(p, flight);
    }

    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> flightsByDate(int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> getFlightsByPassenger(Passenger passenger) {
        throw new RuntimeException("Not yet implemented.");
    }

    public Iterable<Flight> getFlightsByDestination(String destination, int year, int month, int day) {
        throw new RuntimeException("Not yet implemented.");
    }
}
