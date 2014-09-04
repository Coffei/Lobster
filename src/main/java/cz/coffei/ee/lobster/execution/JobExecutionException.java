package cz.coffei.ee.lobster.execution;

import cz.coffei.ee.lobster.data.JobEntity;

/**
 * Exception to indicate there was a problem with execution of certain Job (or JobEntity as this exception can indicate
 * a Job cannot be created).
 * Created by Coffei on 4.9.14.
 */
public class JobExecutionException extends Exception {

    private JobEntity job;

    public JobExecutionException(JobEntity job) {
        this.job = job;
    }

    public JobExecutionException(String message, JobEntity job) {
        super(message);
        this.job = job;
    }

    public JobExecutionException(String message, Throwable cause, JobEntity job) {
        super(message, cause);
        this.job = job;
    }

    public JobExecutionException(Throwable cause, JobEntity job) {
        super(cause);
        this.job = job;
    }
}
