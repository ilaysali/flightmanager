package com.example.flightmanager.dao;


import com.example.flightmanager.entity.Passenger;
import java.util.List;

public interface PassengerDao {
    void add(Passenger passenger);
    List<Passenger> getAll();
    void save();
    void load();
}
