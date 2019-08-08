package co.grandcircus.movie.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import co.grandcircus.movie.entity.Movie;



public interface MovieDao extends JpaRepository<Movie, Long> {
	
	List<Movie> findByTitleContainsIgnoreCase(String title);

	List<Movie> findByCategoryContainsIgnoreCase(String category);
	

}

