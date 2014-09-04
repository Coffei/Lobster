package cz.coffei.ee.lobster.data;

/**
 * Represents a runnable job. All job implementation must implement this. Job also has to have an empty constructor.
 * Created by Coffei on 4.9.14.
 */
public interface Job {

    /**
     * Run the job.
     * @return false if the job should be disabled after its finished, true otherwise.
     */
    boolean run();
}
