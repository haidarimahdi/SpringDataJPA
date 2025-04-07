package com.example.SpringDataJPA.dto;

import java.time.Duration;

/**
 * Data Transfer Object (DTO) for Project time summary.
 * This class is used to transfer data between the application and the client.
 * It contains the project ID, project name, total time spent on the project in seconds,
 * and the percentage of total time spent on the project.
 * It also provides methods to format the duration and percentage for display.
 */
public record ProjectTimeSummaryDTO(Integer projectId, String projectName, long totalSeconds, double percentage) {

    public String getFormattedDuration() {
        Duration duration = Duration.ofSeconds(totalSeconds);
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();
        return String.format("%d hours, %d minutes", hours, minutes);
    }

    public String getFormattedPercentage() {
        return String.format("%.2f%%", percentage);
    }
}