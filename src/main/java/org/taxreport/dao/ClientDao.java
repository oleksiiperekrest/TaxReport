package org.taxreport.dao;

import org.taxreport.entity.Client;
import org.taxreport.entity.User;

import java.util.Optional;

public interface ClientDao extends UserDao<Client> {

    @Override
    Optional<Client> getByEmail(String email);

    @Override
    Optional<Client> getById(Long id);
}
