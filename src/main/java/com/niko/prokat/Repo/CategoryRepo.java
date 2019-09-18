package com.niko.prokat.Repo;

import com.niko.prokat.Model.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category, Long> {
    Category findByNameIgnoreCase(String name);

    List<Category> findCategoriesByChildren_Empty();
}
