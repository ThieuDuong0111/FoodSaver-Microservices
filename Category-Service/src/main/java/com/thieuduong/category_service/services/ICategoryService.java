package com.thieuduong.category_service.services;

import java.util.List;

import com.thieuduong.category_service.models.Category;
import com.thieuduong.commons.dto.CategoryDTO;

public interface ICategoryService {

	CategoryDTO convertToDto(Category category);

	Category convertToEntity(CategoryDTO categoryDTO);

	List<CategoryDTO> getAllCategories();

	Category getCategoryById(int id);

	Category getCategoryByImageUrl(String url);

	void deleteCategoryById(int id);
}
