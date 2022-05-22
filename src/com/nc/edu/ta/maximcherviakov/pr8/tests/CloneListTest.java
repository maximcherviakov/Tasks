package com.nc.edu.ta.maximcherviakov.pr8.tests;

import com.nc.edu.ta.maximcherviakov.pr8.*;

import java.util.*;

import org.junit.*;
import static org.junit.Assert.*;

public class CloneListTest extends AbstractTaskListTest {

    public CloneListTest(Class<? extends TaskList> tasksClass) {
        super(tasksClass);
    }
    
    @Test
    public void testClone() {
        Task[] elements = { task("A"), task("B"), task("C") };
        addAll(elements);
        TaskList clone = (TaskList) tasks.clone();
        
        assertEquals(getTitle(), tasks.size(), clone.size());
        Iterator<?> ti = tasks.iterator();
        Iterator<?> ci = clone.iterator();
        while (ti.hasNext() && ci.hasNext())
            assertEquals(getTitle(), ti.next(), ci.next());
            
        assertNotSame(getTitle(), tasks, clone);
        
        clone.add(task("D"));
        assertEquals(getTitle(), clone.size() - 1, tasks.size());
        
        clone.remove(task("B"));
        assertContains(elements);
    }
}
