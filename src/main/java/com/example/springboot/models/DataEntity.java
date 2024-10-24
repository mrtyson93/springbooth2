package com.example.springboot.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class DataEntity {

    @Column(name="source", nullable = false)
    private String source;

    @Column(name = "code_list_code", nullable = false)
    private String codeListCode;

    @Id
    @Column(name="code", nullable = false)
    private String code;

    @Column(name = "display_value", nullable = false)
    private String displayValue;

    @Column(name = "long_description", columnDefinition = "TEXT")
    private String longDescription;

    @Column(name = "from_date", nullable = false)
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;

    public DataEntity() {
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCodeListCode() {
        return codeListCode;
    }

    public void setCodeListCode(String codeListCode) {
        this.codeListCode = codeListCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getSortingPriority() {
        return sortingPriority;
    }

    public void setSortingPriority(Integer sortingPriority) {
        this.sortingPriority = sortingPriority;
    }

    @Override
    public String toString() {
        return "DataEntity{" +
                "source='" + source + '\'' +
                ", codeListCode='" + codeListCode + '\'' +
                ", code='" + code + '\'' +
                ", displayValue='" + displayValue + '\'' +
                ", longDescription='" + (longDescription != null ? longDescription.substring(0, Math.min(longDescription.length(), 50)) + "..." : "null") + '\'' +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", sortingPriority=" + sortingPriority +
                '}';
    }
}
