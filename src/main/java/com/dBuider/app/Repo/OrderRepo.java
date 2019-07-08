package com.dBuider.app.Repo;

import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<Order, Long>
{
    List<Order> findByUser(User user);
}
