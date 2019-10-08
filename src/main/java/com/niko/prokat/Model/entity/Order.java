package com.niko.prokat.Model.entity;

import com.niko.prokat.Model.enums.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "uncompletedOrders")
@Data
public class Order extends AbstractEntity {
    private String address;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Tool> tools;
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;
    private Date date;
    private Integer fordays;
    private boolean done;
    private Integer total;
    private Integer pledge;
    private OrderStatus status;
}
