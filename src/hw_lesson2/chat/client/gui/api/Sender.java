package hw_lesson2.chat.client.gui.api;

@FunctionalInterface
public interface Sender {
    void send(String data);
}