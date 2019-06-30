package com.dBuider.app.Model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class OrderForm
{
    private String address;
    private MultipartFile[] files;
}
