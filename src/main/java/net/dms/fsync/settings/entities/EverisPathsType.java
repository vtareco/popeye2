package net.dms.fsync.settings.entities;

/**
 * Created by dminanos on 18/04/2017.
 */
public enum EverisPathsType {
    PROJECT_DIRECTORY("");

    private String path;

    EverisPathsType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
