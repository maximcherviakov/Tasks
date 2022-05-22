package com.nc.edu.ta.maximcherviakov.pr8;

public abstract class AbstractTaskList extends TaskList implements Cloneable {
    protected final String BEGINNING_OF_TASK = "[EDUCTR][TA]";
    protected final int DEFAULT_LENGTH = 10;
    protected final int ADDITIONAL_LENGTH = 10;
    protected static int numberOfLists = 0;
    protected int index = 0;

    public abstract void add(Task task);

    public abstract void remove(Task task);

    public abstract Task getTask(int index);

    public abstract int size();

    public abstract Task[] incoming(int from, int to);

    @Override
    public abstract AbstractTaskList clone();

    @Override
    public abstract String toString();

    @Override
    public abstract boolean equals(Object list);

    @Override
    public abstract int hashCode();

    /**
     * Copy elements of the list to list of other length
     *
     * @param original  incoming list
     * @param newLength new length of new list
     * @return new list with other length
     */
    public Task[] copyOf(Task[] original, int newLength) {
        if (original.length <= newLength) {
            Task[] copy = new Task[newLength];
            for (int i = 0; i < original.length; i++) {
                copy[i] = original[i];
            }
            return copy;
        } else {
            System.out.println("Your new length is less than current length");
            return original;
        }
    }
}
