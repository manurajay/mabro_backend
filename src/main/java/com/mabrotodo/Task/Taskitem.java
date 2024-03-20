package com.mabrotodo.Task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Duration;
import java.util.Date;

@Entity
@Table(name = "todo")
public class Taskitem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Title is required")
    private String title;

    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean done;
    // constructors, getters, and setters...

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    //default constructor
    public Taskitem() {
        // Default constructor
    }


    public Taskitem(int id, String title, boolean done, Date date) {
        this.id = id;
        this.done = done;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
