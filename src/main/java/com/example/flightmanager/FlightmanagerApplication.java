package com.example.flightmanager;

import com.example.flightmanager.cli.FlightCLI; // Make sure this import exists!
import com.example.flightmanager.entity.Flight;
import com.example.flightmanager.entity.Passenger;
import com.example.flightmanager.service.FlightService;
import com.example.flightmanager.service.PassengerService;
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
    public CommandLineRunner testApp(FlightService flightService,
                                     PassengerService passengerService,
                                     FlightCLI flightCLI) {
        return (args) -> {
            System.out.println("---------- Initializing Data ----------");

            // FIX: Check if data already exists to avoid duplicates
            if (flightService.getAllFlights().isEmpty()) {
                System.out.println("[System] Database empty. Seeding data...");

                Flight f1 = new Flight(null, "Tel Aviv", "New York", 100);
                Flight f2 = new Flight(null, "London", "Paris", 2);
                flightService.createFlight(f1);
                flightService.createFlight(f2);

                // Fetch the new IDs
                List<Flight> saved = flightService.getAllFlights();
                Long id1 = saved.get(0).getId();
                Long id2 = saved.get(1).getId();

                passengerService.addPassenger(new Passenger(null, "Yuval", "Shir-ran", "111111111", id1));
                passengerService.addPassenger(new Passenger(null, "John", "Pork", "222222222", id2));

                System.out.println("[System] Seeding complete.");
            } else {
                System.out.println("[System] Data already exists. Skipping seed.");
            }

            // Launch the menu
            flightCLI.start();
        };
    }
}