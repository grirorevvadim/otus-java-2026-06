package ru.otus.server;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

class AuthFilter implements Filter {

    static final String ADMIN_ATTRIBUTE = "admin";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) request;
        var session = httpRequest.getSession(false);
        if (session != null && session.getAttribute(ADMIN_ATTRIBUTE) != null) {
            chain.doFilter(request, response);
            return;
        }
        ((HttpServletResponse) response).sendRedirect("/login");
    }
}
