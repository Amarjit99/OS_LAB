import java.util.Scanner;

public class RoundRobinScheduling {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get number of processes from user
        System.out.print("Enter the number of processes: ");
        int numProcesses = scanner.nextInt();

        int[] processes = new int[numProcesses];
        int[] burstTime = new int[numProcesses];
        int[] remainingTime = new int[numProcesses];
        int[] waitingTime = new int[numProcesses];
        int[] turnaroundTime = new int[numProcesses];

        // Get burst time for each process
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Enter burst time for process P" + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            processes[i] = i + 1;  // process number
            remainingTime[i] = burstTime[i];
        }

        // Get the time quantum
        System.out.print("Enter time quantum: ");
        int timeQuantum = scanner.nextInt();

        int time = 0;
        boolean done;

        // Round Robin Scheduling logic
        do {
            done = true;
            for (int i = 0; i < numProcesses; i++) {
                if (remainingTime[i] > 0) {
                    done = false; // There is still a pending process

                    if (remainingTime[i] > timeQuantum) {
                        time += timeQuantum;
                        remainingTime[i] -= timeQuantum;
                    } else {
                        time += remainingTime[i];
                        waitingTime[i] = time - burstTime[i];
                        remainingTime[i] = 0;
                    }
                }
            }
        } while (!done);

        // Calculate turnaround times
        for (int i = 0; i < numProcesses; i++) {
            turnaroundTime[i] = burstTime[i] + waitingTime[i];
        }

        // Display process info and results
        System.out.println("\nProcess\tBurst Time\tWaiting Time\tTurnaround Time");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("P" + processes[i] + "\t\t" + burstTime[i] + "\t\t" + waitingTime[i] + "\t\t" + turnaroundTime[i]);
        }

        // Calculate and display average waiting and turnaround times
        double avgWaitingTime = 0;
        double avgTurnaroundTime = 0;
        for (int i = 0; i < numProcesses; i++) {
            avgWaitingTime += waitingTime[i];
            avgTurnaroundTime += turnaroundTime[i];
        }
        avgWaitingTime /= numProcesses;
        avgTurnaroundTime /= numProcesses;

        System.out.println("\nAverage Waiting Time: " + avgWaitingTime);
        System.out.println("Average Turnaround Time: " + avgTurnaroundTime);

        scanner.close();
    }
}
