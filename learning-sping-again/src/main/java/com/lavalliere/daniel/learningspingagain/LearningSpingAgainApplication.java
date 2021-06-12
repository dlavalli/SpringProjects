package com.lavalliere.daniel.learningspingagain;

import com.lavalliere.daniel.learningspingagain.data.entity.Room;
import com.lavalliere.daniel.learningspingagain.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.web.bind.annotation.*;

// @SpringBootApplication()
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LearningSpingAgainApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningSpingAgainApplication.class, args);
	}

/*
	// Does not work because missing configuration to a real database
	// Consider defining a bean of type 'com.lavalliere.daniel.learningspingagain.data.repository.RoomRepository' in your configuration.

	// this is only for testing, would not do this in production ....
	@RestController
	@RequestMapping("/rooms")
	public class RoomController {
		@Autowired
		private RoomRepository roomRepository;

		@GetMapping
		public Iterable<Room> getRooms() {
			return this.roomRepository.findAll();
		}
	}
*/

}
