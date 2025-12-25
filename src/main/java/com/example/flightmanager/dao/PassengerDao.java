package com.example.flightmanager.dao;

import com.example.flightmanager.entity.Passenger;
import java.util.List;
import java.util.Optional;

public interface PassengerDao {
    List<Passenger> getAll();
    Optional<Passenger> get(Long id);
    void save(Passenger passenger);
    void update(Passenger passenger);
    void delete(Long id);

    // פונקציה קריטית עבור Service: בדיקת תפוסה בטיסה
    List<Passenger> getPassengersByFlightId(Long flightId);
}