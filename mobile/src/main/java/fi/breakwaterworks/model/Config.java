package fi.breakwaterworks.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fi.breakwaterworks.networking.Datasource;

@Entity(tableName = "config")
public class Config {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "config_id")
    private Long configId;

    protected String serverUrl;

    protected String token;

    //LOCAL OR REMOTE
    private String DefaultDataSource;

    protected boolean saveAlwaysLocally;

    protected boolean saveAlwaysRemote;

    public Config() {
    }
    public Config(Datasource local) {
        this.setDefaultDataSource(local.toString());
    }
    public Config(String token,String url) {
        this.token = token;
        this.serverUrl = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    public Long getConfigId() {
        return configId;
    }

    public void setConfigId(@NonNull Long configId) {
        this.configId = configId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public boolean isSaveAlwaysLocally() {
        return saveAlwaysLocally;
    }

    public void setDefaultDataSource(String defaultDataSource) {
        DefaultDataSource = defaultDataSource;
    }
    public String getDefaultDataSource() {
        return DefaultDataSource;
    }

    public void setSaveAlwaysLocally(boolean saveAlwaysLocally) {
        this.saveAlwaysLocally = saveAlwaysLocally;
    }

    public boolean isSaveAlwaysRemote() {
        return saveAlwaysRemote;
    }

    public void setSaveAlwaysRemote(boolean saveAlwaysRemote) {
        this.saveAlwaysRemote = saveAlwaysRemote;
    }

    public Datasource getDataSource(){
        if (!DefaultDataSource.isEmpty()) {
            return Datasource.valueOf(DefaultDataSource);
        }
        else{
            return Datasource.LOCAL;
        }
    }

}