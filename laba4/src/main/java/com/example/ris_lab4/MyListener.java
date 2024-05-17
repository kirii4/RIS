package com.example.ris_lab4;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup",
                propertyValue = "jms/Queue"),
        @ActivationConfigProperty(propertyName = "destinationType",
                propertyValue = "jakarta.jms.Queue")
})
public class MyListener implements MessageListener {

    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();

                removeMessageFromFile(text);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void removeMessageFromFile(String message) {
        try {
            File inputFile = new File("D:/Desktop/messages.txt");
            FileReader fileReader = new FileReader(inputFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals(message)) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }
            bufferedReader.close();
            FileWriter fileWriter = new FileWriter(inputFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(content.toString());
            bufferedWriter.close();

            System.out.println("Deleted message: " + message);
        } catch (IOException e) {
            System.out.println("Ошибка при удалении сообщения из файла: " + e.getMessage());
        }
    }
}
