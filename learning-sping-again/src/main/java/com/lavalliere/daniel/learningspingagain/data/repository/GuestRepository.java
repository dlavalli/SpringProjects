package com.lavalliere.daniel.learningspingagain.data.repository;

import com.lavalliere.daniel.learningspingagain.data.entity.Guest;

public interface GuestRepository {
    public default Guest findById(long id) {
        return null;
    }
}
