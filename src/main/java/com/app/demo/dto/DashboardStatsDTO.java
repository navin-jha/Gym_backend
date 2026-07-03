package com.app.demo.dto;

public class DashboardStatsDTO {

    private String email;
    private String name;

    private long totalMembers;
    private long activeMembers;
    private long totalTrainers;
    private long totalStaff;

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(String email, String name,
            long totalMembers,
            long activeMembers,
            long totalTrainers,
            long totalStaff) {
        this.email = email;
        this.name = name;
        this.totalMembers = totalMembers;
        this.activeMembers = activeMembers;
        this.totalTrainers = totalTrainers;
        this.totalStaff = totalStaff;
    }

    // ===== GETTERS & SETTERS =====

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public long getActiveMembers() {
        return activeMembers;
    }

    public void setActiveMembers(long activeMembers) {
        this.activeMembers = activeMembers;
    }

    public long getTotalTrainers() {
        return totalTrainers;
    }

    public void setTotalTrainers(long totalTrainers) {
        this.totalTrainers = totalTrainers;
    }

    public long getTotalStaff() {
        return totalStaff;
    }

    public void setTotalStaff(long totalStaff) {
        this.totalStaff = totalStaff;
    }
}