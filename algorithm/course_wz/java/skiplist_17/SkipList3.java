package skiplist_17;


import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * <p>
 * The design of the skip list structure is as follows:
 * H->a1--------->a2->null
 * H->a1----->a3->a2->null
 * H->a1->a4->a3->a2->null
 * <p>
 * Some properties of this skiplist are:
 * 1. head stores min possible value
 * 2. there is always at least one number present to the right of head if list is non-empty
 * 3. a tower (vertical column) only consists of the same number
 * 4. a level (horizontal row) will have at least 2 nodes, one head and one number on its right
 * 5. rows and columns are linked by a single linked list
 */
// 借助栈实现，对于有多层的情况，需要把Node节点先放到栈中，
public class SkipList3 {
    class Node {
        int val;
        Node next;
        Node down;

        public Node(int val, Node next, Node down) {
            this.val = val;
            this.next = next;
            this.down = down;
        }
    }

    Node head;

    public SkipList3() {
        head = null;
    }

    public boolean search(int target) {
        if (head == null) {
            return false;
        }
        Node ptr = head;

        do {
            while (ptr.next != null && target >= ptr.next.val) {
                ptr = ptr.next;
            }
            if (ptr.val == target) {
                return true;
            }
            ptr = ptr.down;
        } while (ptr != null);
        return false;
    }

    public void add(int num) {
        if (head == null) {
            // first element of the skiplist
            Node node = new Node(num, null, null);
            head = new Node(Integer.MIN_VALUE, node, null);
            return;
        }

        Node ptr = head;

        Stack<Node> backtrack = new Stack<>(); // to increase tower height
        while (true) {
            while (ptr.next != null && num >= ptr.next.val) {
                ptr = ptr.next;
            }
            if (ptr.down != null) {
                backtrack.add(ptr);
                ptr = ptr.down;
            } else {
                // we have reached base level. Add element here
                Node newBaseNode = new Node(num, ptr.next, null);
                ptr.next = newBaseNode;

                Random rand = new Random();
                Node downNode = newBaseNode;
                while (rand.nextInt(2) == 0) {
                    if (!backtrack.isEmpty()) {
                        // increase tower length and link it with other towers. Height of skiplist doesn't increase
                        Node node = backtrack.pop();
                        Node newNode = new Node(num, node.next, downNode);
                        node.next = newNode;
                        downNode = newNode;
                    } else {
                        // height of skiplist increases
                        Node newNode = new Node(num, null, downNode);
                        Node newHead = new Node(Integer.MIN_VALUE, newNode, head);
                        head = newHead;
                        downNode = newNode;
                    }
                }
                break;
            }
        }
    }

    public boolean erase(int num) {
        if (head == null) {
            return false;
        }

        Node ptr = head;
        boolean deleted = false;
        do {
            while (ptr.next != null && num > ptr.next.val) {
                ptr = ptr.next;
            }
            if (ptr.next != null && ptr.next.val == num) {
                ptr.next = ptr.next.next;
                deleted = true;
            }
            ptr = ptr.down;
        } while (ptr != null);

        // when no element exists in skiplist, make head null (our start position)
        while (head != null && head.next == null) {
            head = head.down;
        }
        return deleted;
    }

    public void printSkipList() {
        if (head == null) {
            return;
        }
        Node ptr = head;
        Node ptr2;
        while (ptr != null) {
            ptr2 = ptr;
            while (ptr2 != null) {
                System.out.print(ptr2.val);
                if (ptr2.down != null) {
                    System.out.print("(" + ptr2.down.val + ") -> ");
                } else {
                    System.out.print("(.) -> ");
                }
                ptr2 = ptr2.next;
            }
            System.out.println();
            ptr = ptr.down;
        }
    }

    public static void main(String[] args) {
        SkipList3 skipList = new SkipList3();

        int i = 1, j = 6;
        while (i <= 5) {
            skipList.add(i++);
            skipList.add(j++);
        }
        skipList.printSkipList();


        Scanner scanner = new Scanner(System.in);
        int option;
        int num;
        do {
            System.out.print("Enter number (0[Exit], 1[Add], 2[Search], 3[Print], 4[Delete]): ");
            option = scanner.nextInt();
            switch (option) {
                case 0:
                    System.out.println("Exit");
                    break;
                case 1:
                    System.out.println("Add");
                    num = scanner.nextInt();
                    skipList.add(num);
                    break;
                case 2:
                    System.out.println("Search");
                    num = scanner.nextInt();
                    if (skipList.search(num)) {
                        System.out.println("Present");
                    } else {
                        System.out.println("Absent");
                    }
                    break;
                case 3:
                    System.out.println("Print");
                    skipList.printSkipList();
                    break;
                case 4:
                    System.out.println("Delete");
                    num = scanner.nextInt();
                    if (skipList.erase(num)) {
                        System.out.println("Deleted");
                    } else {
                        System.out.println("Not Found");
                    }
                    break;
                default:
                    System.out.println("Wrong option. ");
            }
        } while (option != 0);
    }
}