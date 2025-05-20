package model;

import java.time.LocalDateTime;

public class BaseModel {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private User createdUser;


    public void setUpdatedUser(User updatedUser) {
        this.updatedUser = updatedUser;
    }

    private User updatedUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdatedDate() {
        return updatedDate;
    }

    public User getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public User getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(User createdUser) {
        this.createdUser = createdUser;
    }
}
