package cz.coffei.ee.lobster.execution;

import cz.coffei.ee.lobster.data.Job;
import cz.coffei.ee.lobster.data.JobEntity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * Created by Coffei on 4.9.14.
 */
public class JobExecution {

    /**
     * Takes an entity and creates job object ready to be executed.
     * @return
     */
    public static Job createJobExecution(JobEntity job) throws JobExecutionException {
        if(job==null)
            throw new NullPointerException("job");
        if(job.getType()==null)
            throw new JobExecutionException("Null job type", job);

        Class<? extends Job> jobType = job.getType();
        Constructor jobConstructor = findEmptyConstructor(jobType.getConstructors());

        if(jobConstructor==null)
            throw new JobExecutionException(
                    "No public parameter-less constructor found for job of type " + job.getType().getName(),
                    job);

        //else we can create the job via the constructor
        try {
            Job executionJob = (Job)jobConstructor.newInstance();
            return executionJob;
        } catch (InstantiationException e) {
            throw new JobExecutionException("Cannot instantiate the job", e, job);
        } catch (IllegalAccessException e) {
            throw new JobExecutionException("Cannot access and instantiate the job", e, job);
        } catch (InvocationTargetException e) {
            throw new JobExecutionException("Cannot invoke and instantiate the job", e, job);
        }
    }

    private static Constructor findEmptyConstructor(Constructor[] constructors) {
        for(Constructor constructor : constructors) {
            if(constructor.getParameterTypes().length==0 && Modifier.isPublic(constructor.getModifiers())) {
            //find proper constructor
                return constructor;
            }
        }

        return null;
    }
}
