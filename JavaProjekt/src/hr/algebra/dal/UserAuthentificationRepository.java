/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.model.User;
import java.util.Optional;

/**
 *
 * @author zakesekresa
 */
public interface UserAuthentificationRepository {

    void create(User user) throws Exception;

    Optional<User> get(String username, String password) throws Exception;

    boolean exists(String username) throws Exception;

}
