package com.example.flightmanager.dao.impl;

import com.example.flightmanager.dao.PassengerDao;
import com.example.flightmanager.entity.Passenger;
import com.example.flightmanager.util.IdGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PassengerDaoFileImpl implements PassengerDao {

    private ArrayList<Passenger> passengers = new ArrayList<>();
    private static final String FILE_NAME = "src/main/resources/passengers.dat";

    @Override
    public void add(Passenger passenger) {
        if (!passengers.contains(passenger)) {
            passengers.add(passenger);
            Collections.sort(passengers);
        }
    }

    @Override
    public ArrayList<Passenger> getAll() {
        return passengers;
    }

    @Override
    public void save() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(passengers);
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

            passengers = (ArrayList<Passenger>) ois.readObject();

            int maxId = passengers.stream()
                    .mapToInt(Passenger::getId)
                    .max()
                    .orElse(0);

            IdGenerator.updatePassengerId(maxId);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
