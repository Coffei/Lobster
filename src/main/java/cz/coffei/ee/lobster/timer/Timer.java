package cz.coffei.ee.lobster.timer;

import cz.coffei.ee.lobster.data.Job;
import cz.coffei.ee.lobster.data.JobEntity;
import cz.coffei.ee.lobster.data.JobStatus;
import cz.coffei.ee.lobster.data.dao.JobDAO;
import cz.coffei.ee.lobster.execution.JobExecution;
import cz.coffei.ee.lobster.execution.JobExecutionException;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.logging.Logger;

/**
 * Main timer to execute the pending jobs. Jobs shall be executed as often as possible (fixed for now).
 * Created by Coffei on 4.9.14.
 */@Stateless
   public class Timer {

    private Logger log = Logger.getLogger(Timer.class.getName());

    @Inject
    private JobDAO dao;

    @Schedule(hour = "*", minute = "*/5") // run jobs every 5 minutes
    public void runJobs() {
        List<JobEntity> jobs = dao.listAllExecutionJobs();
        for(JobEntity job : jobs) {
            switch (job.getStatus()) {
                case DISABLED: //should not happen, ignore such a job
                    break;
                case RUNNING:
                    handleRuningJob(job);
                    break;
                case IDLE:
                    handleIdleJob(job);
                    break;

            }
        }
    }

    private void handleIdleJob(JobEntity job) {
        //basically just run the job

        Job jobExecution = null;
        try {
            jobExecution = JobExecution.createJobExecution(job);
        } catch (JobExecutionException e) {
            log.severe(String.format("Creating job execution failed, id: %s, name: %s",
                    new String[]{job.getId().toString(), job.getName()}));

            disableJob(job);
        }

        if(jobExecution!=null) {

            boolean disable = false;
            try {
                disable = !jobExecution.run();
            } catch (Exception e) {
                log.severe(String.format("Job execution failed, id: %s, name: %s",
                        new String[]{job.getId().toString(), job.getName()}));
            }

            if (disable) { // disable job if requested
                disableJob(job);
            }
        }
    }

    private void disableJob(JobEntity job) {
        job.setStatus(JobStatus.DISABLED);
        dao.update(job);
    }

    private void handleRuningJob(JobEntity job) {
        job.setStatus(JobStatus.IDLE); // set job to idle and skip its execution
        dao.update(job);

        log.warning(String.format("Found a running job, id: %s, name: %s", new String[]{job.getId().toString(), job.getName()}));
    }
}
