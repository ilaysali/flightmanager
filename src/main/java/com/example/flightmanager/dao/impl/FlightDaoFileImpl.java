package com.example.flightmanager.dao.impl;


import com.example.flightmanager.dao.FlightDao;
import com.example.flightmanager.entity.Flight;
import com.example.flightmanager.util.IdGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class FlightDaoFileImpl implements FlightDao {

    private ArrayList<Flight> flights = new ArrayList<>();
    private static final String FILE_NAME = "./flights.dat";

    @Override
    public void add(Flight flight) {
        if (!flights.contains(flight)) {
            flights.add(flight);
            Collections.sort(flights);
        }
    }

    @Override
    public ArrayList<Flight> getAll() {
        return flights;
    }

    @Override
    public void save() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(flights);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            flights = (ArrayList<Flight>) ois.readObject();

            int maxId = flights.stream()
                    .mapToInt(Flight::getId)
                    .max()
                    .orElse(0);

            IdGenerator.updateFlightId(maxId);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
