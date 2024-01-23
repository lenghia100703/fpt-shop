package com.laptopshop.library.service.impl;

import com.laptopshop.library.dto.BrandDto;
import com.laptopshop.library.model.Brand;
import com.laptopshop.library.repository.BrandRepository;
import com.laptopshop.library.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    @Override
    public Brand save(Brand brand) {
        Brand brandSave = new Brand();
        brandSave.setName(brand.getName());
        brandSave.setActivated(true);
        brandSave.setDeleted(false);
        return brandRepository.save(brandSave);

    }

    @Override
    public Brand update(Brand brand) {
        Brand brandUpdate = brandRepository.getReferenceById(brand.getId());
        brandUpdate.setName(brand.getName());
        return brandRepository.save(brandUpdate);
    }

    @Override
    public List<Brand> findAllByActivatedTrue() {
        return brandRepository.findAllByActivatedTrue();
    }

    @Override
    public List<Brand> findALl() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Brand> curr = brandRepository.findById(id);
        if(curr.isPresent()){
            Brand brand = curr.get();
            brand.setActivated(false);
            brand.setDeleted(true);
            brandRepository.save(brand);
        }
    }

    @Override
    public void enableById(Long id) {
        Optional<Brand> curr = brandRepository.findById(id);
        if(curr.isPresent()){
            Brand brand = curr.get();
            brand.setActivated(true);
            brand.setDeleted(false);
            brandRepository.save(brand);
        }

    }

    @Override
    public List<BrandDto> getBrandsAndSize() {
        List<BrandDto> brands = brandRepository.getBrandsAndSize();
        return brands;
    }

}


