package com.dBuider.app.Repo;

import com.dBuider.app.Model.Category;
import com.dBuider.app.Model.Tool;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepo extends CrudRepository<Tool,Long>
{
    List<Tool> findByCategory (Category category);

    List<Tool> findByNameContainingIgnoreCase (String name);
}
