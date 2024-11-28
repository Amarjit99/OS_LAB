class Frame:
    def __init__(self, frame_size):
        self.frame_size = frame_size
        self.is_occupied = False
        self.page_id = None

class FirstFitMemoryAllocation:
    def __init__(self, frame_sizes):
        # Initialize frames with user-defined sizes
        self.frames = [Frame(size) for size in frame_sizes]

    def allocate_memory(self, page_id, page_size):
        # Check if page is already loaded
        for frame in self.frames:
            if frame.is_occupied and frame.page_id == page_id:
                print(f"Page {page_id} is already allocated.")
                return False

        # Find the first suitable frame for the page
        for frame in self.frames:
            if not frame.is_occupied and frame.frame_size >= page_size:
                frame.is_occupied = True
                frame.page_id = page_id
                print(f"Allocated page {page_id} of size {page_size} into frame of size {frame.frame_size}")
                return True

        print(f"No suitable frame found for page {page_id} with size {page_size}")
        return False

    def free_memory(self, page_id):
        # Free the frame occupied by the page with the given page_id
        for frame in self.frames:
            if frame.is_occupied and frame.page_id == page_id:
                frame.is_occupied = False
                frame.page_id = None
                print(f"Freed memory for page {page_id}")
                return
        print(f"Page {page_id} is not found in memory.")

    def display_memory_status(self):
        print("\nMemory Status:")
        for i, frame in enumerate(self.frames):
            status = f"Occupied by page {frame.page_id}" if frame.is_occupied else "Free"
            print(f"Frame {i} (Size: {frame.frame_size}): {status}")

def main():
    # Get memory block sizes from the user
    frame_sizes = []
    num_blocks = int(input("Enter the number of memory blocks: "))
    
    print("Enter the sizes of each memory block:")
    for i in range(num_blocks):
        block_size = int(input(f"Size of block {i + 1}: "))
        frame_sizes.append(block_size)

    memory = FirstFitMemoryAllocation(frame_sizes)

    # User menu for memory operations
    while True:
        print("\nChoose an operation:")
        print("1. Allocate Memory")
        print("2. Free Memory")
        print("3. Display Memory Status")
        print("4. Exit")
        choice = int(input("Enter your choice: "))

        if choice == 1:
            page_id = int(input("Enter the page ID to allocate: "))
            page_size = int(input("Enter the size of the page: "))
            memory.allocate_memory(page_id, page_size)

        elif choice == 2:
            page_id = int(input("Enter the page ID to free: "))
            memory.free_memory(page_id)

        elif choice == 3:
            memory.display_memory_status()

        elif choice == 4:
            print("Exiting...")
            break

        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()
