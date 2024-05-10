package com.example.nvclothes.service;

import com.example.nvclothes.entity.City;
import com.example.nvclothes.exception.CityNotFoundException;
import com.example.nvclothes.repository.interfaces.CityRepositoryInterface;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepositoryInterface cityRepository;

    public City getCityById(Long id) throws CityNotFoundException{
        Optional<City> cityOptional = cityRepository.getCityById(id);
        if (!cityOptional.isPresent()){
            throw new CityNotFoundException("City wasn't found by id: "+id);
        }
        return cityOptional.get();
    };

    public City getCityByName(String name) throws CityNotFoundException {
        Optional<City> cityOptional = cityRepository.getCityByName(name);
        if (!cityOptional.isPresent()){
            throw new CityNotFoundException("City wasn't found by name: "+name);
        }
        return cityOptional.get();
    }


    public void deleteCityById(Long id){
        cityRepository.deleteCityById(id);
    }

    public List<City> getAll(){
        return cityRepository.findAll();
    }
}
