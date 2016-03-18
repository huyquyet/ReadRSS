package com.example.framgianguyenhuyquyet.menuleft.models;

import java.io.Serializable;

/**
 * Created by FRAMGIA\nguyen.huy.quyet on 15/03/2016.
 */
public class Data implements Serializable {
    private String id;
    private String title;
    private String description;
    private String link;
    private String guid;
    private String pubDate;
    private String category;
    private String author;
    private String enclosure;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Data() {
        super();
    }

    public Data(String title, String description, String link, String guid, String pubDate, String category, String author, String enclosure) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.guid = guid;
        this.pubDate = pubDate;
        this.category = category;
        this.author = author;
        this.enclosure = enclosure;
    }
}
