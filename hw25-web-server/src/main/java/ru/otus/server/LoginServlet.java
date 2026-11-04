package ru.otus.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

class LoginServlet extends BaseHtmlServlet {

    private final String adminLogin;
    private final String adminPassword;

    LoginServlet(String adminLogin, String adminPassword) {
        this.adminLogin = adminLogin;
        this.adminPassword = adminPassword;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        renderForm(resp, false);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (matches(adminLogin, req.getParameter("login")) & matches(adminPassword, req.getParameter("password"))) {
            var oldSession = req.getSession(false);
            if (oldSession != null) {
                oldSession.invalidate();
            }
            req.getSession(true).setAttribute(AuthFilter.ADMIN_ATTRIBUTE, adminLogin);
            resp.sendRedirect("/admin/clients");
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            renderForm(resp, true);
        }
    }

    private boolean matches(String expected, String actual) {
        return actual != null
                && MessageDigest.isEqual(
                        expected.getBytes(StandardCharsets.UTF_8), actual.getBytes(StandardCharsets.UTF_8));
    }

    private void renderForm(HttpServletResponse resp, boolean failed) throws IOException {
        var out = beginHtml(resp, "Вход");
        out.println("<h1>Вход для администратора</h1>");
        if (failed) {
            out.println("<p class=\"error\">Неверный логин или пароль</p>");
        }
        out.println("""
                <form method="post" action="/login">
                    <label>Логин <input type="text" name="login" required autofocus></label>
                    <label>Пароль <input type="password" name="password" required></label>
                    <button type="submit">Войти</button>
                </form>
                """);
        endHtml(out);
    }
}
