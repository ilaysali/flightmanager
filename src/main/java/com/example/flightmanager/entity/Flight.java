package com.example.flightmanager.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

public class Flight implements Serializable, Comparable<Flight> {

    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @Min(value = 0, message = "Capacity must be non-negative")
    private int maxCapacity; // שדה חובה ללוגיקה העסקית

    public Flight() {}

    public Flight(Long id, String origin, String destination, int maxCapacity) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.maxCapacity = maxCapacity;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrigin() { return origin; }
    public void setOrigin(String origin) { this.origin = origin; }

    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Flight{id=" + id + ", origin='" + origin + "', destination='" + destination + "', capacity=" + maxCapacity + "}";
    }

    @Override
    public int compareTo(Flight other) {
        // מיון לפי יעד (Destination)
        return this.destination.compareTo(other.destination);
    }
}