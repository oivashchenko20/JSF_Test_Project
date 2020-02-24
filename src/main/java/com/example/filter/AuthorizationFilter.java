package com.example.filter;

import com.example.beans.LoginBean;
import lombok.NoArgsConstructor;

import javax.faces.application.ResourceHandler;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@NoArgsConstructor
@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class AuthorizationFilter implements Filter {
    LoginBean bean = new LoginBean();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession(false);

        String loginURI = servletRequest.getContextPath() + "/login";
        String registrationURI = servletRequest.getContextPath() + "/registration";
        String forgotPasswordURI = servletRequest.getContextPath() + "/forgotPassword";
        String forgotAnswerURI = servletRequest.getContextPath() + "/forgotAnswer";

        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = servletRequest.getRequestURI().equals(loginURI);
        boolean forgotAnswerRequest = servletRequest.getRequestURI().equals(forgotAnswerURI);
        boolean registrationRequest = servletRequest.getRequestURI().equals(registrationURI);
        boolean forgotPasswordRequest = servletRequest.getRequestURI().equals(forgotPasswordURI);
        boolean resourceRequest = servletRequest.getRequestURI().startsWith(servletRequest.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);

        if (forgotAnswerRequest | forgotPasswordRequest | loggedIn | loginRequest | resourceRequest | registrationRequest) {
            chain.doFilter(request, response);
        } else {
            servletResponse.sendRedirect(loginURI);
        }
    }

    @Override
    public void destroy() {
    }
}
