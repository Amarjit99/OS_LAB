import java.util.Scanner;

public class BankersAlgorithm {

    static int[][] max, allocation, need;
    static int[] available;
    static int numProcesses, numResources;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input number of processes and resources
        System.out.print("Enter the number of processes: ");
        numProcesses = scanner.nextInt();

        System.out.print("Enter the number of resources: ");
        numResources = scanner.nextInt();

        // Initialize matrices and vectors
        max = new int[numProcesses][numResources];
        allocation = new int[numProcesses][numResources];
        need = new int[numProcesses][numResources];
        available = new int[numResources];

        // Input available resources
        System.out.print("Enter the available resources (space-separated): ");
        for (int i = 0; i < numResources; i++) {
            available[i] = scanner.nextInt();
        }

        // Input max matrix
        System.out.println("Enter the Max Matrix:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Process " + i + ": ");
            for (int j = 0; j < numResources; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        // Input allocation matrix
        System.out.println("Enter the Allocation Matrix:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("Process " + i + ": ");
            for (int j = 0; j < numResources; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        // Calculate the Need matrix
        calculateNeed();

        // Input request for a specific process
        System.out.print("Enter the process ID making the request (0 to " + (numProcesses - 1) + "): ");
        int processID = scanner.nextInt();

        int[] request = new int[numResources];
        System.out.print("Enter the resource request for process " + processID + " (space-separated): ");
        for (int i = 0; i < numResources; i++) {
            request[i] = scanner.nextInt();
        }

        // Check if the request can be granted
        if (requestResources(processID, request)) {
            System.out.println("Request can be granted. System is in a safe state.");
        } else {
            System.out.println("Request cannot be granted as it leads to an unsafe state.");
        }

        scanner.close();
    }

    // Calculate the Need matrix as (Max - Allocation)
    private static void calculateNeed() {
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = max[i][j] - allocation[i][j];
            }
        }
    }

    // Request resources function
    private static boolean requestResources(int processID, int[] request) {
        // Check if request is within the Need matrix for the process
        for (int i = 0; i < numResources; i++) {
            if (request[i] > need[processID][i]) {
                System.out.println("Error: Process has exceeded its maximum claim.");
                return false;
            }
        }

        // Check if request is within the Available resources
        for (int i = 0; i < numResources; i++) {
            if (request[i] > available[i]) {
                System.out.println("Error: Resources are not available.");
                return false;
            }
        }

        // Pretend to allocate the resources
        for (int i = 0; i < numResources; i++) {
            available[i] -= request[i];
            allocation[processID][i] += request[i];
            need[processID][i] -= request[i];
        }

        // Check if the new state is safe
        boolean safe = isSafeState();

        // If not safe, rollback the allocation
        if (!safe) {
            for (int i = 0; i < numResources; i++) {
                available[i] += request[i];
                allocation[processID][i] -= request[i];
                need[processID][i] += request[i];
            }
        }

        return safe;
    }

    // Safety algorithm to check if the system is in a safe state
    private static boolean isSafeState() {
        int[] work = available.clone();
        boolean[] finish = new boolean[numProcesses];
        int[] safeSequence = new int[numProcesses];
        int count = 0;

        while (count < numProcesses) {
            boolean foundProcess = false;

            for (int i = 0; i < numProcesses; i++) {
                if (!finish[i]) {
                    int j;
                    for (j = 0; j < numResources; j++) {
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }

                    if (j == numResources) {
                        for (int k = 0; k < numResources; k++) {
                            work[k] += allocation[i][k];
                        }
                        safeSequence[count++] = i;
                        finish[i] = true;
                        foundProcess = true;
                    }
                }
            }

            if (!foundProcess) {
                System.out.println("System is in an unsafe state.");
                return false;
            }
        }

        // If safe, print the safe sequence
        System.out.print("System is in a safe state. Safe sequence: ");
        for (int i = 0; i < numProcesses; i++) {
            System.out.print("P" + safeSequence[i] + " ");
        }
        System.out.println();
        return true;
    }
}
