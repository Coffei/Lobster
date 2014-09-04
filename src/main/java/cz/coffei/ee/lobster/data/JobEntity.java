package cz.coffei.ee.lobster.data;

import com.sun.javafx.beans.annotations.NonNull;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Coffei on 4.9.14.
 */
@Entity
public class JobEntity {
    @GeneratedValue
    @Id
    private Long id;

    @NotEmpty
    private String name;

    @NotNull
    private Class<? extends Job> type;

    @NotNull
    @Enumerated(EnumType.STRING)
    private JobStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<? extends Job> getType() {
        return type;
    }

    public void setType(Class<? extends Job> type) {
        this.type = type;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }
}
