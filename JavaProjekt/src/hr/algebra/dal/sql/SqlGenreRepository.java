/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.model.Genre;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import hr.algebra.dal.Repository;

/**
 *
 * @author zakesekresa
 */
public class SqlGenreRepository implements Repository<Genre> {

    private static final String ID_ZANR = "IDZanr";
    private static final String NAME = "Name";

    private static final String SELECT_ZANRS = "{ CALL selectZanrs  }";
    private static final String SELECT_ZANR = "{ CALL selectGenre (?) }";
    private static final String CREATE_ZANRIFNOTEXISTS = "{ CALL createZanrIfNotExists (?,?) }";
    private static final String UPDATE_GENRE = "{ CALL updateGenre (?,?) }";
    private static final String DELETE_GENRE = "{ CALL deleteGenre (?) }";

    private static final String DELETE_ALL_GENRES = "{ CALL deleteAllGenres }";

    @Override
    public int create(Genre object) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ZANRIFNOTEXISTS)) {

            stmt.setString(1, object.getName());

            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }

    @Override
    public void createMultiple(Collection<Genre> objects) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ZANRIFNOTEXISTS)) {

            for (Genre genre : objects) {
                stmt.setString(1, genre.getName());

                stmt.registerOutParameter(2, Types.INTEGER);
                stmt.executeUpdate();

            }

        }
    }

    @Override
    public void update(int id, Genre data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();

        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_GENRE)) {

            stmt.setString(1, data.getName());

            stmt.setInt(2, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_GENRE)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Genre> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ZANR)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Genre(
                            id,
                            rs.getString(NAME)
                    ));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Genre> selectAll() throws Exception {
        List<Genre> zanrs = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ZANRS);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                zanrs.add(new Genre(
                        rs.getInt(ID_ZANR),
                        rs.getString(NAME)));
            }
        }
        return zanrs;
    }

    @Override
    public void deleteAll() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ALL_GENRES)) {
            stmt.executeUpdate();

        }
    }

}
