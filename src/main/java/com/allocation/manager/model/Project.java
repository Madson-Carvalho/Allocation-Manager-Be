package com.allocation.manager.model;

import com.allocation.manager.exceptions.InsufficientWorkHoursException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

import static com.allocation.manager.util.FormatDataUtil.formatInstantDateTime;
import static java.time.Duration.between;

@Entity
@Table(name = "Projects")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "projectId")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    private UUID projectId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private float projectHours;
    private long allocatedHours = 0;

    @Column(nullable = false)
    private String projectCoordinator;
    @Column(nullable = false)
    private String fundingSource;
    private float totalProjectValue;
    @Column(nullable = false)
    private Instant initialDate;
    @Column(nullable = false)
    private Instant deliveryDate;

    public UUID getProjectId() {
        return projectId;
    }

    public void setProjectId(UUID projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProjectHours() {
        return projectHours;
    }

    public void setProjectHours(float projectHours) {
        this.projectHours = projectHours;
    }

    public long getAllocatedHours() {
        return allocatedHours;
    }

    public void setAllocatedHours(long allocatedHours) {
        this.allocatedHours = allocatedHours;
    }

    public String getProjectCoordinator() {
        return projectCoordinator;
    }

    public void setProjectCoordinator(String projectCoordinator) {
        this.projectCoordinator = projectCoordinator;
    }

    public String getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    public float getTotalProjectValue() {
        return totalProjectValue;
    }

    public void setTotalProjectValue(float totalProjectValue) {
        this.totalProjectValue = totalProjectValue;
    }

    public Instant getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Instant initialDate) {
        this.initialDate = initialDate;
    }

    public Instant getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Instant deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public void verifyProjectValidity(long requestAllocationInSeconds){
        if(requestAllocationInSeconds > projectHours && allocatedHours + requestAllocationInSeconds > projectHours){
            throw new InsufficientWorkHoursException(
                    "O Projeto esta vigente apenas entre o período: "
                            + formatInstantDateTime(this.getInitialDate()) + " à " + formatInstantDateTime(this.getDeliveryDate()));
        }
    }
}
