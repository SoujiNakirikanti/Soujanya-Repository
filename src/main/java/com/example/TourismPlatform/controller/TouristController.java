package com.example.TourismPlatform.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TourismPlatform.model.TouristDestination;
import com.example.TourismPlatform.repository.TouristDestinationRepository;

@RestController
@RequestMapping("/tourism/platform/v1/destinations")
public class TouristController {

	TouristDestinationRepository destinationRepository;

	public TouristController(TouristDestinationRepository destinationRepository) {
		this.destinationRepository = destinationRepository;
	}

	@PostMapping
	public ResponseEntity<TouristDestination> createDestinationRecord(@RequestBody TouristDestination destination) {
		TouristDestination saved = destinationRepository.save(destination);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}


	@GetMapping
	public ResponseEntity<List<TouristDestination>> getAllDestinations() {

		List<TouristDestination> details = destinationRepository.findAll();
		details.sort((t1, t2) -> Long.compare(t1.getId(), t2.getId()));
		return ResponseEntity.status(HttpStatus.OK).body(details);

	}

	@DeleteMapping("/{destinationId}")
	public ResponseEntity<TouristDestination> deleteTouristDestination(@PathVariable Long destinationId) {

		boolean isPresent = destinationRepository.existsById(destinationId);
		if (isPresent) {
			destinationRepository.deleteById(destinationId);
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/sorted")
	public ResponseEntity<List<TouristDestination>> getDestinationsSortedByVisitorCountDesc() {

		List<TouristDestination> details = destinationRepository.findAll();
		details.sort((t1, t2) -> Long.compare(t2.getVisitorCount(), t1.getVisitorCount()));
		return ResponseEntity.status(HttpStatus.OK).body(details);

	}

}
