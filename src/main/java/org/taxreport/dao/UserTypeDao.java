package org.taxreport.dao;

import org.taxreport.dao.GenericDao;
import org.taxreport.entity.UserType;

import java.util.Optional;

public interface UserTypeDao extends GenericDao<UserType> {
    Optional<Long> getIdByType(String type);
}
