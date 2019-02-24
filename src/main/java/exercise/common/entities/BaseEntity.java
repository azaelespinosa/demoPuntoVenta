package exercise.common.entities;

import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {

    @Column(name = "SOFT_DELETE")
    private boolean softDelete;

    @Column(name = "MODIFIED_AT")
    private Timestamp modifiedAt;

    @Column(name = "CREATED_AT",updatable = false)
    private Timestamp createdAt;

    @Column(name = "UUID",updatable = false)
    private String uuid;

    protected BaseEntity() {

    }

    @PrePersist
    public void setAuditInfo() {
        Calendar current = Calendar.getInstance();
        this.setCreatedAt(new Timestamp(current.getTimeInMillis()));
        this.setSoftDelete(false);
        this.setModifiedAt(new Timestamp(current.getTimeInMillis()));
        if (StringUtils.isEmpty(uuid)) this.setUuid(UUID.randomUUID().toString());
    }

    @PreUpdate
    public void setModifyInfo() {
        Calendar current = Calendar.getInstance();
        this.setModifiedAt(new Timestamp(current.getTimeInMillis()));
    }
}