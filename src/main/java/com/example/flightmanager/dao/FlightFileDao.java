package com.example.flightmanager.dao;

import com.example.flightmanager.entity.Flight;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class FlightFileDao implements FlightDao {

    private final String FILE_NAME = "flights.dat";
    private List<Flight> flights = new ArrayList<>();
    private long currentIdCounter = 1;

    // נטען אוטומטית כשהאפליקציה עולה
    @PostConstruct
    public void init() {
        loadFromFile();
        // מציאת ה-ID האחרון כדי להמשיך את המספור
        if (!flights.isEmpty()) {
            currentIdCounter = flights.stream()
                    .mapToLong(Flight::getId)
                    .max()
                    .orElse(0) + 1;
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                flights = (ArrayList<Flight>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading flights: " + e.getMessage());
                flights = new ArrayList<>();
            }
        }
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(flights);
        } catch (IOException e) {
            System.err.println("Error saving flights: " + e.getMessage());
        }
    }

    @Override
    public List<Flight> getAll() {
        return new ArrayList<>(flights); // החזרת עותק להגנה על הרשימה המקורית
    }

    @Override
    public Optional<Flight> get(Long id) {
        return flights.stream().filter(f -> f.getId().equals(id)).findFirst();
    }

    @Override
    public void save(Flight flight) {
        flight.setId(currentIdCounter++);
        flights.add(flight);
        Collections.sort(flights); // דרישה: מיון לאחר הוספה
        saveToFile();
    }

    @Override
    public void update(Flight flight) {
        // מחפש את האינדקס של הטיסה ומחליף אותה
        for (int i = 0; i < flights.size(); i++) {
            if (flights.get(i).getId().equals(flight.getId())) {
                flights.set(i, flight);
                saveToFile();
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        boolean removed = flights.removeIf(f -> f.getId().equals(id));
        if (removed) {
            saveToFile();
        }
    }
}