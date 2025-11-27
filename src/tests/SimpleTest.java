package tests;

import org.junit.Test;
import static org.junit.Assert.*;

import scheduler.*;

public class SimpleTest {
    @Test
    public void test01() {
        /* Pour se chauffer, un seul utilisateur, un seul job. */
        User steve = new User("Steve", new Score(1));
        Scheduler sched = new Scheduler();
        sched.addUser(steve);
        JobId rip = sched.addJob("sleep +inf", 10, "Steve");
        Job steveJob = sched.getJob(rip);

        Job next = sched.extractNextJobToSchedule();
        assertNotNull(next); // C'est nul si c'est null !
        assertEquals(steveJob, next); // Si c'est pas nul, on vérifie
                                      // qu'on a bien récupéré le job de Steve.
        System.out.println("Test 1 OK");
    }

    @Test
    public void test02() {
        /*
            Cette fois, ça rigole plus : deux utilisateurs !
            - Bob, qui a un job à exécuter ;
            - Alice, qui n'en a pas, mais qui a un score tout petit,
            pour donner envie au scheduler de la choisir.

            Voyons si notre scheduler retourne le job de Bob !
            (@Sylvain, job de Bob, c'est une contrepèterie, t'as vu ?)
        */
        User alice = new User("Alice", new Score(1));
        User bob = new User("Bob", new Score(100));

        Scheduler sched = new Scheduler();
        sched.addUser(alice);
        sched.addUser(bob);

        JobId ls = sched.addJob("ls -l | wc", 10, "Bob");
        Job job2Bob = sched.getJob(ls);

        Job next = sched.extractNextJobToSchedule();
        assertNotNull(next); // C'est nul si c'est null !
        assertEquals(job2Bob, next); // Si c'est pas nul, on vérifie
                                     // qu'on a bien récupéré le job de Bob.
        System.out.println("Test 2 OK");
    }

    @Test
    public void test03() {
        /*
            Bob en a marre de bosser pendant que cette feignasse
            d'Alice reste à la maison à regarder Joséphine Ange Gardien
            sur Salto en mangeant des chips. Leur couple bat de l'aile.

            Alice décide de réagir : elle sort brusquement de chez elle,
            traverse la rue et se dégote un job.

            On a du coup :
            - Bob, qui a un job à exécuter ;
            - Alice, qui a un job à exécuter aussi !

            On considère qu'Alice et Bob ont le même score au départ.
            Pour les départager, on choisira Alice plutôt que Bob,
            parce que dans ce cas, on suit l'ordre lexicographique des noms d'utilisateur.
            Non, c'est pas injuste, c'est la vie (spécifications écrites
            par Bouveret & Broquedis).

            Voyons si notre scheduler retourne bien le job d'Alice d'abord,
            puis le job de Bob !
        */
        User alice = new User("Alice", new Score(5));
        User bob = new User("Bob", new Score(5));

        Scheduler sched = new Scheduler();
        sched.addUser(alice);
        sched.addUser(bob);

        JobId ls = sched.addJob("ls -l | wc", 10, "Bob");
        Job job2Bob = sched.getJob(ls);
        JobId make = sched.addJob("make clean && make", 10, "Alice");
        Job job2Alice = sched.getJob(make);

        /* Ici, on s'attend à récupérer le job d'Alice... */
        Job next = sched.extractNextJobToSchedule();
        assertNotNull(next);
        assertEquals(job2Alice, next);

        /* ... et seulement ensuite le job de Bob. */
        next = sched.extractNextJobToSchedule();
        assertNotNull(next);
        assertEquals(job2Bob, next);
        System.out.println("Test 3 OK");
    }

    @Test
    public void test04() {
        /*
            Alice prend goût à la vie active. Elle décide d'agrandir
            sa surface financière en contractant un deuxième job.

            On a du coup :
            - Bob, qui a un job à exécuter ;
            - Alice, qui en a deux !

            On considère qu'Alice et Bob ont le même score au départ.
            Pour les départager au début, on choisira Alice plutôt que Bob,
            pour les mêmes raisons objectives que précédemment.

            Cette fois-ci, notre scheduler devrait retourner le job
            d'Alice le plus ancien, puis le job de Bob, et enfin le second job d'Alice.
        */
        User alice = new User("Alice", new Score(5));
        User bob = new User("Bob", new Score(5));

        Scheduler sched = new Scheduler();
        sched.addUser(alice);
        sched.addUser(bob);

        JobId ls = sched.addJob("ls -l | wc", 10, "Bob");
        Job job2Bob = sched.getJob(ls);
        JobId make = sched.addJob("make clean && make", 10, "Alice");
        Job job2AliceLePlusAncien = sched.getJob(make);
        JobId find = sched.addJob("find . -type f", 10, "Alice");
        Job job2AliceLePlusRecent = sched.getJob(find);

        /* En preum's, le job d'Alice le plus ancien ...*/
        Job next = sched.extractNextJobToSchedule();
        assertNotNull(next);
        assertEquals(job2AliceLePlusAncien, next);

        /*... ensuite celui de Bob...*/
        next = sched.extractNextJobToSchedule();
        assertNotNull(next);
        assertEquals(job2Bob, next);

        /*... et enfin le job d'Alice le plus récent. */
        next = sched.extractNextJobToSchedule();
        assertNotNull(next);
        assertEquals(job2AliceLePlusRecent, next);
        System.out.println("Test 4 OK");
    }
}
