package com.lavalliere.daniel.learningspingagain.data.repository;

import java.sql.Date;
import com.lavalliere.daniel.learningspingagain.data.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
    Iterable<Reservation> findReservationByReservationDate(Date date);
}
