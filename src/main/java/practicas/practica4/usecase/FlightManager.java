package practicas.practica4.usecase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightManager {

    private Map<Passenger, List<Flight>> passengers = new HashMap<>();
    private Map<FlightKey, Flight> flights = new HashMap<>();

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

        if(!uFlight.getFlightKey().equals(fKey)) {
            fKey = uFlight.getFlightKey();
            if(flights.get(fKey) != null)
                throw new RuntimeException("The new flight identifiers are already in use.");

            flights.remove(f.getFlightKey());
            flights.put(fKey, f);
        }

        f.update(uFlight);
    }

    public void addPassenger(String dni, String name, String surname, Flight flight) {
        FlightKey fKey = flight.getFlightKey();
        if(!flights.containsKey(fKey))
            throw new RuntimeException("The flight doesn't exits.");

        if(flight.getCapacity() == 0)
            throw new RuntimeException("This flight doesn't have capacity for more passengers.");

        Passenger p = new Passenger(dni, name, surname);
        List<Flight> passengerFlights = new ArrayList<>();

        if(!passengers.containsKey(p))
            flight.setCapacity(flight.getCapacity() - 1);
        else {
            passengerFlights = passengers.get(p);
            passengers.remove(p);
        }

        updateFlight(fKey.getCompany(), fKey.getFlightCode(), fKey.getYear(),
                fKey.getMonth(), fKey.getDay(), flight);

        passengerFlights.add(flights.get(fKey));
        passengers.put(p, passengerFlights);
    }

    public Iterable<Passenger> getPassengers(String company, int flightCode, int year, int month, int day) {
        Flight f = new Flight(company, flightCode, year, month, day);
        if(!flights.containsKey(f.getFlightKey()))
            throw new RuntimeException("The flight doesn't exists.");

        return passengers.entrySet().stream()
                .filter(entry -> entry.getValue().contains(f))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Iterable<Flight> flightsByDate(int year, int month, int day) {
        return flights.values().stream()
                .filter(x -> x.getYear() == year && x.getMonth() == month && x.getDay() == day)
                .collect(Collectors.toList());
    }

    public Iterable<Flight> getFlightsByPassenger(Passenger passenger) {
        return passengers.containsKey(passenger) ? passengers.get(passenger) : new LinkedList<>();
    }

    public Iterable<Flight> getFlightsByDestination(String destination, int year, int month, int day) {
        List<Flight> flightsByDestination = flights.values().stream()
                .filter(flight -> flight.getDestination().equals(destination))
                .collect(Collectors.toList());

        if(flightsByDestination.isEmpty())
            throw new RuntimeException("The destination doesn't exists.");

        return flightsByDestination.stream()
                .filter(x -> x.getYear() == year && x.getMonth() == month && x.getDay() == day)
                .collect(Collectors.toList());
    }
}
