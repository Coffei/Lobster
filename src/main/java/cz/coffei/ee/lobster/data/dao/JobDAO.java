package cz.coffei.ee.lobster.data.dao;

import cz.coffei.ee.lobster.data.JobEntity;
import cz.coffei.ee.lobster.data.JobStatus;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Coffei on 4.9.14.
 */
@Stateless
public class JobDAO {

    @PersistenceContext
    private EntityManager em;

    public void create(JobEntity job) {
        em.persist(job);
    }

    public void remove(JobEntity job) {
        if(!em.contains(job)) {
            job = em.merge(job);
        }

        em.remove(job);
    }

    public void update(JobEntity job) {
        em.merge(job);
    }

    /**
     * Lists all jobs
     * @return
     */
    public List<JobEntity> listAllJobs() {
        TypedQuery<JobEntity> query = em.createQuery("select * from jobentity ORDER BY name ASC", JobEntity.class);
        return query.getResultList();
    }

    /**
     * Lists all jobs ready to be executed. Note that status of these jobs can be both IDLE and RUNNING.
     * @return
     */
    public List<JobEntity> listAllExecutionJobs() {
        TypedQuery<JobEntity> query = em.createQuery("select * from jobentity WHERE NOT(status=:status) ORDER BY name ASC",
                JobEntity.class);

        query.setParameter("status", JobStatus.DISABLED);

        return query.getResultList();
    }
}
