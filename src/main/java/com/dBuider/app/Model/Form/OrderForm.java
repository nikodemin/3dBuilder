package com.dBuider.app.Model.Form;

import com.dBuider.app.Model.Tool;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderForm
{
    private String address;
    private String date;
    private Integer fordays;
}
