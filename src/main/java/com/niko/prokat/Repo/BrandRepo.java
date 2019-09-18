package com.niko.prokat.Repo;

import com.niko.prokat.Model.entity.Brand;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepo extends CrudRepository<Brand, Long> {
    List<Brand> findByName(String name);
}
