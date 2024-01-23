package com.laptopshop.library.service.impl;

import com.laptopshop.library.model.City;
import com.laptopshop.library.repository.CityRepository;
import com.laptopshop.library.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }
}
