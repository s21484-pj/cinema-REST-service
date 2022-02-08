package cinema.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CinemaRoom {

    @JsonProperty("total_rows")
    private int totalRows;

    @JsonProperty("total_columns")
    private int totalColumns;

    @JsonProperty("available_seats")
    private List<Seat> availableSeats;

    public CinemaRoom() {
    }

    public CinemaRoom(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        this.availableSeats = fillSeats();
    }

    private List<Seat> fillSeats() {
        List<Seat> list = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                Seat seat;
                if (i <= 4) {
                    seat = new Seat(i, j, 10, true);
                } else {
                    seat = new Seat(i, j, 8, true);
                }
                list.add(seat);
            }
        }
        return list;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }
}
