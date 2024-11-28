import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Frame {
    int frameSize;
    boolean isOccupied;
    int pageId;

    Frame(int frameSize) {
        this.frameSize = frameSize;
        this.isOccupied = false;
        this.pageId = -1;
    }
}

public class FirstFitMemoryAllocation {
    private final List<Frame> frames;

    public FirstFitMemoryAllocation(List<Integer> frameSizes) {
        frames = new ArrayList<>();
        // Initialize frames with user-defined sizes
        for (int frameSize : frameSizes) {
            frames.add(new Frame(frameSize));
        }
    }

    public boolean allocateMemory(int pageId, int pageSize) {
        // Check if the page is already allocated
        for (Frame frame : frames) {
            if (frame.isOccupied && frame.pageId == pageId) {
                System.out.println("Page " + pageId + " is already allocated.");
                return false;
            }
        }

        // Find the first suitable frame for the page
        for (Frame frame : frames) {
            if (!frame.isOccupied && frame.frameSize >= pageSize) {
                frame.isOccupied = true;
                frame.pageId = pageId;
                System.out.println("Allocated page " + pageId + " of size " + pageSize + " into frame of size " + frame.frameSize);
                return true;
            }
        }

        System.out.println("No suitable frame found for page " + pageId + " with size " + pageSize);
        return false;
    }

    public void freeMemory(int pageId) {
        for (Frame frame : frames) {
            if (frame.isOccupied && frame.pageId == pageId) {
                frame.isOccupied = false;
                frame.pageId = -1;
                System.out.println("Freed memory for page " + pageId);
                return;
            }
        }
        System.out.println("Page " + pageId + " is not found in memory.");
    }

    public void displayMemoryStatus() {
        System.out.println("\nMemory Status:");
        for (int i = 0; i < frames.size(); i++) {
            Frame frame = frames.get(i);
            String status = frame.isOccupied ? "Occupied by page " + frame.pageId : "Free";
            System.out.println("Frame " + i + " (Size: " + frame.frameSize + "): " + status);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get memory block sizes from the user
        List<Integer> frameSizes = new ArrayList<>();
        System.out.print("Enter the number of memory blocks: ");
        int numBlocks = scanner.nextInt();
        
        System.out.println("Enter the sizes of each memory block:");
        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Size of block " + (i + 1) + ": ");
            frameSizes.add(scanner.nextInt());
        }

        FirstFitMemoryAllocation memory = new FirstFitMemoryAllocation(frameSizes);

        // User menu for memory operations
        boolean exit = false;
        while (!exit) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Allocate Memory");
            System.out.println("2. Free Memory");
            System.out.println("3. Display Memory Status");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the page ID to allocate: ");
                    int pageId = scanner.nextInt();
                    System.out.print("Enter the size of the page: ");
                    int pageSize = scanner.nextInt();
                    memory.allocateMemory(pageId, pageSize);
                    break;

                case 2:
                    System.out.print("Enter the page ID to free: ");
                    int freePageId = scanner.nextInt();
                    memory.freeMemory(freePageId);
                    break;

                case 3:
                    memory.displayMemoryStatus();
                    break;

                case 4:
                    exit = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }
}
