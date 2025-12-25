package com.example.flightmanager.service;

import com.example.flightmanager.dao.FlightDao;
import com.example.flightmanager.entity.Flight;
import com.example.flightmanager.exception.FlightNotFoundException;
import com.example.flightmanager.exception.MaxFlightsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {

    @Autowired
    private FlightDao flightDao;

    private static final int MAX_FLIGHTS = 100;

    public List<Flight> getAllFlights() {
        return flightDao.getAll();
    }

    public Flight getFlightById(Long id) {
        return flightDao.get(id)
                .orElseThrow(() -> new FlightNotFoundException(id));
    }

    public void createFlight(Flight flight) {
        // אילוץ: הגבלת כמות הטיסות הכללית
        if (flightDao.getAll().size() >= MAX_FLIGHTS) {
            throw new MaxFlightsException("Cannot add more flights. Max limit of " + MAX_FLIGHTS + " reached.");
        }
        flightDao.save(flight);
    }

    public void updateFlight(Long id, Flight flightDetails) {
        Flight existingFlight = flightDao.get(id)
                .orElseThrow(() -> new FlightNotFoundException(id));

        // עדכון השדות
        existingFlight.setOrigin(flightDetails.getOrigin());
        existingFlight.setDestination(flightDetails.getDestination());

        // עדכון קיבולת (אם השתנתה, זהירות לא לפגוע בנוסעים קיימים - לשיקול דעתך)
        existingFlight.setMaxCapacity(flightDetails.getMaxCapacity());

        flightDao.update(existingFlight);
    }

    public void deleteFlight(Long id) {
        if (flightDao.get(id).isEmpty()) {
            throw new FlightNotFoundException(id);
        }
        // הערה: במערכת אמיתית נרצה לבדוק שאין נוסעים רשומים לפני מחיקת טיסה
        flightDao.delete(id);
    }
}