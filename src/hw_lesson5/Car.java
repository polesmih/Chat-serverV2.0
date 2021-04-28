package hw_lesson5;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Car  implements Runnable {

    private static int CARS_COUNT;
    static {
        CARS_COUNT = 0;
    }

    private Race race;
    private int speed;
    private String name;

    //add
    private final CyclicBarrier cyclicBarrier;
    private static boolean winner;
    private CountDownLatch countDownLatch;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    //code changed
    public Car(Race race, int speed, CyclicBarrier cyclicBarrier, CountDownLatch countDownLatch) {
        this.race = race;
        this.speed = speed;
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override

    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");

            cyclicBarrier.await();
            cyclicBarrier.await();

            for (int i = 0; i < race.getStages().size(); i++) {
                race.getStages().get(i).go(this);
            }

            checkWinner(this);
            cyclicBarrier.await();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    //add method

    private static synchronized void checkWinner(Car c) {
        if (!winner) {
            System.out.println(c.name + " - WIN");
            winner = true;
        }
    }
}
