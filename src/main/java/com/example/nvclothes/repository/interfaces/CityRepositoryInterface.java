package com.example.nvclothes.repository.interfaces;

import com.example.nvclothes.entity.City;
import jakarta.transaction.Transactional;
import nonapi.io.github.classgraph.utils.VersionFinder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepositoryInterface extends JpaRepository<City, Long> {

    Optional<City> getCityById(Long id);

    Optional<City> getCityByName(String name);

    @Transactional
    void deleteCityById(Long id);
}
