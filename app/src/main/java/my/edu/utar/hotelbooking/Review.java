package my.edu.utar.hotelbooking;

public class Review {

    private String userName;
    private String date;
    private String comment;

    public Review(String userName, String date, String comment) {
        this.userName = userName;
        this.date = date;
        this.comment = comment;

    }

    public String getUserName() {
        return userName;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }
}
