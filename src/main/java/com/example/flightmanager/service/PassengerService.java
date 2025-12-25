package com.example.flightmanager.service;

import com.example.flightmanager.dao.FlightDao;
import com.example.flightmanager.dao.PassengerDao;
import com.example.flightmanager.entity.Flight;
import com.example.flightmanager.entity.Passenger;
import com.example.flightmanager.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PassengerService {

    @Autowired
    private PassengerDao passengerDao;

    @Autowired
    private FlightDao flightDao;

    private static final int MAX_TOTAL_PASSENGERS = 1000;

    public List<Passenger> getAllPassengers() {
        return passengerDao.getAll();
    }

    public Passenger getPassengerById(Long id) {
        return passengerDao.get(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));
    }

    public void addPassenger(Passenger passenger) {
        // 1. אילוץ: מקסימום נוסעים בכל המערכת
        if (passengerDao.getAll().size() >= MAX_TOTAL_PASSENGERS) {
            throw new MaxPassengersException("System reached max passenger capacity.");
        }

        // 2. אילוץ: ייחודיות דרכון
        boolean passportExists = passengerDao.getAll().stream()
                .anyMatch(p -> p.getPassportId().equals(passenger.getPassportId()));
        if (passportExists) {
            throw new DuplicatePassportException(passenger.getPassportId());
        }

        // 3. בדיקה שהטיסה קיימת
        Flight flight = flightDao.get(passenger.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException(passenger.getFlightId()));

        // 4. אילוץ: תפוסה בטיסה ספציפית (Current vs MaxCapacity)
        long passengersOnFlight = passengerDao.getPassengersByFlightId(passenger.getFlightId()).size();

        if (passengersOnFlight >= flight.getMaxCapacity()) {
            throw new FlightCapacityException("Flight " + flight.getId() + " is full. Capacity: " + flight.getMaxCapacity());
        }

        passengerDao.save(passenger);
    }

    public void updatePassenger(Long id, Passenger passengerDetails) {
        Passenger existingPassenger = passengerDao.get(id)
                .orElseThrow(() -> new PassengerNotFoundException(id));

        // אם הדרכון השתנה, וודא שהחדש לא תפוס ע"י מישהו אחר
        if (!existingPassenger.getPassportId().equals(passengerDetails.getPassportId())) {
            boolean passportExists = passengerDao.getAll().stream()
                    .anyMatch(p -> p.getPassportId().equals(passengerDetails.getPassportId()));
            if (passportExists) {
                throw new DuplicatePassportException(passengerDetails.getPassportId());
            }
        }

        // אם מחליפים טיסה, צריך לבדוק שיש מקום בטיסה החדשה
        if (!existingPassenger.getFlightId().equals(passengerDetails.getFlightId())) {
            Flight newFlight = flightDao.get(passengerDetails.getFlightId())
                    .orElseThrow(() -> new FlightNotFoundException(passengerDetails.getFlightId()));

            long currentCount = passengerDao.getPassengersByFlightId(newFlight.getId()).size();
            if (currentCount >= newFlight.getMaxCapacity()) {
                throw new FlightCapacityException("Target flight " + newFlight.getId() + " is full.");
            }
        }

        // ביצוע העדכון
        existingPassenger.setFirstName(passengerDetails.getFirstName());
        existingPassenger.setLastName(passengerDetails.getLastName());
        existingPassenger.setPassportId(passengerDetails.getPassportId());
        existingPassenger.setFlightId(passengerDetails.getFlightId());

        passengerDao.update(existingPassenger);
    }

    public void deletePassenger(Long id) {
        if (passengerDao.get(id).isEmpty()) {
            throw new PassengerNotFoundException(id);
        }
        passengerDao.delete(id);
    }
}