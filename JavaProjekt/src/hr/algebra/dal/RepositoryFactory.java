/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.SqlGenreRepository;
import hr.algebra.dal.sql.SqlMovieRepository;
import hr.algebra.dal.sql.SqlPersonRepository;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import java.util.HashMap;
import java.util.Map;

public class RepositoryFactory {

    private RepositoryFactory() {
    }

    private static final Map<String, Repository> map = new HashMap<>();

    static {
        map.put(Movie.class.getSimpleName(), new SqlMovieRepository());
        map.put(Person.class.getSimpleName(), new SqlPersonRepository());
        map.put(Genre.class.getSimpleName(), new SqlGenreRepository());
    }

    public static Repository getRepository(Class type) throws Exception {
        return map.get(type.getSimpleName());

    }
}
