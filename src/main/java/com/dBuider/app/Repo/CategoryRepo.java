package com.dBuider.app.Repo;

import com.dBuider.app.Model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepo extends CrudRepository<Category,Long>
{
    Category findByNameAndSubcat(String name, String subcat);
}
