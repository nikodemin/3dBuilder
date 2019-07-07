package com.dBuider.app.Repo;

import com.dBuider.app.Model.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepo extends CrudRepository<Tool,Long>
{
    List<Tool> findByCategory (String category);

    List<Tool> findByNameContainingIgnoreCase (String name);

    @Query(value = "SELECT DISTINCT category FROM tools", nativeQuery = true)
    List<String> findCategories();

    @Query(value = "SELECT DISTINCT subcat FROM tools",nativeQuery = true)
    List<String> findSubCategories();
}
