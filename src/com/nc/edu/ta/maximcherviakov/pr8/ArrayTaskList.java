package com.nc.edu.ta.maximcherviakov.pr8;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayTaskList extends AbstractTaskList {
    private Task[] tasks = new Task[DEFAULT_LENGTH];

    /**
     * Constructor, which count the number of lists of the tasks
     */
    public ArrayTaskList() {
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
        if (size() >= tasks.length) {
            tasks = copyOf(tasks, tasks.length + ADDITIONAL_LENGTH);
        }
        tasks[size()] = task;
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

        int index = 0;
        Task[] newTasks = new Task[DEFAULT_LENGTH];
        for (int i = 0; i < size(); i++) {
            if (!tasks[i].equals(task)) {
                if (index >= newTasks.length) {
                    newTasks = copyOf(newTasks, newTasks.length + ADDITIONAL_LENGTH);
                }
                newTasks[index] = tasks[i];
                index++;
            }
        }
        tasks = newTasks;
        setIndex(index);
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
     * @return array task list
     */
    @Override
    public ArrayTaskList clone() {
        ArrayTaskList clonedArrayTaskList = new ArrayTaskList();

        for (int i = 0; i < this.size(); i++) {
            clonedArrayTaskList.add(this.getTask(i));
        }

        return clonedArrayTaskList;
    }

    /**
     * @return list of the tasks
     */
    @Override
    public String toString() {
        if (size() == 0) {
            return "Array task list is empty";
        } else {
            String toString = "Array task list [";

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
        if (!(list instanceof ArrayTaskList)) {
            return false;
        } else if (list == null) {
            return false;
        } else if (this == list) {
            return true;
        } else if (this.size() != ((ArrayTaskList) list).size()) {
            return false;
        } else if (this.hashCode() != ((ArrayTaskList) list).hashCode()) {
            return false;
        } else {
            ArrayTaskList firstList = this.clone();
            ArrayTaskList secondList = ((ArrayTaskList) list).clone();

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
        return tasks[index];
    }

    /**
     * @param index number of elements of list
     */
    private void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return iterator object
     */
    @Override
    public Iterator<Task> iterator() {
        return new ArrayTaskListIterator();
    }

    /**
     * Class with realisation of the interface Iterator
     */
    private class ArrayTaskListIterator implements Iterator<Task> {
        private boolean isLegal = false;
        private int position = 0;

        /**
         * @return true if list has the next element or false if has not
         */
        public boolean hasNext() {
            if (position < size()) {
                return true;
            } else {
                return false;
            }
        }

        /**
         * @return the next element of the list
         */
        public Task next() throws NoSuchElementException {
            if (this.hasNext()) {
                isLegal = true;
                return tasks[position++];
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

            Task[] newTasks = new Task[size() - 1];
            int index = 0;

            for (int i = 0; i < size(); i++) {
                if (i != position - 1) {
                    newTasks[index] = tasks[i];
                    index++;
                }
            }

            tasks = newTasks;
            setIndex(index);
        }
    }
}
