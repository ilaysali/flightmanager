package com.example.flightmanager.dao;

import com.example.flightmanager.entity.Passenger;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PassengerFileDao implements PassengerDao {

    private final String FILE_NAME = "passengers.dat";
    private List<Passenger> passengers = new ArrayList<>();
    private long currentIdCounter = 1;

    @PostConstruct
    public void init() {
        loadFromFile();
        if (!passengers.isEmpty()) {
            currentIdCounter = passengers.stream()
                    .mapToLong(Passenger::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                passengers = (ArrayList<Passenger>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading passengers: " + e.getMessage());
                passengers = new ArrayList<>();
            }
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(passengers);
        } catch (IOException e) {
            System.err.println("Error saving passengers: " + e.getMessage());
        }
    }

    @Override
    public List<Passenger> getAll() {
        return new ArrayList<>(passengers);
    }

    @Override
    public Optional<Passenger> get(Long id) {
        return passengers.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Passenger passenger) {
        passenger.setId(currentIdCounter++);
        passengers.add(passenger);
        Collections.sort(passengers); // דרישה: מיון
        saveToFile();
    }

    @Override
    public void update(Passenger passenger) {
        for (int i = 0; i < passengers.size(); i++) {
            if (passengers.get(i).getId().equals(passenger.getId())) {
                passengers.set(i, passenger);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        boolean removed = passengers.removeIf(p -> p.getId().equals(id));
        if (removed) {
            saveToFile();
        }
    }

    @Override
    public List<Passenger> getPassengersByFlightId(Long flightId) {
        // שימוש ב-Stream לסינון מהיר
        return passengers.stream()
                .filter(p -> p.getFlightId().equals(flightId))
                .collect(Collectors.toList());
    }
}