package com.dBuider.app.Config;

import com.dBuider.app.Model.Order;
import com.dBuider.app.Model.Tool;
import com.dBuider.app.Model.User;
import com.dBuider.app.Service.Interfaces.ToolsService;
import com.dBuider.app.Service.UserRepositoryUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class SessionInterceptor implements HandlerInterceptor
{
    private final HttpSession session;
    private final ToolsService toolsService;
    private final UserRepositoryUserDetailsService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                              Object handler) throws Exception
    {
        session.setAttribute("categories",toolsService.getCategories());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (!username.equals("anonymousUser"))
            session.setAttribute("username",username);

        if (session.getAttribute("order") == null
                && session.getAttribute("username") != null)
        {
            User user = userService.
                    getUser((String)session.getAttribute("username"));
            session.setAttribute("order", new Order(user.getAddress(), new ArrayList<Tool>(),
                    user, new Date(), 1, false));
        }

        return true;
    }
}
