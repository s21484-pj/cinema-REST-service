package cinema.model;

public class TicketDTO {

    private String token;

    public TicketDTO() {
    }

    public TicketDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
