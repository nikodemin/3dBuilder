package com.dBuider.app.Model.Form;

import com.dBuider.app.Model.Tool;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class OrderForm
{
    private String address;
    private Tool tool;
    private Date date;
    private Integer fordays;
}
