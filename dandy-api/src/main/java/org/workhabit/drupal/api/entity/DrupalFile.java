package org.workhabit.drupal.api.entity;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/30/10, 10:57 AM
 */
public class DrupalFile {
    private String file;
    private String fid;
    private String filepath;
    private String filename;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
