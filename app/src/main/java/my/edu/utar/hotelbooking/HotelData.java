package my.edu.utar.hotelbooking;

public class HotelData {

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
        Hotel hotel = new Hotel();
        hotel.setName("The START Hotel, Casino & SkyPod");
        hotel.setAddress("2000 Las Vegas Blvd S, Las Vegas, NV 89104, United States");
        // Add more hotel data as needed
        return hotel;
    }

    public static Hotel getSelectedHotel2() {
        // Simulated hotel data
        Hotel hotel = new Hotel();
        hotel.setName("Sunway Putra Hotel Kuala Lumpur");
        hotel.setAddress("100, Jalan Putra, Chow Kit, 50350 Kuala Lumpur, Wilayah Persekutuan Kuala Lumpur");
        // Add more hotel data as needed
        return hotel;
    }
}

class Hotel {
    private String name;
    private String address;
    // Add more hotel attributes as needed

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
