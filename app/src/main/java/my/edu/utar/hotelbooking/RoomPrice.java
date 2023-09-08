package my.edu.utar.hotelbooking;

import java.util.HashMap;
import java.util.Map;

public class RoomPrice {
    // Create a Map to store room prices for each hotel
    private Map<String, Map<String, Double>> hotelRoomPrices = new HashMap<>();

    public RoomPrice() {
        // Initialize room prices for different hotels
        initializeRoomPricesForHotel("The Platinum Kuala Lumpur by Cozy White");
        initializeRoomPricesForHotel("The START Hotel, Casino & SkyPod");
        // Add more hotels and room prices as needed
    }

    // Method to initialize room prices for a specific hotel
    private void initializeRoomPricesForHotel(String hotelName) {
        Map<String, Double> roomPrices = new HashMap<>();
        // Add room types and their respective prices for the hotel
        if ("The Platinum Kuala Lumpur by Cozy White".equals(hotelName)) {
            roomPrices.put("Standard Single Room", 150.0);
            roomPrices.put("Standard Double Room", 250.0);
            roomPrices.put("Deluxe Suite", 500.0);
            // Add more room types and prices for HotelA
        } else if ("The START Hotel, Casino & SkyPod".equals(hotelName)) {
            roomPrices.put("Standard Single Room", 120.0);
            roomPrices.put("Standard Double Room", 220.0);
            roomPrices.put("Executive Suite", 450.0);
            // Add more room types and prices for HotelB
        }
        // Add room prices to the map for the hotel
        hotelRoomPrices.put(hotelName, roomPrices);
    }

    // Method to get the room price for a specific hotel and room type
    public double getRoomPrice(String hotelName, String roomType) {
        // Retrieve room prices for the specified hotel
        Map<String, Double> roomPrices = hotelRoomPrices.get(hotelName);
        if (roomPrices != null) {
            // Get the price for the specified room type
            Double price = roomPrices.get(roomType);
            if (price != null) {
                return price;
            }
        }
        // Return a default price if the hotel or room type is not found
        return 0.0;
    }

    public static void main(String[] args) {
        RoomPrice pricing = new RoomPrice();

        // Example: Get the price for a room type in a specific hotel
        String hotelName = "The Platinum Kuala Lumpur by Cozy White";
        String roomType = "Deluxe Suite";
        double price = pricing.getRoomPrice(hotelName, roomType);
        System.out.println("Price for " + roomType + " at " + hotelName + ": $" + price);
    }
}

