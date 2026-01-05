package com.example.flightmanager.cli;

import com.example.flightmanager.entity.Flight;
import com.example.flightmanager.entity.Passenger;
import com.example.flightmanager.service.FlightService;
import com.example.flightmanager.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.Scanner;

@Component
public class FlightCLI {

    // Inject Services from Team Member 2
    @Autowired
    private FlightService flightService;

    @Autowired
    private PassengerService passengerService;

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.welcomeMessage}")
    private String welcomeMsg;

    private Scanner scanner = new Scanner(System.in);
    private boolean running = true;

    @PostConstruct
    public void init() {
        System.out.println(">>> System Initializing: " + appName + " v" + appVersion);
    }

    @PreDestroy
    public void cleanup() {
        System.out.println(">>> System Shutting Down... Saving states.");
        scanner.close();
    }

    public void start() {
        System.out.println(welcomeMsg);

        while (running) {
            printMenu();
            String input = scanner.nextLine();

            try {
                switch (input) {
                    case "1" -> showAllFlights();
                    case "2" -> showAllPassengers();
                    case "3" -> findFlightById();
                    case "4" -> findPassengerById();
                    case "5" -> createNewFlight();
                    case "6" -> registerNewPassenger();
                    case "7" -> updateExistingFlight();
                    case "8" -> updateExistingPassenger();
                    case "9" -> removeFlight();
                    case "10" -> removePassenger();
                    case "e", "E" -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("\n*** ERROR: " + e.getMessage() + " ***\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Show All Flights");
        System.out.println("2. Show All Passengers");
        System.out.println("3. Find Flight by ID");
        System.out.println("4. Find Passenger by ID");
        System.out.println("5. Add New Flight");
        System.out.println("6. Add New Passenger");
        System.out.println("7. Update Flight");
        System.out.println("8. Update Passenger");
        System.out.println("9. Delete Flight");
        System.out.println("10. Delete Passenger");
        System.out.println("E. Exit");
        System.out.print("Select: ");
    }


    private void showAllFlights() {
        var flights = flightService.getAllFlights();
        if (flights.isEmpty()) System.out.println("No flights found.");
        else flights.forEach(System.out::println);
    }

    private void findFlightById() {
        System.out.print("Enter Flight ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println(flightService.getFlightById(id));
    }

    private void createNewFlight() {
        System.out.print("Origin: ");
        String origin = scanner.nextLine();
        System.out.print("Destination: ");
        String dest = scanner.nextLine();
        System.out.print("Max Capacity: ");
        int cap = Integer.parseInt(scanner.nextLine());

        Flight f = new Flight(null, origin, dest, cap);
        flightService.createFlight(f);
        System.out.println("Flight created successfully.");
    }

    private void updateExistingFlight() {
        System.out.print("Enter Flight ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("New Origin: ");
        String origin = scanner.nextLine();
        System.out.print("New Destination: ");
        String dest = scanner.nextLine();
        System.out.print("New Capacity: ");
        int cap = Integer.parseInt(scanner.nextLine());

        Flight f = new Flight(id, origin, dest, cap);
        flightService.updateFlight(id, f);
        System.out.println("Flight updated successfully.");
    }

    private void removeFlight() {
        System.out.print("Enter Flight ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        flightService.deleteFlight(id);
        System.out.println("Flight deleted.");
    }


    private void showAllPassengers() {
        var passengers = passengerService.getAllPassengers();
        if (passengers.isEmpty()) System.out.println("No passengers found.");
        else passengers.forEach(System.out::println);
    }

    private void findPassengerById() {
        System.out.print("Enter Passenger ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.println(passengerService.getPassengerById(id));
    }

    private void registerNewPassenger() {
        System.out.print("First Name: ");
        String first = scanner.nextLine();
        System.out.print("Last Name: ");
        String last = scanner.nextLine();
        System.out.print("Passport ID (9 digits): ");
        String passId = scanner.nextLine();
        System.out.print("Flight ID to assign: ");
        Long fId = Long.parseLong(scanner.nextLine());

        Passenger p = new Passenger(null, first, last, passId, fId);
        passengerService.addPassenger(p);
        System.out.println("Passenger registered successfully.");
    }

    private void updateExistingPassenger() {
        System.out.print("Enter Passenger ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("New First Name: ");
        String first = scanner.nextLine();
        System.out.print("New Last Name: ");
        String last = scanner.nextLine();
        System.out.print("New Passport ID: ");
        String passId = scanner.nextLine();
        System.out.print("New Flight ID: ");
        Long fId = Long.parseLong(scanner.nextLine());

        Passenger p = new Passenger(id, first, last, passId, fId);
        passengerService.updatePassenger(id, p);
        System.out.println("Passenger updated successfully.");
    }

    private void removePassenger() {
        System.out.print("Enter Passenger ID to delete: ");
        Long id = Long.parseLong(scanner.nextLine());
        passengerService.deletePassenger(id);
        System.out.println("Passenger deleted.");
    }
}