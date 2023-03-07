package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientEntityRepositoryInterface extends JpaRepository<ClientEntity, Long> {

    Optional<ClientEntity> getClientEntityById(@Param("id") Long id);

    Optional<ClientEntity> getClientEntityByEmail(@Param("email") String email);

    void deleteClientEntityById(@Param("id") Long id);


}
