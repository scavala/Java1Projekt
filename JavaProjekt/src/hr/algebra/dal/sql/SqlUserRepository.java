/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal.sql;

import hr.algebra.model.User;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;
import javax.sql.DataSource;
import hr.algebra.dal.UserAuthentificationRepository;

/**
 *
 * @author zakesekresa
 */
public class SqlUserRepository implements UserAuthentificationRepository {

    private static final String ISADMIN = "isAdmin";

    private static final String SELECT_USER = "{ CALL selectUser (?,?) }";
    private static final String SELECT_USERNAME = "{ CALL selectUsername (?) }";
    private static final String CREATE_USER = "{ CALL createUser (?,?,?) }";

    @Override
    public void create(User user) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_USER)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setInt(3, user.isIsAdmin() ? 1 : 0);

            stmt.executeUpdate();

        }
    }

    @Override
    public Optional<User> get(String username, String password) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USER)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new User(username, password, rs.getInt(ISADMIN) == 1));

                }
            }

        }
        return Optional.empty();
    }

    @Override
    public boolean exists(String username) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_USERNAME)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return true;
                }
            }

        }
        return false;
    }

}
