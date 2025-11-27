package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.util.TreeSet;

import scheduler.Scheduler;
import scheduler.User;
import scheduler.Score;
import scheduler.Job;
import scheduler.JobId;


public class SchedulerTest {
    @Test
    void testOrderAndEquality() {
        /** Basic comparison and equality tests **/
        User u1 = new User("Alice", new Score(42));
        User u2 = new User("Bob", new Score(42));
        User u3 = new User("Carole", new Score(41));
        User u4 = new User("Alice", new Score(41));

        u2.addJob(new Job("execFile", 42));
        u3.addJob(new Job("execFile", 42));
        
        assertTrue(u1.equals(u4));
        assertFalse(u1.equals(u3));
        TreeSet<User> set = new TreeSet<>();
        set.add(u1);
        set.add(u2);
        set.add(u3);
        assertEquals(u3, set.pollFirst());
        assertEquals(u2, set.pollFirst());
        assertEquals(u1, set.pollFirst());        
    }
    
    @Test
    void testExtractNextJobToSchedule() {
        /** A basic scenario that extracts all the jobs from the scheduler. */
        User alice = new User("Alice", new Score(5));
        User bob = new User("Bob", new Score(7));
        User carole = new User("Carole", new Score(3));

        Scheduler scheduler = new Scheduler();
        scheduler.addUser(alice);
        scheduler.addUser(bob);
        scheduler.addUser(carole);

        JobId alice01 = scheduler.addJob("alice01", 42, "Alice");
        JobId alice02 = scheduler.addJob("alice02", 42, "Alice");
        JobId alice03 = scheduler.addJob("alice03", 42, "Alice");
        JobId alice04 = scheduler.addJob("alice04", 42, "Alice");

        JobId bob01 = scheduler.addJob("bob01", 42, "Bob");
        JobId bob02 = scheduler.addJob("bob02", 42, "Bob");
        JobId bob03 = scheduler.addJob("bob03", 42, "Bob");

        JobId carole01 = scheduler.addJob("carole01", 42, "Carole");
        JobId carole02 = scheduler.addJob("carole02", 42, "Carole");
        JobId carole03 = scheduler.addJob("carole03", 42, "Carole");
        JobId carole04 = scheduler.addJob("carole04", 42, "Carole");
        JobId carole05 = scheduler.addJob("carole05", 42, "Carole");
        JobId carole06 = scheduler.addJob("carole06", 42, "Carole");
        JobId carole07 = scheduler.addJob("carole07", 42, "Carole");
        JobId carole08 = scheduler.addJob("carole08", 42, "Carole");
        JobId carole09 = scheduler.addJob("carole08", 42, "Carole");

        Job nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole01), nextJob);
        // Scores : 5, 7, 4 
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole02), nextJob);
        // Scores : 5, 7, 5
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(alice01), nextJob);
        // Scores : 6, 7, 5
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole03), nextJob);
        // Scores : 6, 7, 6
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(alice02), nextJob);
        // Scores : 7, 7, 6
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole04), nextJob);
        // Scores : 7, 7, 7
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(alice03), nextJob);
        // Scores : 8, 7, 7
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(bob01), nextJob);
        // Scores : 8, 8, 7
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole05), nextJob);
        // Scores : 8, 8, 8
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(alice04), nextJob);
        // Scores : 9, 8, 8
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(bob02), nextJob);
        // Scores : 9, 9, 8
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole06), nextJob);
        // Scores : 9, 9, 9
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(bob03), nextJob);
        // Scores : 9, 10, 9
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole07), nextJob);
        // Scores : 9, 10, 10
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole08), nextJob);
        // Scores : 9, 10, 11
        JobId alice05 = scheduler.addJob("alice05", 42, "Alice");
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(alice05), nextJob);
        // Scores : 10, 10, 11
        nextJob = scheduler.extractNextJobToSchedule();
        assertNotNull(nextJob);
        assertEquals(scheduler.getJob(carole09), nextJob);
        // Scores : 10, 10, 11
        
        assertNull(scheduler.extractNextJobToSchedule());
    }
}

