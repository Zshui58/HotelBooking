package my.edu.utar.hotelbooking;

public class HotelData {

    public static Hotel getSelectedHotel() {
        // Simulated hotel data
        Hotel hotel = new Hotel();
        hotel.setName("The START Hotel, Casino & SkyPod");
        hotel.setAddress("2000 Las Vegas Blvd S, Las Vegas, NV 89104, United States");
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
