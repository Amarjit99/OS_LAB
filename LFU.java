import java.util.*;

public class LFU {

    static class Page {
        int pageNumber;
        int frequency;

        Page(int pageNumber) {
            this.pageNumber = pageNumber;
            this.frequency = 1; // Initialize frequency to 1 when page is accessed for the first time
        }
    }

    // Function to implement LFU Page Replacement Algorithm
    public static void lfuPageReplacement(int[] pages, int frameCount) {
        // LinkedHashMap to store pages and their frequencies
        Map<Integer, Page> memory = new LinkedHashMap<>();
        int pageFaults = 0;

        // Process each page reference
        for (int page : pages) {
            // Check if page is already in memory
            if (memory.containsKey(page)) {
                // Increment frequency of the page
                memory.get(page).frequency++;
            } else {
                // If memory is full, remove the page with the least frequency
                if (memory.size() == frameCount) {
                    // Find the page with the least frequency
                    int minFrequency = Integer.MAX_VALUE;
                    int pageToRemove = -1;

                    for (Map.Entry<Integer, Page> entry : memory.entrySet()) {
                        if (entry.getValue().frequency < minFrequency) {
                            minFrequency = entry.getValue().frequency;
                            pageToRemove = entry.getKey();
                        }
                    }

                    // Remove the page with the least frequency
                    memory.remove(pageToRemove);
                }

                // Add the new page to memory
                memory.put(page, new Page(page));
                pageFaults++;
            }
        }

        // Output results
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.print("Pages in memory: ");
        for (Map.Entry<Integer, Page> entry : memory.entrySet()) {
            System.out.print(entry.getKey() + " ");
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

        // Run the LFU page replacement algorithm
        lfuPageReplacement(pages, frameCount);

        scanner.close();  // Close the scanner to prevent resource leak
    }
}
