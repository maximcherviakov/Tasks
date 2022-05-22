package com.nc.edu.ta.maximcherviakov.pr8;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedTaskList extends AbstractTaskList {
    private Node head;
    private Node tail;

    /**
     * Constructor, which count the number of lists of the tasks
     */
    public LinkedTaskList() {
        numberOfLists++;
    }

    /**
     * Add the task to the list
     *
     * @param task added task
     */
    @Override
    public void add(Task task) throws RuntimeException {
        if (task == null) {
            throw new NullPointerException("Entered incorrect added task");
        }

        Node newItem = new Node(task, null);
        if (head == null) {
            head = newItem;
        } else if (tail == null) {
            tail = newItem;
            head.setNext(tail);
        } else {
            tail.setNext(newItem);
            tail = newItem;
        }
        setIndex(size() + 1);
    }

    /**
     * Remove all the tasks, which are equal to parameter
     *
     * @param task removed task
     */
    @Override
    public void remove(Task task) throws RuntimeException {
        if (task == null) {
            throw new NullPointerException();
        }

        Node previousElement = null;
        Node currentElement = head;

        if (currentElement == null) {
            return;
        }

        while (currentElement != null) {
            if (currentElement.getValue().equals(task)) {
                if (currentElement == head) {
                    head = currentElement.getNext();
                    currentElement = head;
                } else {
                    if (currentElement == tail) {
                        tail = previousElement;
                    }
                    previousElement.setNext(currentElement.getNext());
                    currentElement = previousElement.getNext();
                }
                setIndex(size() - 1);
            } else {
                previousElement = currentElement;
                currentElement = currentElement.getNext();
            }
        }
        if (head == tail) {
            tail = null;
        }
    }

    /**
     * @return number of elements of list
     */
    @Override
    public int size() {
        return index;
    }

    /**
     * @param from the beginning of period of the time
     * @param to   the end of period of the time
     * @return the list of the tasks, which time is located in period
     */
    @Override
    public Task[] incoming(int from, int to) {
        try {
            if (from >= to) {
                throw new IncorrectInputException("Entered incorrect period of the time");
            }
        } catch (IncorrectInputException e) {
            System.err.println(e.getMessage());
            return null;
        }

        int size = 0;
        int index = 0;
        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from) != -1 && getTask(i).nextTimeAfter(from) > from && getTask(i).nextTimeAfter(from) <= to) {
                size++;
            }
        }
        Task[] incoming = new Task[size];
        for (int i = 0; i < size(); i++) {
            if (getTask(i).nextTimeAfter(from) != -1 && getTask(i).nextTimeAfter(from) > from && getTask(i).nextTimeAfter(from) <= to) {
                incoming[index] = getTask(i);
                index++;
            }
        }
        return incoming;
    }

    /**
     * @return cloned linked task list
     */
    @Override
    public LinkedTaskList clone() {
        LinkedTaskList clonedLinkedTaskList = new LinkedTaskList();

        for (int i = 0; i < this.size(); i++) {
            clonedLinkedTaskList.add(this.getTask(i));
        }

        return clonedLinkedTaskList;
    }

    /**
     * @return list of the tasks
     */
    @Override
    public String toString() {
        if (size() == 0) {
            return "Linked task list is empty";
        } else {
            String toString = "Linked task list [";

            for (int i = 0; i < size(); i++) {
                toString = toString + getTask(i).getTitle();
                if (i == size() - 1) {
                    toString = toString + "]";
                } else {
                    toString = toString + ", ";
                }
            }

            return toString;
        }
    }

    /**
     * Checks if this list equal to other list
     *
     * @param list other list
     * @return true, when equal or false, when not equal
     */
    @Override
    public boolean equals(Object list) {
        if (!(list instanceof LinkedTaskList)) {
            return false;
        } else if (list == null) {
            return false;
        } else if (this == list) {
            return true;
        } else if (this.size() != ((LinkedTaskList) list).size()) {
            return false;
        } else if (this.hashCode() != ((LinkedTaskList) list).hashCode()) {
            return false;
        } else {
            LinkedTaskList firstList = this.clone();
            LinkedTaskList secondList = ((LinkedTaskList) list).clone();

            while (firstList.size() > 0 && secondList.size() > 0) {
                secondList.remove(firstList.getTask(0));
                firstList.remove(firstList.getTask(0));
                if (firstList.size() != secondList.size()) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * @return Integer number for every task
     */
    @Override
    public int hashCode() {
        int sum = 0;
        Iterator<Task> iterator = this.iterator();
        while (iterator.hasNext()) {
            sum += iterator.next().hashCode();
        }
        return sum;
    }

    /**
     * @param index index of the task, which you want to get
     * @return task by the index
     */
    public Task getTask(int index) throws RuntimeException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Entered incorrect index");
        }

        Node node = head;
        int innerIndex = 0;
        while (node != null) {
            if (innerIndex == index) {
                return node.getValue();
            }
            node = node.getNext();
            innerIndex++;
        }
        if (innerIndex < index) {
            System.out.println("Your index is out of range");
        }
        return null;
    }

    /**
     * @param index number of elements of list
     */
    private void setIndex(int index) {
        this.index = index;
    }

    /**
     * Class, which describes the node of the linked list
     */
    public static class Node {
        private Task value;
        private Node next;

        /**
         * Constructor, which sets the value of the node and link to the next node
         *
         * @param value value
         * @param next  next node
         */
        public Node(Task value, Node next) {
            this.value = value;
            this.next = next;
        }

        /**
         * @return value of the node
         */
        public Task getValue() {
            return value;
        }

        /**
         * @return link to the next node
         */
        public Node getNext() {
            return next;
        }

        /**
         * @param value value of the node
         */
        public void setValue(Task value) {
            this.value = value;
        }

        /**
         * @param next link to the next node
         */
        public void setNext(Node next) {
            this.next = next;
        }
    }

    /**
     * @return iterator object
     */
    @Override
    public Iterator<Task> iterator() {
        return new LinkedTaskList.LinkedTaskListIterator();
    }

    /**
     * Class with realisation of the interface Iterator
     */
    private class LinkedTaskListIterator implements Iterator<Task> {
        private boolean isLegal = false;
        private Node previousNode = null;
        private Node currentNode = null;
        private Node nextNode = head;

        /**
         * @return true if list has the next element or false if has not
         */
        public boolean hasNext() {
            if (nextNode == null) {
                return false;
            } else {
                return true;
            }
        }

        /**
         * @return the next element of the list
         */
        public Task next() throws NoSuchElementException {
            if (this.hasNext()) {
                isLegal = true;
                previousNode = currentNode;
                currentNode = nextNode;
                nextNode = nextNode.getNext();
                return currentNode.getValue();
            } else {
                throw new NoSuchElementException();
            }
        }

        /**
         * remove current element in the list
         */
        public void remove() throws IllegalStateException {
            if (!isLegal) {
                throw new IllegalStateException();
            }

            isLegal = false;

            if (currentNode == head) {
                currentNode = currentNode.getNext();
                head = currentNode;
                if (head == tail) {
                    tail = null;
                }
            } else if (currentNode == tail) {
                currentNode = null;
                previousNode.setNext(null);
                tail = previousNode;
            } else {
                previousNode.setNext(currentNode.getNext());
                currentNode = previousNode.getNext();
            }

            setIndex(size() - 1);
        }
    }
}
