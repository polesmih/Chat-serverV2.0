package hw_lesson3.chat.client.gui.api;

@FunctionalInterface
public interface Sender {
    void send(String data);
}