package com.example.grpcserver.model;

import javax.persistence.*;

/**
 * @author z.Taghizadeh
 */
@Entity
@Table(name = "BasicInfo")
public class BasicInfo {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "code")
    private Integer code;

    @Column(name = "name")
    private String name;

    @Column(name = "englishName")
    private String englishName;

    @Column(name = "isActive")
    private Boolean isActive;

    public BasicInfo() {
    }

    public BasicInfo(Long id, Integer code, String name, String englishName, Boolean isActive) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.englishName = englishName;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
