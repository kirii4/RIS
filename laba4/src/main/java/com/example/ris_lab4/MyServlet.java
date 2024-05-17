package com.example.ris_lab4;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ser")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<String> messages = new ArrayList<>();
        try {
            File inputFile = new File("D:/Desktop/messages.txt");
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                messages.add(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("Ошибка при чтении сообщений из файла: " + e.getMessage());
        }


        req.setAttribute("msgs", messages);
        getServletContext().getRequestDispatcher("/msg.jsp").forward(req, resp);
    }
}
