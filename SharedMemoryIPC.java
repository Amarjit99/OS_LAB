import java.util.concurrent.Semaphore;
import java.util.Scanner;

class SharedMemory {
    int[] buffer;
    int size;
    
    // Constructor to initialize the buffer
    public SharedMemory(int size) {
        this.size = size;
        this.buffer = new int[size];
    }
}

class Producer extends Thread {
    SharedMemory sharedMemory;
    Semaphore semEmpty;
    Semaphore semFull;
    int itemsToProduce;
    
    // Constructor to initialize the shared memory and semaphores
    public Producer(SharedMemory sharedMemory, Semaphore semEmpty, Semaphore semFull, int itemsToProduce) {
        this.sharedMemory = sharedMemory;
        this.semEmpty = semEmpty;
        this.semFull = semFull;
        this.itemsToProduce = itemsToProduce;
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToProduce; i++) {
            try {
                // Wait for an empty slot
                semEmpty.acquire();
                
                // Produce data and write to shared memory
                System.out.println("Producer is writing " + i + " to shared memory.");
                sharedMemory.buffer[i % sharedMemory.size] = i;  // Circular buffer behavior
                
                // Signal the consumer that data is ready
                semFull.release();
                
                Thread.sleep(1000);  // Simulating time taken to produce data
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    SharedMemory sharedMemory;
    Semaphore semEmpty;
    Semaphore semFull;
    int itemsToConsume;
    
    // Constructor to initialize the shared memory and semaphores
    public Consumer(SharedMemory sharedMemory, Semaphore semEmpty, Semaphore semFull, int itemsToConsume) {
        this.sharedMemory = sharedMemory;
        this.semEmpty = semEmpty;
        this.semFull = semFull;
        this.itemsToConsume = itemsToConsume;
    }

    @Override
    public void run() {
        for (int i = 0; i < itemsToConsume; i++) {
            try {
                // Wait for the data to be produced
                semFull.acquire();
                
                // Consume data from shared memory
                int data = sharedMemory.buffer[i % sharedMemory.size];
                System.out.println("Consumer read " + data + " from shared memory.");
                
                // Signal the producer that a slot is now empty
                semEmpty.release();
                
                Thread.sleep(1000);  // Simulating time taken to consume data
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class SharedMemoryIPC {
    public static void main(String[] args) {
        // Get user input for buffer size and number of items to produce and consume
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the size of the buffer: ");
        int bufferSize = scanner.nextInt();
        
        System.out.print("Enter the number of items the producer will produce: ");
        int itemsToProduce = scanner.nextInt();
        
        System.out.print("Enter the number of items the consumer will consume: ");
        int itemsToConsume = scanner.nextInt();
        
        // Create shared memory with user-defined buffer size
        SharedMemory sharedMemory = new SharedMemory(bufferSize);
        
        // Initialize semaphores: bufferSize empty slots and 0 full slots initially
        Semaphore semEmpty = new Semaphore(bufferSize);  // Initially all slots are empty
        Semaphore semFull = new Semaphore(0);   // Initially no slots are full
        
        // Create producer and consumer threads
        Producer producer = new Producer(sharedMemory, semEmpty, semFull, itemsToProduce);
        Consumer consumer = new Consumer(sharedMemory, semEmpty, semFull, itemsToConsume);
        
        // Start the producer and consumer threads
        producer.start();
        consumer.start();
        
        try {
            // Wait for both threads to complete
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        scanner.close();  // Close the scanner after use
    }
}
