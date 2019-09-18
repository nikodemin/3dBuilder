package com.niko.prokat.Repo;

import com.niko.prokat.Model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);
}
