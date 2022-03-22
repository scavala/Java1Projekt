/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.model.Person;
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
public class SqlPersonRepository implements Repository<Person> {

    private static final String ID_PERSON = "IDPerson";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";

    private static final String CREATE_PERSONIFNOTEXISTS = "{ CALL createPersonIfNotExists (?,?,?) }";
    private static final String SELECT_PERSON = "{ CALL selectPerson (?) }";
    private static final String SELECT_PEOPLE = "{ CALL selectPeople  }";
    private static final String UPDATE_PERSON = "{ CALL updatePerson (?,?,?) }";
    private static final String DELETE_PERSON = "{ CALL deletePerson (?) }";

    private static final String DELETE_ALL_PERSONS = "{ CALL deleteAllPersons }";



    @Override
    public int create(Person person) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_PERSONIFNOTEXISTS)) {

            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());

            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public void createMultiple(Collection<Person> objects) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_PERSONIFNOTEXISTS)) {

            for (Person person : objects) {
                stmt.setString(1, person.getFirstName());
                stmt.setString(2, person.getLastName());

                stmt.registerOutParameter(3, Types.INTEGER);
                stmt.executeUpdate();

            }

        }
    }

    @Override
    public void update(int id, Person data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_PERSON)) {

            stmt.setString(1, data.getFirstName());

            stmt.setString(2, data.getLastName());

            stmt.setInt(3, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_PERSON)) {
            stmt.setInt(1, id);

            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Person> select(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PERSON)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Person(
                            id,
                            rs.getString(FIRST_NAME),
                            rs.getString(LAST_NAME)
                    ));
                }
            }

        }
        return Optional.empty();
    }

    @Override
    public List<Person> selectAll() throws Exception {
        List<Person> people = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_PEOPLE);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                people.add(new Person(
                        rs.getInt(ID_PERSON),
                        rs.getString(FIRST_NAME),
                        rs.getString(LAST_NAME)));
            }
        }
        return people;
    }

    @Override
    public void deleteAll() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ALL_PERSONS)) {
            stmt.executeUpdate();

        }
    }

}
