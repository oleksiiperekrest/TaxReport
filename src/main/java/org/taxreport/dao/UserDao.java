package org.taxreport.dao;

import org.taxreport.entity.Entity;
import org.taxreport.entity.User;

import java.util.Optional;

public interface UserDao<T extends Entity> extends GenericDao<T> {
    Optional<T> getByEmail(String email);
}
