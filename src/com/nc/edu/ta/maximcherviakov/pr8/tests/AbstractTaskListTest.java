package com.nc.edu.ta.maximcherviakov.pr8.tests;

import com.nc.edu.ta.maximcherviakov.pr8.*;

import java.util.*;

import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public abstract class AbstractTaskListTest {

    protected TaskList tasks;
    
    private Class<? extends TaskList> tasksClass;
    
    public AbstractTaskListTest(Class<? extends TaskList> tasksClass) {
        this.tasksClass = tasksClass;
    }
    
    @Before
    public void setUp() throws Exception {
        tasks = createList();
    }
    
    public TaskList createList() throws Exception {
        return tasksClass.newInstance();
    }
    
    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            { ArrayTaskList.class  },
            { LinkedTaskList.class }
        });
    }
    
    protected String getTitle() {
        return tasksClass.getSimpleName();
    }
        
    // utility functions --------------------------------------------------

    protected void assertContains(Task[] expected, Task[] actualA) {
        assertEquals(getTitle() + ": Unexpected size", 
            expected.length, actualA.length);
            
        List<Task> actual = new ArrayList<Task>();
        for (Task task : actualA)
            actual.add(task);
            
        for (Task task : expected)
            if (actual.contains(task))
                actual.remove(task);
            else
                fail(getTitle() + ": Task "+ task +" expected to be in list");
                
        if (! actual.isEmpty())
            fail(getTitle() + ": Tasks "+ actual +" are unexpected in list");
    }

    protected void assertContains(Task[] expected) {
        Task[] actual = new Task[tasks.size()];
        int i = 0;
        for (Task t : tasks)
            actual[i++] = t;
        assertContains(expected, actual);
    }

    protected static Task task(String title) {
        return new Task(title, 0);
    }
    
    protected static Task task(String title, int time, boolean active) {
        Task t = new Task(title, time);
        t.setActive(active);
        return t;
    }

    protected static Task task(String title, int from, int to, int interval, boolean active) {
        Task t = new Task(title, from, to, interval);
        t.setActive(active);
        return t;
    }
    
    protected void addAll(Task[] ts) {
        for (Task t : ts)
            tasks.add(t);
    }        
}
