import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int priority;
    int completionTime;
    int waitingTime;
    int turnAroundTime;

    // Constructor to initialize the process details
    public Process(String name, int arrivalTime, int burstTime, int priority) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input: Number of processes
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();

        // Create an array to hold all processes
        Process[] processes = new Process[n];

        // Input: Details for each process
        for (int i = 0; i < n; i++) {
            System.out.print("Enter process name: ");
            String name = scanner.next();
            System.out.print("Enter arrival time of " + name + ": ");
            int arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time of " + name + ": ");
            int burstTime = scanner.nextInt();
            System.out.print("Enter priority of " + name + " (lower number means higher priority): ");
            int priority = scanner.nextInt();
            processes[i] = new Process(name, arrivalTime, burstTime, priority);
        }

        // Sort processes based on priority (then by arrival time if priorities are the same)
        Arrays.sort(processes, Comparator.comparingInt((Process p) -> p.priority).thenComparingInt(p -> p.arrivalTime));

        // Priority Scheduling Logic
        int currentTime = 0;

        for (int i = 0; i < n; i++) {
            Process process = processes[i];

            // If the current time is less than the arrival time of the process, advance time
            if (currentTime < process.arrivalTime) {
                currentTime = process.arrivalTime;
            }

            // Calculate the completion time, turnaround time, and waiting time
            process.completionTime = currentTime + process.burstTime;
            process.turnAroundTime = process.completionTime - process.arrivalTime;
            process.waitingTime = process.turnAroundTime - process.burstTime;

            // Update the current time to the process's completion time
            currentTime = process.completionTime;
        }

        // Output: Display the results for each process
        System.out.println("\nProcess\tPriority\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (Process process : processes) {
            System.out.println(process.name + "\t\t" + process.priority + "\t\t" + process.arrivalTime + "\t\t" + process.burstTime + "\t\t" +
                    process.completionTime + "\t\t" + process.turnAroundTime + "\t\t\t" + process.waitingTime);
        }

        scanner.close();
    }
}
