package scheduler;


import java.util.LinkedList;

public class User implements Comparable<User> {
    private String id;
    private Score score;
    private LinkedList<Job> jobs = new LinkedList<>();
    
    public User(String id, Score score) {
        this.id = id;
        this.score = score;
    }

    public String getId() {
        return this.id;
    }

    public void addJob(Job job) {
        jobs.addLast(job);
    }
    public Job getJob() {
        if (jobs.isEmpty()) {
            return null;
        }
        return jobs.getFirst();
    }

    public void removeFirstJob() {
        jobs.removeFirst();
    }

    @Override
    public int compareTo(User other) {
        boolean thisHasJobs = !this.jobs.isEmpty();
        boolean otherHasJobs = !other.jobs.isEmpty();

        if (thisHasJobs && !otherHasJobs) {
            return -1; // this < other → this en premier
        }

        if (!thisHasJobs && otherHasJobs) {
            return 1; // this > other → other en premier
        }

        // 3) Si les deux ont OU n'ont pas de jobs → comparer les scores
        int scoreCompare = this.score.compareTo(other.score);
        if (scoreCompare != 0) {
            return scoreCompare;
        }

        // 4) En dernier recours : ID (pour éviter l'égalité totale)
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", score=" + score.getValue() + ", jobs=" + jobs + '}';
    }

}
