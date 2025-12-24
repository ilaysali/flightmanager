package com.example.flightmanager.util;

public class IdGenerator {

    private static int flightId = 0;
    private static int passengerId = 0;

    public static int nextFlightId() {
        return ++flightId;
    }

    public static int nextPassengerId() {
        return ++passengerId;
    }

    // קריאה אחרי טעינה מקובץ
    public static void updateFlightId(int maxId) {
        flightId = Math.max(flightId, maxId);
    }

    public static void updatePassengerId(int maxId) {
        passengerId = Math.max(passengerId, maxId);
    }
}
