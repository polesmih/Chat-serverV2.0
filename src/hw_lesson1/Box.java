package hw_lesson1;

import java.util.ArrayList;
import java.util.Arrays;

public class Box <T extends Fruit> {
    ArrayList <T> fruits;
    private float BoxWeight = 0.0f;

    public Box(T... fruits) {
        this.fruits = new ArrayList<>(Arrays.asList(fruits));
    }

    public void add(T... fruits) {
        this.fruits.addAll(Arrays.asList(fruits));
    }

    float getBoxWeight() {
        if (fruits.size() > 0) {
            BoxWeight = this.fruits.get(0).getWeight() * fruits.size();
        }
        return BoxWeight;
    }

    public boolean compare(Box box) {
        return (box.getBoxWeight() == this.getBoxWeight());
    }

    void move(Box<T> box) {
        box.fruits.addAll(this.fruits);
        fruits.clear();
    }
}
