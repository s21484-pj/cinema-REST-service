package cinema.controller;

import cinema.exception_handler.ErrorResponse;
import cinema.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CinemaController {

    private final CinemaRoom cinemaRoom;
    private List<Ticket> tickets;

    public CinemaController(List<Ticket> tickets) {
        this.cinemaRoom = fillCinemaRoom();
        this.tickets = tickets;
    }

    public CinemaRoom fillCinemaRoom() {
        return new CinemaRoom(9, 9);
    }

    @GetMapping("/seats")
    public CinemaRoom getCinemaRoom() {
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Ticket> postPurchaseTicket(@RequestBody SeatDTO seatDTO) {
        if (seatDTO.getRow() < 1 || seatDTO.getRow() > 9 || seatDTO.getColumn() < 1 || seatDTO.getColumn() > 9) {
            return new ResponseEntity(new ErrorResponse("The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        }
        int index = (seatDTO.getRow() - 1) * 9 + seatDTO.getColumn() - 1;
        Seat seat = cinemaRoom.getAvailableSeats().get(index);
        if (!seat.isAvailable()) {
            return new ResponseEntity(new ErrorResponse("The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);

        }
        cinemaRoom.getAvailableSeats().get(index).setAvailable(false);
        Ticket ticket = new Ticket(seat);
        tickets.add(ticket);
        return ResponseEntity.ok(ticket);
    }

    @PostMapping("/return")
    public ResponseEntity<ReturnedTicket> postReturnTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket ticket = null;
        for (var ticket1 : tickets) {
            if (ticket1.getToken().equals(ticketDTO.getToken())) {
                ticket = ticket1;
            }
        }
        if (ticket != null) {
            ticket.getSeat().setAvailable(true);
        } else {
            return new ResponseEntity(new ErrorResponse("Wrong token!"), HttpStatus.BAD_REQUEST);
        }
        ReturnedTicket returnedTicket = new ReturnedTicket(ticket.getSeat());
        tickets.remove(ticket);
        return ResponseEntity.ok(returnedTicket);
    }

    @PostMapping("/stats")
    public ResponseEntity<Statistics> postStatistics(
            @RequestParam(value = "password", required = false) String password) {
        if (password == null) {
            return new ResponseEntity(new ErrorResponse("The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
        Statistics statistics = new Statistics();
        int income = 0;
        for (var ticket : tickets) {
            income += ticket.getSeat().getPrice();
        }
        statistics.setCurrentIncome(income);
        statistics.setNumberOfAvailableSeats(cinemaRoom.getAvailableSeats().size() - tickets.size());
        statistics.setNumberOfPurchasedTickets(tickets.size());
        return ResponseEntity.ok(statistics);
    }
}
