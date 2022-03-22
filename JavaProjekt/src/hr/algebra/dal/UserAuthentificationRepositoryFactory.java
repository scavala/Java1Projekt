/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.dal;

import hr.algebra.dal.sql.SqlUserRepository;

/**
 *
 * @author zakesekresa
 */
public class UserAuthentificationRepositoryFactory {

    private UserAuthentificationRepositoryFactory() {
    }

    public static UserAuthentificationRepository getRepository() throws Exception {

        return new SqlUserRepository();
    }

}
