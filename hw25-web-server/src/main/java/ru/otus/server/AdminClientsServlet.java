package ru.otus.server;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

class AdminClientsServlet extends BaseHtmlServlet {

    private final transient DBServiceClient dbServiceClient;

    AdminClientsServlet(DBServiceClient dbServiceClient) {
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var out = beginHtml(resp, "Клиенты");
        out.println("<h1>Клиенты</h1>");
        out.println("""
                <form method="post" action="/logout" class="logout">
                    <button type="submit">Выйти</button>
                </form>
                <h2>Создать клиента</h2>
                <form method="post" action="/admin/clients">
                    <label>Имя <input type="text" name="name" required maxlength="255"></label>
                    <button type="submit">Создать</button>
                </form>
                <h2>Список клиентов</h2>
                <table>
                    <thead><tr><th>ID</th><th>Имя</th></tr></thead>
                    <tbody>
                """);
        for (Client client : dbServiceClient.findAll()) {
            out.printf("<tr><td>%d</td><td>%s</td></tr>%n", client.getId(), escape(client.getName()));
        }
        out.println("</tbody></table>");
        endHtml(out);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        var name = req.getParameter("name");
        if (name == null || name.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Name is required");
            return;
        }
        dbServiceClient.saveClient(new Client(name.trim()));
        resp.sendRedirect("/admin/clients");
    }
}
