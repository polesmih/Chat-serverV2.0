package hw_lesson2.chat.client.gui.api;

@FunctionalInterface
public interface Receiver {
    void receive(String data);
}
