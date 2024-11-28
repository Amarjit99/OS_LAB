import java.util.*;

public class FIFO {

    // Function to implement FIFO Page Replacement Algorithm
    public static void fifoPageReplacement(int[] pages, int frameCount) {
        // Create a queue to store the pages in memory (FIFO)
        Queue<Integer> memory = new LinkedList<>();
        Set<Integer> pageSet = new HashSet<>();
        int pageFaults = 0;

        // Process each page reference
        for (int page : pages) {
            // Check if the page is already in memory (no page fault)
            if (!pageSet.contains(page)) {
                pageFaults++;

                // If memory is full, remove the oldest page (FIFO)
                if (memory.size() == frameCount) {
                    int oldestPage = memory.poll();  // Remove the oldest page
                    pageSet.remove(oldestPage);      // Remove it from the set as well
                }

                // Add the new page to memory
                memory.offer(page);
                pageSet.add(page);  // Add it to the set for quick lookup
            }
        }

        // Print the result
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.print("Pages in memory: ");
        for (int page : memory) {
            System.out.print(page + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input for the number of frames
        System.out.print("Enter the number of frames: ");
        int frameCount = scanner.nextInt();

        // Get user input for the number of page references
        System.out.print("Enter the number of page references: ");
        int numPages = scanner.nextInt();

        // Get user input for the page reference string
        int[] pages = new int[numPages];
        System.out.println("Enter the page reference string (space-separated values): ");
        for (int i = 0; i < numPages; i++) {
            pages[i] = scanner.nextInt();
        }

        // Run the FIFO page replacement algorithm
        fifoPageReplacement(pages, frameCount);

        scanner.close();  // Close the scanner to prevent resource leak
    }
}
