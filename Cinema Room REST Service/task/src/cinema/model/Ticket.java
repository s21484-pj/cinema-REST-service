package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Ticket {

    private String token;

    @JsonProperty("ticket")
    private Seat seat;

    public Ticket() {
    }

    public Ticket(Seat seat) {
        this.token = generateToken();
        this.seat = seat;
    }

    private String generateToken() {
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
