import java.util.*;

class Process {
    int pid;       // Process ID
    int arrival;   // Arrival Time
    int burst;     // Burst Time
    int completion;// Completion Time
    int waiting;   // Waiting Time
    int turnaround;// Turnaround Time

    public Process(int pid, int arrival, int burst) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
    }
}

public class SJFScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();

        // Create an array of processes
        Process[] processes = new Process[n];

        // Input process details
        for (int i = 0; i < n; i++) {
            System.out.println("Enter Arrival Time and Burst Time for Process " + (i + 1) + ": ");
            int arrivalTime = sc.nextInt();
            int burstTime = sc.nextInt();
            processes[i] = new Process(i + 1, arrivalTime, burstTime);
        }

        // Sort processes based on arrival time and burst time
        Arrays.sort(processes, Comparator.comparingInt((Process p) -> p.arrival)
                                         .thenComparingInt(p -> p.burst));

        int currentTime = 0;
        float totalWaitingTime = 0, totalTurnaroundTime = 0;

        // Process each task in order
        for (int i = 0; i < n; i++) {
            // Update current time based on when process arrives
            if (currentTime < processes[i].arrival) {
                currentTime = processes[i].arrival;
            }

            // Completion time for the current process
            processes[i].completion = currentTime + processes[i].burst;

            // Update current time
            currentTime = processes[i].completion;

            // Calculate turnaround time and waiting time
            processes[i].turnaround = processes[i].completion - processes[i].arrival;
            processes[i].waiting = processes[i].turnaround - processes[i].burst;

            // Update total waiting and turnaround times
            totalWaitingTime += processes[i].waiting;
            totalTurnaroundTime += processes[i].turnaround;
        }

        // Display process details
        System.out.println("\nProcess\tArrival Time\tBurst Time\tCompletion Time\tWaiting Time\tTurnaround Time");
        for (Process p : processes) {
            System.out.println(p.pid + "\t\t" + p.arrival + "\t\t" + p.burst + "\t\t" + p.completion + "\t\t" + p.waiting + "\t\t" + p.turnaround);
        }

        // Calculate and display average waiting time and turnaround time
        System.out.printf("\nAverage Waiting Time: %.2f", (totalWaitingTime / n));
        System.out.printf("\nAverage Turnaround Time: %.2f", (totalTurnaroundTime / n));

        sc.close();
    }
}
