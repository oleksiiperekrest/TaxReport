package org.taxreport.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.taxreport.dao.ClientDao;
import org.taxreport.dao.DaoPool;
import org.taxreport.entity.Client;
import org.taxreport.entity.Individual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private DaoPool daoPool;

    @Mock
    private ClientDao clientDao;

    @InjectMocks
    private UserServiceImpl service;

    @Test
    public void mustGetAllClients() {
        when(daoPool.getClientDao()).thenReturn(clientDao);
        when(clientDao.getAll()).thenReturn(getClientList());
        List<Client> expected = new ArrayList<>();
        Client client = new Individual(1L, "email@email.com", "password", Collections.emptyList(),
                "First name", "Last name", "1234567890");
        expected.add(client);

        assertEquals(expected, service.getAllClients());
    }

    private List<Client> getClientList() {
        List<Client> clients = new ArrayList<>();
            clients.add(new Individual(1L, "email@email.com", "password", Collections.emptyList(),
                    "First name", "Last name", "1234567890"));

        return clients;
    }


}