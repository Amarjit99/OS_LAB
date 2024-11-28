import java.util.*;

public class LRU {

    // Function to implement LRU Page Replacement Algorithm
    public static void lruPageReplacement(int[] pages, int frameCount) {
        // LinkedHashMap to maintain the order of pages for LRU (least recently used)
        LinkedHashMap<Integer, Integer> memory = new LinkedHashMap<>(frameCount, 0.75f, true);
        int pageFaults = 0;

        // Process each page reference
        for (int page : pages) {
            // Check if the page is already in memory (no page fault)
            if (!memory.containsKey(page)) {
                pageFaults++;
                // If memory is full, remove the least recently used page (oldest)
                if (memory.size() == frameCount) {
                    Iterator<Map.Entry<Integer, Integer>> it = memory.entrySet().iterator();
                    it.next(); // Remove the first element (least recently used)
                    it.remove();
                }
            }
            // Add the new page to memory
            memory.put(page, 1);
        }

        // Print the result
        System.out.println("Total Page Faults: " + pageFaults);
        System.out.print("Pages in memory: ");
        for (Map.Entry<Integer, Integer> entry : memory.entrySet()) {
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

        // Run the LRU page replacement algorithm
        lruPageReplacement(pages, frameCount);

        scanner.close();  // Close the scanner to prevent resource leak
    }
}
