package com.dBuider.app.Config;

import com.dBuider.app.Model.Order;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.TranslitService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class SessionInterceptor implements HandlerInterceptor
{
    private final HttpSession session;
    private final ToolsService toolsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                              Object handler) throws Exception
    {
        session.setAttribute("categories",toolsService.getCategories());
        //todo
        //session.setAttribute("order", new Order());
        return true;
    }
}
