package com.example.flightmanager.dao;

import com.example.flightmanager.entity.Flight;
import java.util.List;
import java.util.Optional;

public interface FlightDao {
    List<Flight> getAll();
    Optional<Flight> get(Long id);
    void save(Flight flight);   // יצירה
    void update(Flight flight); // עדכון
    void delete(Long id);       // מחיקה
}