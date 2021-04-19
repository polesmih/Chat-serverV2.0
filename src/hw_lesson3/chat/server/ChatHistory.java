package hw_lesson3.chat.server;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class ChatHistory {
    public static void writeHistory(String login, String message) {
        String path = String.format("D:/Программирование/Java/3_level/src/hw_lesson3/history_[%s].txt", login);
        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream(path, true), true)) {
            printWriter.println(message);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
