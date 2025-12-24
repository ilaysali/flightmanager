package com.example.flightmanager;

import com.example.flightmanager.dao.impl.FlightDaoFileImpl;
import com.example.flightmanager.dao.impl.PassengerDaoFileImpl;
import com.example.flightmanager.entity.Flight;
import com.example.flightmanager.entity.Passenger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class FlightmanagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlightmanagerApplication.class, args);
    }

    @Bean
    public CommandLineRunner testApp() {
        return (args) -> {
            System.out.println("---------- Starting Flight Manager Test ----------");

            FlightDaoFileImpl flightDao = new FlightDaoFileImpl();
            PassengerDaoFileImpl passengerDao = new PassengerDaoFileImpl();

            try {
                flightDao.load();
                passengerDao.load();
                System.out.println("Loaded existing data (if any).");
            } catch (Exception e) {
                System.out.println("Load failed or no file, starting fresh.");
            }

            Flight f1 = new Flight("Tel Aviv", "New York");
            Flight f2 = new Flight("London", "Paris");

            Passenger p1 = new Passenger("Yuval Shir-ran");
            Passenger p2 = new Passenger("John Pork");

            System.out.println("Adding flights and passengers");
            flightDao.add(f1);
            flightDao.add(f2);
            passengerDao.add(p1);
            passengerDao.add(p2);

            flightDao.save();
            passengerDao.save();
            System.out.println("Data saved to file.");

            System.out.println("\n--- Current Passengers List ---");
            List<Passenger> allPassengers = passengerDao.getAll();
            if (allPassengers != null) {
                for (Passenger p : allPassengers) {
                    System.out.println(p);
                }
            }

            System.out.println("--------------------------------------------------");
        };
    }
}