package com.laptopshop.library.service;


import com.laptopshop.library.dto.BrandDto;
import com.laptopshop.library.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    Brand save(Brand Brand);

    Brand update(Brand Brand);

    List<Brand> findAllByActivatedTrue();

    List<Brand> findALl();

    Optional<Brand> findById(Long id);

    void deleteById(Long id);

    void enableById(Long id);

    List<BrandDto> getBrandsAndSize();
}
