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

    // הפונקציה הזו רצה אוטומטית כשהתוכנית עולה
    @Bean
    public CommandLineRunner testApp() {
        return (args) -> {
            System.out.println("---------- Starting Flight Manager Test ----------");

            // 1. יצירת המופעים של ה-DAO (שמנהלים את הקבצים)
            // שים לב: וודא שהמחלקות האלו הן public בקבצים שלהן
            FlightDaoFileImpl flightDao = new FlightDaoFileImpl();
            PassengerDaoFileImpl passengerDao = new PassengerDaoFileImpl();

            // 2. ניסיון טעינת נתונים קיימים (אם יש קובץ כזה כבר)
            try {
                // אם פונקציות ה-load שלך זורקות Exception, צריך לעטוף ב-try/catch
                // אם לא מימשת load עדיין, אפשר להעיר את השורות האלו
                // flightDao.load();
                // passengerDao.load();
                System.out.println("Data loaded check (if implemented).");
            } catch (Exception e) {
                System.out.println("No existing data found or load failed, starting fresh.");
            }

            // 3. יצירת נתונים לדוגמה (Entities)
            // הערה: וודא שיש לך בנאים (Constructors) מתאימים במחלקות Flight ו-Passenger!
            Flight f1 = new Flight(101, "Tel Aviv", "New York");
            Flight f2 = new Flight(102, "London", "Paris");

            Passenger p1 = new Passenger(1, "Yuval Shir-ran");
            Passenger p2 = new Passenger(2, "John Doe");

            // 4. הוספת הנתונים לזיכרון
            System.out.println("Adding flights and passengers...");
            flightDao.add(f1);
            flightDao.add(f2);
            passengerDao.add(p1);
            passengerDao.add(p2);

            // 5. שמירה לקובץ
            // זה יבדוק אם לוגיקת הכתיבה לקובץ שלך עובדת
            flightDao.save();
            passengerDao.save();
            System.out.println("Data saved to file.");

            // 6. בדיקת קריאה (הדפסה למסך)
            System.out.println("\n--- Current Passengers List ---");
            List<Passenger> allPassengers = passengerDao.getAll();

            if (allPassengers != null) {
                for (Passenger p : allPassengers) {
                    // וודא שיש לך פונקציית toString במחלקת Passenger
                    System.out.println(p);
                }
            } else {
                System.out.println("Passenger list is null!");
            }

            System.out.println("--------------------------------------------------");
        };
    }
}