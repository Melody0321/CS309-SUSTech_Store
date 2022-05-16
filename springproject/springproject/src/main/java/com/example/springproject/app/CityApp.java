package com.example.springproject.app;

import com.example.springproject.domain.City;
import com.example.springproject.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/city")
public class CityApp {
    @Autowired
    private CityService cityService;

    @PostMapping("/addCity")
    public City addCity(City city){
        cityService.saveCity(city);
        return city;
    }

}
