package org.taxreport.dao;

import org.taxreport.entity.Personnel;

import java.util.List;
import java.util.Optional;

public interface PersonnelDao extends UserDao<Personnel> {

    @Override
    Optional<Personnel> getByEmail(String email);

    @Override
    Optional<Personnel> getById(Long id);

    @Override
    List<Personnel> getAll();

    List<Personnel> getByIdList(List<Long> ids);
}
