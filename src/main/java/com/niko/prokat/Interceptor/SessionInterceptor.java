package com.niko.prokat.Interceptor;

import com.niko.prokat.Model.dto.OrderDto;
import com.niko.prokat.Service.ToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class SessionInterceptor implements HandlerInterceptor {
    private final HttpSession session;
    private final ToolService toolService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        session.setAttribute("categories", toolService.getRootCategories());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        if (!username.equals("anonymousUser"))
            session.setAttribute("username", username);

        if (session.getAttribute("order") == null) {
            session.setAttribute("order", new OrderDto());
        }
        return true;
    }
}
