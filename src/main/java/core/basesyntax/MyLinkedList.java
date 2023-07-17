package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    public static class Node<T> {
        private T element;
        private Node<T> prev;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private int size;

    @Override
    public void add(T value) {
        addLast(value);
    }

    @Override
    public void add(T value, int index) {
        if (index == size) {
            add(value);
        } else if (index == 0) {
            addFirst(value);
        } else {
            Node<T> newNode = new Node<>(value);
            Node<T> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
            size++;
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T element: list) {
            add(element);
        }
    }

    @Override
    public T get(int index) {
        return getNode(index).element;
    }

    @Override
    public T set(T value, int index) {
        Node<T> node = getNode(index);
        T refreshValue = node.element;
        node.element = value;
        return refreshValue;
    }

    @Override
    public T remove(int index) {
        Node<T> nodeToRemove = getNode(index);
        return unLinkNode(nodeToRemove);
    }

    @Override
    public boolean remove(T object) {
        Node<T> current = head;
        while (current != null) {
            if (object == null ? current.element == null : object.equals(current.element)) {
                unLinkNode(current);
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void addLast(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            head = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
        }
        tail = newNode;
        size++;
    }

    private void addFirst(T value) {
        Node<T> newNode = new Node<>(value);
        if (isEmpty()) {
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
        }
        head = newNode;
        size++;
    }

    private Node<T> getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index must be greater"
                    + "  than zero and less than "
                    + size);
        }
        Node<T> current;
        if (index < size / 2) {
            current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = tail;
            for (int i = size - 1; i > index; i--) {
                current = current.prev;
            }
        }
        return current;
    }

    private T unLinkNode(Node<T> node) {
        if (node == head) {
            head = head.next;
        } else {
            node.prev.next = node.next;
        }
        if (node == tail) {
            tail = tail.prev;
        } else {
            node.next.prev = node.prev;
        }
        size--;
        return node.element;
    }
}
