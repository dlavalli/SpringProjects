package com.lavalliere.daniel.spring.nullspecify.service;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

@Service
public class MenuService {


    public String[] getMenuCategories() {
        return new String[] {"Coffee", null, "Pastries"};
    }

    public @Nullable String[] getDailySpecial() {
        return new @Nullable String[]{
            "Pumpkin Spic Latte",
            null,
            "Blueberry Muffin",
            null,
            "Happy Hour 50% offpastries"
        };
    }
}
