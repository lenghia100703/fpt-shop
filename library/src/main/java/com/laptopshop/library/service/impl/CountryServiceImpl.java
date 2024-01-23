package com.laptopshop.library.service.impl;

import com.laptopshop.library.model.Country;
import com.laptopshop.library.repository.CountryRepository;
import com.laptopshop.library.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
        return countryRepository.findAll();
    }
}
