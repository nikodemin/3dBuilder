package com.niko.prokat.Repo;

import com.niko.prokat.Model.entity.Order;
import com.niko.prokat.Model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends PagingAndSortingRepository<Order, Long> {
    List<Order> findByUser(User user);

    List<Order> findByDoneIsFalse();

    List<Order> findByDoneIsTrue();
}
