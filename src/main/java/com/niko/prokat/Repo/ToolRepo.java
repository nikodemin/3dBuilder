package com.niko.prokat.Repo;

import com.niko.prokat.Model.entity.Category;
import com.niko.prokat.Model.entity.Tool;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepo extends CrudRepository<Tool, Long> {
    List<Tool> findByCategory(Category category);

    List<Tool> findByNameContainingIgnoreCase(String name);
}
