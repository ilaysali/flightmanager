package com.example.flightmanager;

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
    public CommandLineRunner testApp(FlightService flightService, PassengerService passengerService) {
        return (args) -> {
            System.out.println("---------- Starting Flight Manager Test ----------");

            // 1. ניקוי נתונים (אופציונלי - רק כדי להתחיל נקי בכל הרצה לצורך הדגמה)
            // בפרויקט אמיתי אולי נרצה לשמור את ההיסטוריה
            /* flightService.getAllFlights().forEach(f -> flightService.deleteFlight(f.getId()));
            passengerService.getAllPassengers().forEach(p -> passengerService.deletePassenger(p.getId()));
            */

            try {
                // 2. יצירת טיסות (שים לב: ID הוא null כי ה-DAO מייצר אותו, והוספנו קיבולת)
                System.out.println("\n[1] Creating Flights...");

                Flight f1 = new Flight(null, "Tel Aviv", "New York", 100);
                Flight f2 = new Flight(null, "London", "Paris", 2); // קיבולת קטנה לבדיקה

                flightService.createFlight(f1);
                flightService.createFlight(f2);

                // שליפת הטיסות מהמערכת כדי לקבל את ה-ID האמיתי שנוצר להן
                List<Flight> savedFlights = flightService.getAllFlights();
                Long flightId1 = savedFlights.get(0).getId();
                Long flightId2 = savedFlights.get(1).getId();

                System.out.println("Flights created successfully:");
                savedFlights.forEach(System.out::println);


                // 3. יצירת נוסעים
                System.out.println("\n[2] Adding Passengers...");

                // נוסע 1 לטיסה הראשונה
                Passenger p1 = new Passenger(null, "Yuval", "Shir-ran", "111111111", flightId1);
                passengerService.addPassenger(p1);

                // נוסע 2 לטיסה השנייה
                Passenger p2 = new Passenger(null, "John", "Pork", "222222222", flightId2);
                passengerService.addPassenger(p2);

                // נוסע 3 לטיסה השנייה (ממלא את הטיסה לגמרי כי הקיבולת היא 2)
                Passenger p3 = new Passenger(null, "Jane", "Doe", "333333333", flightId2);
                passengerService.addPassenger(p3);

                System.out.println("Passengers added successfully.");

                // 4. הדפסת דוח מצב
                System.out.println("\n--- Current System Status ---");
                System.out.println("Flights:");
                flightService.getAllFlights().forEach(System.out::println);

                System.out.println("Passengers:");
                passengerService.getAllPassengers().forEach(System.out::println);


                // 5. בדיקת מקרי קצה (חריגות) - אופציונלי לבדיקה
                System.out.println("\n[3] Testing Exceptions (Validation Logic)...");

                try {
                    // ניסיון להוסיף נוסע 4 לטיסה 2 שהיא כבר מלאה (קיבולת 2, יש כבר 2 נוסעים)
                    Passenger pOverload = new Passenger(null, "Late", "Comer", "444444444", flightId2);
                    passengerService.addPassenger(pOverload);
                } catch (RuntimeException e) {
                    System.out.println("Caught expected exception (Capacity Full): " + e.getMessage());
                }

                try {
                    // ניסיון להוסיף נוסע עם דרכון שכבר קיים
                    Passenger pDuplicate = new Passenger(null, "Imposter", "Man", "111111111", flightId1);
                    passengerService.addPassenger(pDuplicate);
                } catch (RuntimeException e) {
                    System.out.println("Caught expected exception (Duplicate Passport): " + e.getMessage());
                }

            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }

            System.out.println("\n---------- Test Finished ----------");
        };
    }
}