package co.grandcircus.movie;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.grandcircus.movie.dao.MovieDao;
import co.grandcircus.movie.entity.Movie;

@RestController
public class MovieController {
	@Autowired
	private MovieDao dao;

	@GetMapping("/movies")
	public List<Movie> listMovies(@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "title", required = false) String title) {
		if (((category == null || category.isEmpty()) && ((title == null || title.isEmpty())))) {
			return dao.findAll();

		} else if (category == null || category.isEmpty()) {
			return dao.findByTitleContainsIgnoreCase(title);

		} else {
			return dao.findByCategoryContainsIgnoreCase(category);
		}	
		
	}
	
	@GetMapping("/movies/random")
	public Movie randomMovie(
			@RequestParam(value="category", required=false) String category,
			@RequestParam(value="title", required=false) String title
			) {
		if (category == null || category.isEmpty()) {
			Long id = (long)((Math.random() * 100) + 1);
			return dao.findById(id).get();
		} else {
			List<Movie> moviesCat = dao.findByCategoryContainsIgnoreCase(category);
			int length = moviesCat.size();
			int id = (int)((Math.random() * length));
			return moviesCat.get(id);
		}
	}

	@GetMapping("/movies/{id}")
	public Movie getMovie(@PathVariable("id") Long id) {
		return dao.findById(id).get();
	}

	@GetMapping("/categories")
	public List<String> getAllCat(){
		List<Movie> movieList = dao.findAll();
		List<String> movieCategories = new ArrayList<String>();
		for (Movie movie: movieList) {
			if (!movieCategories.contains(movie.getCategory())) {
				movieCategories.add(movie.getCategory());
			}
		} return movieCategories;
	}
}
