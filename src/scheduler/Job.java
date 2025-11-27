package scheduler;

public class Job {    
    private JobId jobId;
    private String execFile;
    private int maxSpan;
    private JobStatus status;


    public Job(String execFile, int maxSpan) {
        this.jobId = new JobId();
        this.execFile = execFile;
        this.maxSpan = maxSpan;
        this.status = JobStatus.PENDING;
    }

    public void setJobId(JobId jobId) {
        this.jobId = jobId;
    }

    public void start() {
        this.status = JobStatus.STARTED;
    }

    @Override
    public String toString() {
        return "<" + this.execFile + ", " + this.maxSpan + ">";
    }

}
