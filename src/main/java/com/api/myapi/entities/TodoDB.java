package com.api.myapi.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "TodoDB")
public class TodoDB {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tododb_seq")
    @SequenceGenerator(name = "tododb_seq", sequenceName = "TODODB_SEQ", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private UserDB user;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "done")
    private boolean done;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

}
