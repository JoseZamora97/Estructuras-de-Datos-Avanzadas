package practicas.practica4.usecase;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

//import practicas.practica4.HashTableMap.HashTableMapSC;
//import practicas.practica4.Map;

public class FlightManager {

    private Map<Passenger, Seat> passengers = new HashMap<>();

    private Map<Flight, Flight> flights = new HashMap<>();

    public void addPassenger(String dni, String name, String surname, Flight flight) {
        Passenger p = new Passenger(dni, name, surname);

        if(!flights.containsKey(flight))
            throw new RuntimeException("The flight doesn't exits.");

        if(flight.getCapacity() == 0)
            throw new RuntimeException("This flight doesn't have capacity for more passengers.");

        if(passengers.containsKey(p)) {
            Seat s = passengers.get(p);
            s.passenger = new Passenger(p);
            passengers.replace(p, s);
        }
        else {
            Seat s = new Seat(p, flight);
            passengers.put(p, s);
            flight.setCapacity(flight.getCapacity() - 1);
        }

        updateFlight(flight.getCompany(), flight.getFlightCode(), flight.getYear(),
                flight.getMonth(), flight.getDay(), flight);

    }

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

    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day) {
        Flight f = new Flight(company, flightCode, year, month, day);

        if (!flights.containsKey(f))
            throw new RuntimeException("The flight doesn't exists.");

        return passengers.values()
                .stream()
                .filter(seat -> seat.flight.equals(f))
                .map(seat -> seat.passenger)
                .collect(Collectors.toList());
    }

    private class Seat {
        private Passenger passenger;
        private Flight flight;
        public Seat(Passenger passenger, Flight flight) {
            this.passenger = passenger;
            this.flight = flight;
        }
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
