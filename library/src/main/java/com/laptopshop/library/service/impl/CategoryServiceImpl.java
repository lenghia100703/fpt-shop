package com.laptopshop.library.service.impl;

import com.laptopshop.library.dto.CategoryDto;
import com.laptopshop.library.model.Category;
import com.laptopshop.library.repository.CategoryRepository;
import com.laptopshop.library.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        Category categorySave = new Category();
        categorySave.setName(category.getName());
        categorySave.setActivated(true);
        categorySave.setDeleted(false);
        return categoryRepository.save(categorySave);

    }

    @Override
    public Category update(Category category) {
        Category categoryUpdate = categoryRepository.getReferenceById(category.getId());
        categoryUpdate.setName(category.getName());
        return categoryRepository.save(categoryUpdate);
    }

    @Override
    public List<Category> findAllByActivatedTrue() {
        return categoryRepository.findAllByActivatedTrue();
    }

    @Override
    public List<Category> findALl() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Category> curr = categoryRepository.findById(id);
        if(curr.isPresent()){
            Category category = curr.get();
            category.setActivated(false);
            category.setDeleted(true);
            categoryRepository.save(category);
        }
    }

    @Override
    public void enableById(Long id) {
        Optional<Category> curr = categoryRepository.findById(id);
        if(curr.isPresent()){
            Category category = curr.get();
            category.setActivated(true);
            category.setDeleted(false);
            categoryRepository.save(category);
        }
    }

    @Override
    public List<CategoryDto> getCategoriesAndSize() {
        List<CategoryDto> categories = categoryRepository.getCategoriesAndSize();
        return categories;
    }

}

