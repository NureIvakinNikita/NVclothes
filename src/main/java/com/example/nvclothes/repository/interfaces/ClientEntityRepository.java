package com.example.nvclothes.nvclothes.repository.interfaces;

import com.example.nvclothes.nvclothes.entity.ClientEntity;

import java.util.Optional;

public interface ClientEntityRepository {

    void createClient(ClientEntity client);

    void updateClient(ClientEntity client);

    Optional<ClientEntity> findClientById(Long id);

    Optional<ClientEntity> findClientByEmail(String email);

}
