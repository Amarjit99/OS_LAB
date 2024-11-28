import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

class ProducerConsumerProblem {
    private final Queue<Integer> buffer;
    private final int maxSize;
    private final Semaphore empty;
    private final Semaphore full;
    private final Semaphore mutex;

    public ProducerConsumerProblem(int size) {
        buffer = new LinkedList<>();
        maxSize = size;
        empty = new Semaphore(maxSize); // Tracks empty slots
        full = new Semaphore(0); // Tracks filled slots
        mutex = new Semaphore(1); // Controls exclusive access to the buffer
    }

    // Producer Thread
    class Producer extends Thread {
        private final int itemsToProduce;

        public Producer(int items) {
            this.itemsToProduce = items;
        }

        public void run() {
            for (int i = 0; i < itemsToProduce; i++) {
                try {
                    empty.acquire(); // Wait for an empty slot
                    mutex.acquire(); // Lock the buffer

                    // Produce an item and add to buffer
                    int item = i + 1;
                    buffer.add(item);
                    System.out.println("Produced: " + item);

                    mutex.release(); // Release buffer lock
                    full.release(); // Indicate a filled slot

                    // Simulate time taken to produce
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Consumer Thread
    class Consumer extends Thread {
        private final int itemsToConsume;

        public Consumer(int items) {
            this.itemsToConsume = items;
        }

        public void run() {
            for (int i = 0; i < itemsToConsume; i++) {
                try {
                    full.acquire(); // Wait for a filled slot
                    mutex.acquire(); // Lock the buffer

                    // Consume an item from the buffer
                    int item = buffer.poll();
                    System.out.println("Consumed: " + item);

                    mutex.release(); // Release buffer lock
                    empty.release(); // Indicate an empty slot

                    // Simulate time taken to consume
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // User input for buffer size
        System.out.print("Enter the buffer size: ");
        int bufferSize = scanner.nextInt();

        // User input for number of items each producer will produce
        System.out.print("Enter the number of items for producer to produce: ");
        int itemsToProduce = scanner.nextInt();

        // User input for number of items each consumer will consume
        System.out.print("Enter the number of items for consumer to consume: ");
        int itemsToConsume = scanner.nextInt();

        ProducerConsumerProblem pc = new ProducerConsumerProblem(bufferSize);

        // Start producer and consumer threads
        Producer producer = pc.new Producer(itemsToProduce);
        Consumer consumer = pc.new Consumer(itemsToConsume);

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        scanner.close();
        System.out.println("Production and Consumption completed.");
    }
}
