package com.dBuider.app.Controller;

import com.dBuider.app.Model.Form.OrderForm;
import com.dBuider.app.Model.Order;
import com.dBuider.app.Repo.OrderRepo;
import com.dBuider.app.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    @GetMapping("/make")
    public String getOrderPage(Model model, Principal principal)
    {
        OrderForm form = new OrderForm();
        if (principal != null)
        {
            model.addAttribute("username",principal.getName());
            form.setAddress(userRepo.findByUsername(principal.getName()).getAddress());
        }
        model.addAttribute("form", form);
        return "makeorder";
    }

    @PostMapping("/make")
    public String getForm(HttpServletRequest request,
                          Model model,
                          @ModelAttribute("form") OrderForm form, Principal principal)
    {
        return this.doUpload(request,model,form, principal);
    }

    @GetMapping("/recent")
    public String recent(Model model, Principal principal)
    {
        if (principal != null)
        {
            List<Order> orders = orderRepo.findByUser(
                    userRepo.findByUsername(principal.getName()));
            model.addAttribute("orders",orders);
        }
        return "recentorder";
    }

    private String doUpload(HttpServletRequest request, Model model,
                            OrderForm form, Principal principal)
    {

        /*// Root Directory.
        String uploadRootPath = config.getUploadPath();
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
            String name = (new Date()).getTime()+"_"+fileData.getOriginalFilename();
            System.out.println("Client File Name = " + name);

            if (fileData.getOriginalFilename() != null && fileData.getOriginalFilename().length() > 0)
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

        User user = userRepo.findByUsername(principal.getName());
        Order order = new Order(form.getAddress(),user
                ,uploadedFiles.toArray(String[]::new), new Date(),false);
        orderRepo.save(order);
        return "redirect:/order/recent";
         */
        return "";
    }
}
