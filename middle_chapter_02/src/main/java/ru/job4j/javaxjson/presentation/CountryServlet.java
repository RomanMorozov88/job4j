package ru.job4j.javaxjson.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.job4j.javaxjson.persistent.CountryCityStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CountryServlet extends HttpServlet {

    private final CountryCityStore store = CountryCityStore.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        ObjectMapper mapper = new ObjectMapper();
        writer.append(mapper.writeValueAsString(store.getCountries()));
        writer.flush();
    }
}
