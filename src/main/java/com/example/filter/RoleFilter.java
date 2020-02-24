package com.example.filter;

import lombok.NoArgsConstructor;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@NoArgsConstructor
@WebFilter(filterName = "RoleFilter", urlPatterns = {"/fields", "/responses"})
public class RoleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession(false);

        String homeURI = servletRequest.getContextPath() + "/home.xhtml";
        boolean isRole = session.getAttribute("role").equals("ADMIN");
        boolean homeRequest = servletRequest.getRequestURI().equals(homeURI);
        boolean resourceRequest = servletRequest.getRequestURI().startsWith(servletRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);

        if (isRole | homeRequest | resourceRequest) {
            chain.doFilter(request, response);
        } else {
            servletResponse.sendRedirect(homeURI);
        }
    }

    @Override
    public void destroy() {
    }
}
