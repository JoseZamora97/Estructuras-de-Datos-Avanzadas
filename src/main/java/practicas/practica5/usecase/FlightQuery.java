package practicas.practica5.usecase;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import practicas.practica4.usecase.Flight;
import practicas.practica5.binarysearchtree.BinarySearchTree;
import practicas.practica5.binarysearchtree.LinkedBinarySearchTree;


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

    LocalDate lDate = LocalDate.MIN;
    LocalDate hDate = LocalDate.MAX;
    String hCompany = Character.MIN_VALUE + "";
    String lCompany = Character.MAX_VALUE + "";
    int hFlightCode = 0;
    int lFlightCode = 0;

    public void addFlight(Flight flight) {
        flights.insert(flight);

        LocalDate fDate = LocalDate.of(flight.getYear(), flight.getMonth(), flight.getDay());
        String fCompany = flight.getCompany();
        int fFlightCode = flight.getFlightCode();

        if(flights.size() == 1) {
            lDate = hDate = fDate;
            lCompany = hCompany = fCompany;
            lFlightCode = hFlightCode = fFlightCode;
        }
        else {
            lDate = (lDate.isBefore(fDate)) ? lDate : fDate;
            hDate = (hDate.isAfter(fDate)) ? hDate : fDate;

            lCompany = (lCompany.compareTo(fCompany) < 0) ? lCompany : fCompany;
            hCompany = (hCompany.compareTo(fCompany) > 0) ? hCompany : fCompany;

            lFlightCode = Math.min(lFlightCode, fFlightCode);
            hFlightCode = Math.max(hFlightCode, fFlightCode);
        }
    }

    public Iterable<Flight> searchByDates(int start_year, int start_month, int start_day, int end_year, int end_month, int end_day) throws RuntimeException {
        Flight fMin = new Flight(lCompany, lFlightCode, start_year, start_month, start_day);
        Flight fMax = new Flight(hCompany, hFlightCode, end_year, end_month, end_day);
        List<Flight> iterable = new LinkedList<>();
        this.flights.findRange(fMin, fMax).forEach(x -> iterable.add(x.getElement()));
        return iterable;
    }

    public Iterable<Flight> searchByDestinations(String start_destination, String end_destination) throws RuntimeException {
        if (CharSequence.compare(start_destination, end_destination) > 1)
            throw new RuntimeException("Invalid range. (min>max)");

        Flight fMin = new Flight(lCompany, lFlightCode, lDate.getYear(),
                lDate.getMonthValue(), lDate.getDayOfMonth());
        Flight fMax = new Flight(hCompany, hFlightCode, hDate.getYear(),
                hDate.getMonthValue(), hDate.getDayOfMonth());

        List<Flight> iterable = new LinkedList<>();
        this.flights.findRange(fMin, fMax).forEach(x -> {
            if (CharSequence.compare(x.getElement().getDestination(), start_destination) >= 0
                    && CharSequence.compare(x.getElement().getDestination(), end_destination) <= 0)
                iterable.add(x.getElement());
        });
        return iterable;
    }

    public Iterable<Flight> searchByCompanyAndFLightCode(String start_company, int start_flightCode, String end_company, int end_flightCode) {

        Flight fMin = new Flight(start_company, start_flightCode,0,1,1);
        Flight fMax = new Flight(end_company, end_flightCode,0,1,1);

        if((start_company.compareTo(end_company) > 0
                || (start_company.compareTo(end_company) == 0 && end_flightCode <= start_flightCode))) {
            flights.findRange(fMin, fMax);
        }

        fMin.setDate(lDate.getYear(), lDate.getMonthValue(), lDate.getDayOfMonth());
        fMax.setDate(hDate.getYear(), hDate.getMonthValue(), hDate.getDayOfMonth());

        List<Flight> iterable = new LinkedList<>();
        flights.findRange(fMin, fMax).forEach(flight -> {
            boolean comp1 = start_company.compareTo(flight.getElement().getCompany()) < 0;
            boolean comp2 = end_company.compareTo(flight.getElement().getCompany()) >= 0;
            boolean comp3 = start_company.compareTo(flight.getElement().getCompany()) == 0;
            boolean comp4 = start_flightCode <= flight.getElement().getFlightCode();
            boolean comp5 = end_flightCode >= flight.getElement().getFlightCode();
            if((comp1 && comp2) ||(comp3 && comp4 && comp5))
                iterable.add(flight.getElement());
        });

        return iterable;
    }

}
