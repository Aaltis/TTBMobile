package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    protected Long userId;
    protected String name;
    protected Boolean enabled;

    public Boolean getInitDone() {
        return initDone;
    }

    public void setInitDone(Boolean initDone) {
        this.initDone = initDone;
    }

    protected Boolean initDone;

    protected String email;

    @Ignore
    protected Set<WorkLog> worklogs;

    protected Date lastPasswordReset;

    public User() {
    }

    @Ignore
    public User(String name, boolean initDone) {
        this.name = name;
        this.initDone = initDone;
    }

    @Ignore
    public User(String name, String email) {
        super();
        this.name = name;
        this.email = email;
        this.enabled = true;
        this.worklogs = new HashSet<WorkLog>();
    }

    @NonNull
    public Long getUserId() {
        return userId;
    }

    public void setUserId(@NonNull Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<WorkLog> getWorklogs() {
        return worklogs;
    }

    public void setWorklogs(Set<WorkLog> worklogs) {
        this.worklogs = worklogs;
    }

    public Date getLastPasswordReset() {
        return lastPasswordReset;
    }

    public void setLastPasswordReset(Date lastPasswordReset) {
        this.lastPasswordReset = lastPasswordReset;
    }

}
