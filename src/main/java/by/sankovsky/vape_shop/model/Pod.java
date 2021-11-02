package com.example.library.library.model;

import com.example.library.library.converter.YearAttributeConverter;

import javax.persistence.*;
import java.time.Year;
import java.util.Objects;

@Entity
public class Pod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    @Column(length = 100)
    private String name;

    @Column(columnDefinition = "int(8) not null default '0'")
    private Integer battery;

    @Column(length = 100)
    private String manufacturer;

    @Column(
            name = "year_of_issue",
            columnDefinition = "smallint"
    )
    @Convert(
            converter = YearAttributeConverter.class
    )
    private Year yearOfIssue;

    @Column(columnDefinition = "int(8) not null default '0'")
    private Integer chargeTime;

    @Column(columnDefinition = "int(2) not null default '0'")
    private Integer volume;

    @Column(columnDefinition = "int(8) not null default '0'")
    private Integer availableCount;

    @Lob
    private byte[] cover;

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBattery() {
        return battery;
    }

    public void setBattery(Integer battery) {
        this.battery = battery;
    }

    public Year getYearOfIssue() {
        return yearOfIssue;
    }

    public void setYearOfIssue(Year yearOfIssue) {
        this.yearOfIssue = yearOfIssue;
    }

    public Integer getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Integer getAvailableCount() {
        return availableCount;
    }

    public void setAvailableCount(Integer availableCount) {
        this.availableCount = availableCount;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pod pod = (Pod) o;
        return Objects.equals(pid, pod.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }

    @Override
    public String toString() {
        return "Pod{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", battery=" + battery +
                ", yearOfIssue=" + yearOfIssue +
                ", chargeTime=" + chargeTime +
                ", volume=" + volume +
                ", availableCount=" + availableCount +
                '}';
    }
}
