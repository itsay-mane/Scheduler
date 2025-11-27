package scheduler;


import java.util.*;

public class Scheduler  {
    PriorityQueue<User> users = new PriorityQueue<>();
    Map<JobId, Job> jobMap = new HashMap<>();

    public void addUser(User user) {
        users.add(user);
    }

    
    public JobId addJob(String execFile, int maxSpan, String userId){
        /*
        retourne l'identifiant du job ajouté
         */
        Job job = new Job (execFile, maxSpan);
        JobId jobId =  new JobId();
        job.setJobId(jobId);
        jobMap.put(jobId, job);
        for (User user : users) {
            if (user.getId().equals(userId)) {
                users.remove(user);
                user.addJob(job);
                users.add(user);
                return jobId;
            }
        }
        User newUser = new User(userId, new Score(0));
        newUser.addJob(job);
        users.add(newUser);
        return jobId;
    }

    public Job getJob(JobId id){
        /*
        retourne un objet avec les infos sur le job
         */
        return jobMap.get(id);
    }

    public Job extractNextJobToSchedule() {
        /*
        retourne le prochain job à exécuter, le supprime de l'ensemble des jobs en attente
        et met à jour le score utilisateur
         */
        User user = users.poll();
        //System.out.println(user);
        Job nextJob = user.getJob();
        user.removeFirstJob();
        return nextJob;
    }

}
