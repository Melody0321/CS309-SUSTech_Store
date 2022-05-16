package com.example.springproject.service;

import com.example.springproject.api.CityRepository;
import com.example.springproject.domain.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService{
    @Autowired
    CityRepository cityRepository;

    @Override
    public City saveCity(City city){
        return cityRepository.save(city);
    }
}
