package my.edu.utar.hotelbooking;

public class HotelData {

    private static Hotel selectedHotel; // To store the selected hotel
    // Define arrays to store room prices based on hotels
    public static final double[] hotel1RoomPrices = {166.0, 340.0, 428.0, 540.0};
    public static final double[] hotel2RoomPrices = {200.0, 340.0, 450.0, 550.0};
    public static final double[] hotel3RoomPrices = {340.0, 450.0, 560.0, 740.0};

    public static Hotel getSelectedHotel() {
        // Simulated hotel data
        Hotel hotel = new Hotel();
        hotel.setName("The Platinum Kuala Lumpur by Cozy White");
        hotel.setAddress("1020 Jalan Sultan Ismail D-15-07, 50250 Kuala Lumpur, Malaysia");
        // Add more hotel data as needed
        return hotel;
    }

    public static Hotel getSelectedHotel1() {
        // Simulated hotel data
        Hotel hotel1 = new Hotel();
        hotel1.setName("Mughal Gardens, Srinagar");
        hotel1.setAddress("2000 Las Vegas Blvd S, Las Vegas, NV 89104, United States");
        // Add more hotel data as needed
        return hotel1;
    }

    public static Hotel getSelectedHotel2() {
        // Simulated hotel data
        Hotel hotel2 = new Hotel();
        hotel2.setName("Sunway Putra Hotel Kuala Lumpur");
        hotel2.setAddress("100, Jalan Putra, Chow Kit, 50350 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur");
        // Add more hotel data as needed
        return hotel2;
    }

}

class Hotel {
    private String name;
    private String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
