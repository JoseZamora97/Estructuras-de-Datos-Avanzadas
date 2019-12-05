package practicas.practica5.usecase;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import practicas.Position;
import practicas.practica3.bynarytree.BinaryTree;
import practicas.practica4.usecase.Flight;
import practicas.practica4.usecase.FlightKey;
import practicas.practica5.binarysearchtree.BinarySearchTree;
import practicas.practica5.binarysearchtree.LinkedBinarySearchTree;
import practicas.practica5.dictionary.BSTOrderedDict;
import practicas.practica5.dictionary.OrderedDictionary;


public class FlightQuery {

    /*
     * En cada caso solo se ordena por las claves especificadas.
     * Al buscar por (date1, date1) se deberia devolver Vuelo2 pero al ser la misma fecha se compara
     * por codigo de vuelo y se excluye.
     *
     * Vuelo( date1, code=550) > Vuelo2 (date1, code=0)
     */

    BinarySearchTree<Flight> flights = new LinkedBinarySearchTree<>((f1, f2) -> {
        LocalDate dateF1 = LocalDate.of(f1.getYear(), f1.getMonth(), f1.getDay());
        LocalDate dateF2 = LocalDate.of(f2.getYear(), f2.getMonth(), f2.getDay());
        int dateComparison = dateF1.compareTo(dateF2);
        if(dateComparison != 0) return dateComparison;

        int companyComparison = CharSequence.compare(f1.getCompany(), f2.getCompany());
        if(companyComparison != 0) return companyComparison;

        else return Integer.compare(f1.getFlightCode(), f2.getFlightCode());
    });


    public void addFlight(Flight flight) {
        flights.insert(flight);
    }

    public Iterable<Flight> searchByDates(int start_year, int start_month, int start_day, int end_year, int end_month, int end_day) throws RuntimeException {
        Flight fMin = new Flight(Character.MIN_VALUE+"", Integer.MIN_VALUE, start_year, start_month, start_day);
        Flight fMax = new Flight(Character.MAX_VALUE+"", Integer.MAX_VALUE, end_year, end_month, end_day);
        List<Flight> iterable = new LinkedList<>();
        this.flights.findRange(fMin, fMax).forEach(x -> iterable.add(x.getElement()));
        return iterable;
    }

    public Iterable<Flight> searchByDestinations(String start_destination, String end_destination) throws RuntimeException {

        if (CharSequence.compare(start_destination, end_destination) > 1)
            throw new RuntimeException("Invalid range. (min>max)");
        List<Flight> iterable = new LinkedList<>();
        try {
            flights.successors(flights.first()).forEach(x -> {
                if (CharSequence.compare(x.getElement().getDestination(), start_destination) >= 0
                        && CharSequence.compare(x.getElement().getDestination(), end_destination) <= 0)
                    iterable.add(x.getElement());
            });
        }
        catch (RuntimeException e){}
        return iterable;
    }


    public Iterable<Flight> searchByCompanyAndFLightCode(String start_company, int start_flightCode, String end_company, int end_flightCode) {
        LocalDate date1 = LocalDate.MIN;
        LocalDate date2 = LocalDate.MAX;
        Flight fMin = new Flight(start_company, start_flightCode, date1.getYear(),
                date1.getMonthValue(), date1.getDayOfMonth());
        Flight fMax = new Flight(end_company, end_flightCode, date2.getYear(),
                date2.getMonthValue(), date2.getDayOfMonth());
        List<Flight> iterable = new LinkedList<>();
        this.flights.findRange(fMin, fMax).forEach(x -> iterable.add(x.getElement()));
        return iterable;
    }

}
