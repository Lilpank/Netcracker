/**
 * Класс создан на основе логики коллекции LinkedList,
 * используется для удобства быстрой вставки\удаления элемента, выполняется за константное время.
 */

public class LinkedTaskList extends AbstractTaskList {
    private Node head;
    private Node tail;
    private int count = 0;

    // Создаем класс Узел, у которого есть ссылки на first, last элемент.
    private static class Node {
        public Task data;
        public Node next = null;
        public Node previous = null;
    }

    // Создаем лист
    public LinkedTaskList() {
        head = null;
        tail = null;
    }


    @Override
    public void add(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        Node newNode = new Node();
        newNode.data = task;

        if (head == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        newNode.previous = tail;
        tail = newNode;
        count++;
    }


    @Override
    public boolean remove(Task task) throws IllegalArgumentException {
        if (task == null) {
            throw new IllegalArgumentException();
        }
        // Если лист пустой.
        if (count == 0) {
            return false;
        }
        //Проверка если пришел Task на самый первый элемент, то есть head.
        if (head.data.getTime() == task.getTime()) {
            head = head.next;
            head.previous = null;
            --count;
            return true;
        }
        // Если пришел Task на самый последний элемент, то есть tail.
        if (tail.data.getTime() == task.getTime()) {
            tail = tail.previous;
            tail.previous = null;
            tail.next = null;
            --count;
            return true;
        }
        // Находим нужный нам Node
        Node deleteNode = findNode(task);
        // Если не нашли
        if (deleteNode == null) {
            return false;
        } else { // Если нашли, избовляемся от ссылок объекта
            Node last = deleteNode.previous;
            Node first = deleteNode.next;
            last.next = first;
            first.previous = last;

            deleteNode.next = null;
            deleteNode.previous = null;
            --count;
            return true;
        }
    }

    private Node findNode(Task value) {
        Node node = head;
        while (node.next != null) {
            if (node.next.data.getTime() == value.getTime()) {
                return node.next;
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index >= count || index < 0) {
            throw new IndexOutOfBoundsException();
        }

        int i = 0;
        Node currentNode = head;
        while (currentNode != null) {
            if (i == index) {
                return currentNode.data;
            }
            currentNode = currentNode.next;
            ++i;
        }
        return null;
    }

    @Override
    public LinkedTaskList incoming(int from, int to) throws IndexOutOfBoundsException {
        if (from < 0 || to < 0) {
            throw new IndexOutOfBoundsException();
        }

        LinkedTaskList tasks = new LinkedTaskList();
        Node currentNode = head;
        while (currentNode != null) {
            if (isIntervalValid(currentNode.data, from, to)) {
                tasks.add(currentNode.data);
            }
            currentNode = currentNode.next;
        }

        return tasks;
    }

    private boolean isIntervalValid(Task element, int from, int to) {
        return (from <= element.getStartTime() && element.getStartTime() <= to) || (from <= element.getTime() && element.getTime() <= to);
    }

    @Override
    public String toString() {
        return "Node: " + "values = " + head.data + "next = " + head.next + "previsious = " + head.previous;
    }

    public static void printList(LinkedTaskList list) {
        Node currNode = list.head;

        System.out.print("LinkedList: ");

        // Traverse through the LinkedList
        while (currNode != null) {
            // Print the data at current node
            System.out.print(currNode.data.getTime() + " ");

            // Go to next node
            currNode = currNode.next;
        }
    }
}
