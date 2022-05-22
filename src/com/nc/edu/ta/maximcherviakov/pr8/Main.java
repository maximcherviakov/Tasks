package com.nc.edu.ta.maximcherviakov.pr8;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Task[] incoming = {
                new Task("b", 20),
                new Task("c", 30),
                new Task("e", 0, 100, 5)
        };
        LinkedTaskList task1 = new LinkedTaskList();
        LinkedTaskList task2 = new LinkedTaskList();

        task1.add(incoming[2]);
        task1.add(incoming[2]);
        task1.add(incoming[2]);
        task1.add(new Task("a", 10));
        task1.add(incoming[0]);
        task1.add(incoming[2]);
        task1.add(incoming[1]);
        task1.add(incoming[2]);
        task1.add(incoming[2]);
        task1.add(new Task("d", 40));
        task1.add(incoming[2]);

        TaskXMLSerializer.save(task1, "task1.xml");
        AbstractTaskList list = new LinkedTaskList();
        TaskXMLSerializer.load(list, "task1.xml");
        System.out.println("list");
        System.out.println(list.toString());

        task2.add(incoming[2]);
        task2.add(incoming[2]);
        task2.add(incoming[2]);
        task2.add(new Task("a", 10));
        task2.add(incoming[0]);
        task2.add(incoming[1]);
        task2.add(incoming[2]);
        task2.add(incoming[2]);
        task2.add(incoming[2]);
        task2.add(new Task("d", 40));
        task2.add(incoming[2]);

        TaskXMLSerializer.save(task2, "task2.xml");


        System.out.println("-----------------------");

        System.out.println();

        System.out.println(task1.toString());
        System.out.println(task2.toString());

        System.out.println();

        Iterator<Task> iterator = task1.iterator();

        System.out.println(iterator.hasNext());

        while (iterator.hasNext()) {
            System.out.println(iterator.next().getTitle());
        }


    }
}
