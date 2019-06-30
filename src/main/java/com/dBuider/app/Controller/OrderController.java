package com.dBuider.app.Controller;

import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.OrderForm;
import com.dBuider.app.Model.User;
import com.dBuider.app.Repo.OrderRepo;
import com.dBuider.app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    @GetMapping("/make")
    public String getOrderPage(Model model)
    {
        model.addAttribute("form", new OrderForm());
        return "makeorder";
    }

    @PostMapping("/make")
    public String getForm(HttpServletRequest request,
                          Model model,
                          @ModelAttribute("form") OrderForm form)
    {
        return this.doUpload(request,model,form);
    }

    @GetMapping("/recent")
    public String recent(Model model)
    {
        //model.addAttribute("orders")
        return "recentorder";
    }

    private String doUpload(HttpServletRequest request, Model model,
                            OrderForm form)
    {

        // Root Directory.
        String uploadRootPath = request.getServletContext().getRealPath("upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        // Create directory if it not exists.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        MultipartFile[] fileDatas = form.getFiles();
        List<String> uploadedFiles = new ArrayList<>();
        List<String> failedFiles = new ArrayList<>();

        for (MultipartFile fileData : fileDatas)
        {

            // Client File Name
            String name = fileData.getOriginalFilename();
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0)
            {
                try
                {
                    // Create the files at server
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();

                    uploadedFiles.add(serverFile.toString());
                    System.out.println("Write files: " + serverFile);

                } catch (Exception e) {
                    System.out.println("Error Write files: " + name);
                    failedFiles.add(name);
                }
            }
        }

        User user = new User("lalka","123");
        userRepo.save(user);
        Order order = new Order(form.getAddress(),user
                ,uploadedFiles.toArray(String[]::new), new Date());
        orderRepo.save(order);
        orderRepo.findAll().forEach(o->{
            for (String file:o.getFilenames())
            {
                System.out.println("FILENAME="+file);
            }
        });
        return "redirect:/order/recent";
    }
}
