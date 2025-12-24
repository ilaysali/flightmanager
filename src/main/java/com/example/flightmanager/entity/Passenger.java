package com.example.flightmanager.entity;

import com.example.flightmanager.util.IdGenerator;
import java.io.Serializable;
import java.util.Objects;

public class Passenger implements Serializable, Comparable<Passenger> {

    private static final long serialVersionUID = 1L;

    private int id;
    private String name;

    public Passenger(String name) {
        this.id = IdGenerator.nextPassengerId();
        this.name = name;
    }

    public Passenger(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Passenger other) {
        return Integer.compare(this.id, other.id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;
        Passenger that = (Passenger) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Passenger{id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}