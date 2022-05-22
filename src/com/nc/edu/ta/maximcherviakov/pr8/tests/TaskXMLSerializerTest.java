package com.nc.edu.ta.maximcherviakov.pr8.tests;

import com.nc.edu.ta.maximcherviakov.pr8.*;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class TaskXMLSerializerTest {
    private Task task = new Task("Some task", 10, 100, 10);

    @Test(expected = NullPointerException.class)
    public void saveNullObjectTest() {
        ArrayTaskList list = null;
        TaskXMLSerializer.save(list, "list.xml");
    }

    @Test
    public void saveIncorrectNameObject1() {
        String file = "list.txt";
        AbstractTaskList list = new ArrayTaskList();
        list.add(task);
        TaskXMLSerializer.save(list, file);
        assertTrue(new File("list.xml").exists());
    }

    @Test
    public void saveIncorrectNameObject2() {
        String file = "list";
        AbstractTaskList list = new ArrayTaskList();
        list.add(task);
        TaskXMLSerializer.save(list, file);
        assertTrue(new File(file + ".xml").exists());
    }

    @Test(expected = RuntimeException.class)
    public void loadNotExistingFile() {
        AbstractTaskList list = new ArrayTaskList();
        TaskXMLSerializer.load(list, "NotExistingList.xml");
    }

    @Test(expected = NullPointerException.class)
    public void loadFileToNullList() {
        AbstractTaskList list = null;
        TaskXMLSerializer.load(list, "SomeList.xml");
    }

    @Test
    public void loadIncorrectNameObject1() {
        String file = "list.txt";
        AbstractTaskList list1 = new ArrayTaskList();
        AbstractTaskList list2 = new ArrayTaskList();
        list1.add(task);
        TaskXMLSerializer.save(list1, file);
        TaskXMLSerializer.load(list2, file);
        assertTrue(list1.equals(list2));
    }

    @Test
    public void loadIncorrectNameObject2() {
        String file = "list";
        AbstractTaskList list1 = new ArrayTaskList();
        AbstractTaskList list2 = new ArrayTaskList();
        list1.add(task);
        TaskXMLSerializer.save(list1, file);
        TaskXMLSerializer.load(list2, file);
        assertTrue(list1.equals(list2));
    }
}
