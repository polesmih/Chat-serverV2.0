package hw_lesson3.chat.client.gui.api;

@FunctionalInterface
public interface Receiver {
    void receive(String data);
}
