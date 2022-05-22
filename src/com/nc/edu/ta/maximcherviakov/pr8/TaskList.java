package com.nc.edu.ta.maximcherviakov.pr8;

public abstract class TaskList implements Iterable<Task> {

    public abstract void add(Task task);

    public abstract void remove(Task task);

    public abstract TaskList clone();

    public abstract int size();

    public abstract Task[] incoming(int from, int to);
}
