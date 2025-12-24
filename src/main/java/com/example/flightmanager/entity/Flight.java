package com.example.flightmanager.entity;

import java.io.Serializable;
import java.util.Objects;

public class Flight implements Serializable, Comparable<Flight> {

    private static final long serialVersionUID = 1L;

    private int id;
    private String origin;
    private String destination;

    public Flight(int id, String origin, String destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Flight other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;
        Flight flight = (Flight) o;
        return id == flight.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Flight{id=" + id +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }
}
