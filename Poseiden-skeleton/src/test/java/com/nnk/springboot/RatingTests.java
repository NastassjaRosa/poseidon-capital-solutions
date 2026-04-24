package com.nnk.springboot;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		Assertions.assertNotNull(rating.getId());
		Assertions.assertEquals(Integer.valueOf(10), rating.getOrderNumber());

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		Assertions.assertEquals(Integer.valueOf(20), rating.getOrderNumber());

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		Assertions.assertFalse(listResult.isEmpty());

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		Assertions.assertFalse(ratingList.isPresent());
	}
}