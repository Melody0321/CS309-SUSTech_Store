package com.example.springproject.api;

import com.example.springproject.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends
        JpaRepository<City,Long> {
}
