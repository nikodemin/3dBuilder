package com.dBuider.app.Repo;

import com.dBuider.app.Model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category,Long>
{
    Category findByNameIgnoreCaseAndSubcatIgnoreCase(String name, String subcat);
}
