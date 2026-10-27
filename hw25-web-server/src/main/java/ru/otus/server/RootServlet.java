package ru.otus.server;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

class RootServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if ("/".equals(req.getRequestURI())) {
            resp.sendRedirect("/admin/clients");
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
