package com.example.flightmanager.dao;


import com.example.flightmanager.entity.Flight;
import java.util.List;

public interface FlightDao {
    void add(Flight flight);
    List<Flight> getAll();
    void save();
    void load();
}

