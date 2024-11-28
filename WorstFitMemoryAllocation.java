import java.util.ArrayList;
import java.util.Scanner;

class Block {
    int size;
    boolean isOccupied;
    int processId;

    public Block(int size) {
        this.size = size;
        this.isOccupied = false;
        this.processId = -1; // -1 indicates no process is allocated
    }
}

class WorstFitMemoryAllocation {
    ArrayList<Block> blocks;

    public WorstFitMemoryAllocation(ArrayList<Integer> blockSizes) {
        blocks = new ArrayList<>();
        for (int size : blockSizes) {
            blocks.add(new Block(size));
        }
    }

    public void allocateMemory(int processId, int processSize) {
        int worstFitIndex = -1;
        int maxBlockSize = -1;

        // Find the largest free block that can fit the process
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (!block.isOccupied && block.size >= processSize && block.size > maxBlockSize) {
                worstFitIndex = i;
                maxBlockSize = block.size;
            }
        }

        if (worstFitIndex != -1) {
            Block block = blocks.get(worstFitIndex);
            block.isOccupied = true;
            block.processId = processId;
            System.out.println("Allocated process " + processId + " of size " + processSize + " to block of size " + block.size);
        } else {
            System.out.println("No suitable block found for process " + processId + " of size " + processSize);
        }
    }

    public void freeMemory(int processId) {
        for (Block block : blocks) {
            if (block.isOccupied && block.processId == processId) {
                block.isOccupied = false;
                block.processId = -1;
                System.out.println("Freed memory for process " + processId);
                return;
            }
        }
        System.out.println("Process " + processId + " not found in memory.");
    }

    public void displayMemoryStatus() {
        System.out.println("\nMemory Status:");
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            String status = block.isOccupied ? "Occupied by process " + block.processId : "Free";
            System.out.println("Block " + i + " (Size: " + block.size + "): " + status);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of memory blocks: ");
        int numBlocks = scanner.nextInt();
        ArrayList<Integer> blockSizes = new ArrayList<>();

        System.out.println("Enter the sizes of each memory block:");
        for (int i = 0; i < numBlocks; i++) {
            System.out.print("Size of block " + (i + 1) + ": ");
            blockSizes.add(scanner.nextInt());
        }

        WorstFitMemoryAllocation memory = new WorstFitMemoryAllocation(blockSizes);

        while (true) {
            System.out.println("\nChoose an operation:");
            System.out.println("1. Allocate Memory");
            System.out.println("2. Free Memory");
            System.out.println("3. Display Memory Status");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the process ID to allocate: ");
                    int processId = scanner.nextInt();
                    System.out.print("Enter the size of the process: ");
                    int processSize = scanner.nextInt();
                    memory.allocateMemory(processId, processSize);
                    break;
                case 2:
                    System.out.print("Enter the process ID to free: ");
                    int pid = scanner.nextInt();
                    memory.freeMemory(pid);
                    break;
                case 3:
                    memory.displayMemoryStatus();
                    break;
                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
