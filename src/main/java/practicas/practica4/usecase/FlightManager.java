package practicas.practica4.usecase;


import java.util.ArrayList;
import java.util.List;

import practicas.practica4.HashTableMap.HashTableMapSC;
import practicas.practica4.Map;

public class FlightManager {

    private Map<Passenger, Seat> passengers = new HashTableMapSC<>();
    private Map<FlightKey, Flight> flights = new HashTableMapSC<>();

    public Flight addFlight(String company, int flightCode, int year, int month, int day) {
        FlightKey fKey = new FlightKey(company, flightCode, year, month, day);
        Flight flight = flights.get(fKey);

        if(flight!=null)
            throw new RuntimeException("The flight already exists.");

        flight = new Flight(fKey);
        flights.put(fKey, flight);
        return flight;
    }

    public Flight getFlight(String company, int flightCode, int year, int month, int day) {
        FlightKey fKey = new FlightKey(company, flightCode, year, month, day);
        Flight f = flights.get(fKey);

        if(f == null)
            throw new RuntimeException("Flight not found.");

        return f.copy();
    }

    public void updateFlight(String company, int flightCode, int year, int month, int day,
                             Flight uFlight) {

        FlightKey fKey = new FlightKey(company, flightCode, year, month, day);
        Flight f = flights.get(fKey);

        if(f == null)
            throw new RuntimeException("The flight doesn't exists and can't be updated.");

        f.update(uFlight);
    }

    public void addPassenger(String dni, String name, String surname, Flight flight) {
        FlightKey fKey = flight.getFlightKey();
        Flight f = flights.get(fKey);
        if(f == null)
            throw new RuntimeException("The flight doesn't exits.");
        if(flight.getCapacity() == 0)
            throw new RuntimeException("This flight doesn't have capacity for more passengers.");

        Passenger p = new Passenger(dni, name, surname);
        Seat s = passengers.get(p);

        if(s != null) {
            s.passenger = new Passenger(p);
            passengers.put(p, s);
        }
        else {
            passengers.put(p, new Seat(p, flight));
            flight.setCapacity(flight.getCapacity() - 1);
        }

        updateFlight(flight.getCompany(), flight.getFlightCode(), flight.getYear(),
                flight.getMonth(), flight.getDay(), flight);

    }

    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day) {
        FlightKey fKey = new Flight(company, flightCode, year, month, day);
        Flight f = flights.get(fKey);
        if(f == null)
            throw new RuntimeException("The flight doesn't exists.");

        List<Passenger> passengersList = new ArrayList<>();
        for(Seat s : passengers.values())
            if(s.flight.equals(f))
                passengersList.add(s.passenger);

        return passengersList;
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
