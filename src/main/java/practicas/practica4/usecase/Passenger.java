package practicas.practica4.usecase;

import java.util.Objects;

public class Passenger {

    private String dni;
    private String name;
    private String surname;

    public String getDNI() {
        return dni;
    }

    public void setDNI(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return dni.equals(passenger.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

}
