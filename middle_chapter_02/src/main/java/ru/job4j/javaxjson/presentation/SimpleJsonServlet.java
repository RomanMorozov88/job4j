package ru.job4j.javaxjson.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.javaxjson.models.UserForJson;
import ru.job4j.javaxjson.persistent.StoreInMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class SimpleJsonServlet extends HttpServlet {

    private final StoreInMap store = StoreInMap.getInstance();

    /**
     * Берём все объекты из store и в ручную перегоняем их в Json строку.
     * И отправляем.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.append("[");
        List<UserForJson> list = store.getAllUsers();
        for (int i = 0; i < list.size(); i++) {
            writer.append(String.format("{\"fnm\":\"%s\",\"lnm\":\"%s\",\"gnd\":\"%s\",\"dsc\":\"%s\"}",
                    list.get(i).getFirstName(), list.get(i).getLastName(),
                    list.get(i).getGender(), list.get(i).getDescription()));
            //т.к. при наличии лишней запятой будет ошибка при парсинге- проверяем,
            // нужна ли она при данном шаге итерации.
            if (i + 1 != list.size()) {
                writer.append(',');
            }
        }
        writer.append("]");
        writer.flush();
    }

    /**
     * Получаем данные и с помощью objectMapper конвертируем в UserForJason.
     * Помещаем в store.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String string = null;
        try (BufferedReader br = req.getReader()) {
            while ((string = br.readLine()) != null) {
                sb.append(string);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            UserForJson user = objectMapper.readValue(sb.toString(), UserForJson.class);
            store.add(user);
        }
    }
}
