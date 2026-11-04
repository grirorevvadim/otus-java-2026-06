package ru.otus.server;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

class StaticResourceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!"/static/style.css".equals(req.getRequestURI())) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try (var in = getClass().getResourceAsStream("/static/style.css")) {
            if (in == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            resp.setContentType("text/css; charset=UTF-8");
            in.transferTo(resp.getOutputStream());
        }
    }
}
