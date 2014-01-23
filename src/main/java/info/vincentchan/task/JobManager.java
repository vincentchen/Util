package info.vincentchan.task;

import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Vincent Chan
 * Create At: 13-12-5 下午3:38
 */
@Component
public class JobManager {
    private Map<Long, Job> jobs = new ConcurrentHashMap<Long, Job>(5);

    private static final Object lock = new Object();

    public JobManager() {
    }

    public Job getJob(Long jobId) {
        return jobs.get(jobId);
    }

    public boolean submitJob(Long jobId) {
        synchronized (lock) {
            if (jobs.get(jobId) != null) {
                return false;
            }
            Job job = new Job(jobId);
            jobs.put(jobId, job);
            job.setDaemon(true);
            job.start();
            return true;
        }
    }

    public void removeJob(Long jobId) {
        synchronized (lock) {
            Job job = jobs.remove(jobId);
        }
    }
}
