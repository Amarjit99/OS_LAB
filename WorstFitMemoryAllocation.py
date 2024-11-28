class Block:
    def __init__(self, size):
        self.size = size
        self.is_occupied = False
        self.process_id = None

class WorstFitMemoryAllocation:
    def __init__(self, block_sizes):
        self.blocks = [Block(size) for size in block_sizes]

    def allocate_memory(self, process_id, process_size):
        # Find the largest available block that can fit the process
        worst_fit_index = -1
        max_block_size = -1

        for i, block in enumerate(self.blocks):
            if not block.is_occupied and block.size >= process_size and block.size > max_block_size:
                worst_fit_index = i
                max_block_size = block.size

        if worst_fit_index != -1:
            block = self.blocks[worst_fit_index]
            block.is_occupied = True
            block.process_id = process_id
            print(f"Allocated process {process_id} of size {process_size} to block of size {block.size}")
        else:
            print(f"No suitable block found for process {process_id} of size {process_size}")

    def free_memory(self, process_id):
        # Free the block occupied by the process with the given process_id
        for block in self.blocks:
            if block.is_occupied and block.process_id == process_id:
                block.is_occupied = False
                block.process_id = None
                print(f"Freed memory for process {process_id}")
                return
        print(f"Process {process_id} not found in memory.")

    def display_memory_status(self):
        print("\nMemory Status:")
        for i, block in enumerate(self.blocks):
            status = f"Occupied by process {block.process_id}" if block.is_occupied else "Free"
            print(f"Block {i} (Size: {block.size}): {status}")

def main():
    # Get memory block sizes from the user
    block_sizes = []
    num_blocks = int(input("Enter the number of memory blocks: "))
    
    print("Enter the sizes of each memory block:")
    for i in range(num_blocks):
        block_size = int(input(f"Size of block {i + 1}: "))
        block_sizes.append(block_size)

    memory = WorstFitMemoryAllocation(block_sizes)

    # User menu for memory operations
    while True:
        print("\nChoose an operation:")
        print("1. Allocate Memory")
        print("2. Free Memory")
        print("3. Display Memory Status")
        print("4. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:
            process_id = int(input("Enter the process ID to allocate: "))
            process_size = int(input("Enter the size of the process: "))
            memory.allocate_memory(process_id, process_size)

        elif choice == 2:
            process_id = int(input("Enter the process ID to free: "))
            memory.free_memory(process_id)

        elif choice == 3:
            memory.display_memory_status()

        elif choice == 4:
            print("Exiting...")
            break

        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
