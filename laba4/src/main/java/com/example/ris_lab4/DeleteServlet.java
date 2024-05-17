package com.example.ris_lab4;

import jakarta.annotation.Resource;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/del")
public class DeleteServlet extends HttpServlet {


    //для тех кто тоже тут
    //queue и connection factory создается в
    // гласфише через порт админа (localhost:4848) как в методичке :3
    @Resource(mappedName = "jms/Queue")
    private Queue queue;

    @Resource(mappedName = "jms/ConnectionFactory1")
    private ConnectionFactory connectionFactory;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = req.getParameter("message");
        msg = msg.replaceAll("[\\r\\n]", "");

        try (JMSContext context = connectionFactory.createContext();) {

            context.createProducer().send(queue, msg);
        }

        resp.sendRedirect("/RIS_lab4-1.0-SNAPSHOT/ser");
    }
}
