package org.workhabit.drupal.api.entity.drupal7;

import java.util.Date;

/**
 * Copyright 2009 - WorkHabit, Inc. - acs
 * Date: 11/30/10, 10:57 AM
 */
public class DrupalFile implements DrupalEntity {
    // serialized: { "filename": "testimage.jpg", "filepath": "sites/default/files/testimage_4.jpg", "filemime": "image/jpeg", "source": "upload", "destination": "sites/default/files/testimage_4.jpg", "filesize": 32769, "uid": "107", "status": 0, "timestamp": 1295559121, "fid": "67650" }
    private int fid;
    private String filepath;
    private String filename;
    private String filemime;
    private String source;
    private String destination;
    private int filesize;
    private int uid;
    private int status;
    private Date timestamp;

        public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
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

    public String getId() {
        return String.valueOf(fid);
    }

    public String getFilemime() {
        return filemime;
    }

    public void setFilemime(String filemime) {
        this.filemime = filemime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFilesize() {
        return filesize;
    }

    public void setFilesize(int filesize) {
        this.filesize = filesize;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
