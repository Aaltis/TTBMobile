package fi.breakwaterworks.networking.local.DAO;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import fi.breakwaterworks.model.WorkLog;

@Dao
public interface WorkLogDAO extends BaseDAO<WorkLog> {
    @Insert
    Long insert(WorkLog workLog);

    @Query("select * from worklogs where worklog_id=:id")
    WorkLog loadWorkLogWithId(long id);

    @Query("select * from worklogs")
    List<WorkLog> loadAll();

    @Query("select * from worklogs where template = :templateValue")
    List<WorkLog> loadAllTemplates(boolean templateValue);

    @Query("select * from worklogs where name = :name and template = :isTemplate")
    WorkLog loadTemplateWithName(String name, boolean isTemplate);
}
