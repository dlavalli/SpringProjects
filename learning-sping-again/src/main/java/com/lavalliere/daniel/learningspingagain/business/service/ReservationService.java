package com.lavalliere.daniel.learningspingagain.business.service;

import java.util.HashMap;
import java.util.List;
import java.util.Date;
import java.util.Map;
import com.lavalliere.daniel.learningspingagain.business.domain.RoomReservation;
import com.lavalliere.daniel.learningspingagain.data.entity.Guest;
import com.lavalliere.daniel.learningspingagain.data.entity.Reservation;
import com.lavalliere.daniel.learningspingagain.data.entity.Room;
import com.lavalliere.daniel.learningspingagain.data.repository.GuestRepository;
import com.lavalliere.daniel.learningspingagain.data.repository.ReservationRepository;
import com.lavalliere.daniel.learningspingagain.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * An example of a bean to be injected though Spring
 *
 * @Service is just a stereotype of @Component, which allows component scanning to occur.
 * The reason I use the @Service annotation is, a lot of times, this is where I will set things
 * like transaction boundaries and log boundaries and by using the @Service annotation,
 * I can build aspects against this that won't apply to other classes within my stack
 *
 * But, again, you could also just do @Component, it will work just fine
 */
@Service
public class ReservationService {

    // in order for our reservation service to work correctly, we need to have some dependencies
    // and those dependencies are going to be our repositories.
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    /*
     * auto-wire my constructor:  Now, this is technically not required. Since there's only one constructor,
     * Spring will use it by default and assume it has all of the beans to find in the application context.
     * However, I get a couple benefits by specifying the @Autowired on my constructor.
     *      Number one, my ID is smart enough to know if I've defined that bean in the application context that would run
     *                  with this application, so it would give me a warning that it doesn't know of that bean type.
     *      Number two, I get from that is that it's very clear to me which constructor is being called at any point in time
     *                  and if someone comes in and adds another constructor, they would have to make that change to make
     *                  that the auto-wired constructor. Again, I like specificity when I wrote code only because it makes
     *                  me feel better that, downstream, everything's going to work properly. And that's all we need to do
     *                  to get this bean ready to be injected through Spring.
     */
    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationForDate(Date date) {
        // build out all of our rooms in order to populate our data model
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getRoomId(), roomReservation);
        });

        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            //Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            // Populate other roomReservation fields here...
        });
        return null;
    }
}
