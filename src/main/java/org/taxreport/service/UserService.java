package org.taxreport.service;

import org.taxreport.entity.Client;
import org.taxreport.entity.Personnel;
import org.taxreport.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void create(User user);

    List<Client> getAllClients();
    List<Personnel> getAllPersonnel();

    Client getClientById(Long id);
    Personnel getPersonnelById(Long id);

    User getByEmail(String email);

    boolean login(String email, String password);
}
