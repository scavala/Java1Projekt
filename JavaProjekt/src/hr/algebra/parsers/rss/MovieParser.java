/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.parsers.rss;

import hr.algebra.MainForm;
import hr.algebra.factory.ParserFactory;
import hr.algebra.factory.UrlConnectionFactory;
import hr.algebra.model.Movie;
import hr.algebra.model.Person;
import hr.algebra.model.Genre;
import hr.algebra.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.jsoup.Jsoup;
import hr.algebra.dal.Repository;

/**
 *
 * @author zakesekresa
 */
public class MovieParser {

    private static final int TIMEOUT = 10000;
    private static final String REQUEST_METHOD = "GET";
    private static final String RSS_URL = "https://www.blitz-cinestar.hr/rss.aspx?najava=2";
    private static final String EXT = ".jpg";

    private static final String DELIMITER = ",";

    private final Repository movieRepository;
    private final Repository personRepository;
    private final Repository genreRepository;

    public MovieParser(Repository movieRepository, Repository personRepository, Repository genreRepository) {
        this.movieRepository = movieRepository;
        this.personRepository = personRepository;
        this.genreRepository = genreRepository;
    }

    public List<Movie> parse() throws IOException, XMLStreamException, Exception {
        List<Movie> movies = new ArrayList<>();

        HttpURLConnection con = UrlConnectionFactory.getHttpUrlConnection(RSS_URL, TIMEOUT, REQUEST_METHOD);

        try (InputStream is = con.getInputStream()) {
            XMLEventReader reader = ParserFactory.createStaxParser(is);

            StartElement startElement = null;
            Movie movie = null;
            Optional<TagType> tagType = Optional.empty();

            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT:
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        if (tagType.isPresent()) {
                            switch (tagType.get()) {
                                case ITEM:
                                    movie = new Movie();
                                    movies.add(movie);
                            }
                        }

                        break;
                    case XMLStreamConstants.CHARACTERS:
                        Characters characters = event.asCharacters();
                        String data = characters.getData().trim(); // title, link, desc...
                        if (tagType.isPresent()) { // ako je to tag koji me zanima!!!
                            switch (tagType.get()) {
                                case TITLE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setTitle(data);
                                    }
                                    break;
                                case DESCRIPTION:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDescription(Jsoup.parse(data).text());
                                    }
                                    break;
                                case ORIGNAZIV:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setOrignaziv(data);
                                    }
                                    break;
                                case PUB_DATE:
                                    if (movie != null && !data.isEmpty()) {
                                        LocalDateTime publishedDate = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        movie.setPublishedDate(publishedDate);
                                    }
                                    break;
                                case PLAKAT:
                                    if (movie != null && startElement != null) {

                                        try {
                                            handlePicture(movie, data);
                                        } catch (Exception Exception) {
                                            movie.setPosterPath(MainForm.DEFAULT_IMAGEURL);
                                        }
                                    }
                                    break;
                                case REDATELJ:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setDirectors(Person.parsePersonsFromLine(data, DELIMITER));
                                        for (Person director : movie.getDirectors()) {
                                            director.setId(personRepository.create(director));
                                        }
                                    }
                                    break;
                                case GLUMCI:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setActors(Person.parsePersonsFromLine(data, DELIMITER));
                                        for (Person actor : movie.getActors()) {
                                            actor.setId(personRepository.create(actor));
                                        }
                                    }
                                    break;

                                case TRAJANJE:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setLength(Integer.parseInt(data));
                                    }
                                    break;
                                case ZANR:
                                    if (movie != null && !data.isEmpty()) {
                                        movie.setGenres(Genre.parseGenresFromLine(data, DELIMITER));
                                        for (Genre genre : movie.getGenres()) {
                                            genre.setId(genreRepository.create(genre));
                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                }
            }
        }
        movieRepository.createMultiple(movies);
        return movies;
    }

    private static void handlePicture(Movie movie, String pictureUrl) throws IOException {

        String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
        if (ext.length() > 4) {
            ext = EXT;
        }
        String pictureName = UUID.randomUUID() + ext;
        String picturePath = MainForm.IMAGESFOLDERURL + File.separator + pictureName;
        FileUtils.copyFromUrl(pictureUrl, picturePath);
        movie.setPosterPath(picturePath);

    }

    private enum TagType {
        ITEM("item"),
        TITLE("title"),
        PUB_DATE("pubDate"),
        DESCRIPTION("description"),
        ORIGNAZIV("orignaziv"),
        REDATELJ("redatelj"),
        GLUMCI("glumci"),
        TRAJANJE("trajanje"),
        ZANR("zanr"),
        PLAKAT("plakat");

        private final String name;

        private TagType(String name) {
            this.name = name;
        }

        public static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }

    }

}
