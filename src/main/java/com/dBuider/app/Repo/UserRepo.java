package com.dBuider.app.Repo;

import com.dBuider.app.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<User,Long>
{
    User findByUsername(String username);
}
