import io.swagger.client.ApiException;
import io.swagger.client.api.TimeSlotJsonControllerApi;
import io.swagger.client.model.TimeSlotDTO;
import io.swagger.client.model.TimeSlotDetailDTO;

import org.threeten.bp.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeSlotClient {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Error: Invalid number of arguments.");
            System.err.println("Usage: java TimeSlotClient <date> <startTime> <endTime>");
            System.exit(1);
        }
        try {
            int personId = Integer.parseInt(args[0]);
            int durationMinutes = Integer.parseInt(args[1]);
            String description = args[2];

            // Calculate startTime and endTime
            LocalTime startTime = LocalTime.now();
            LocalTime endTime = startTime.plusMinutes(durationMinutes);
            LocalDate date = LocalDate.now();

            // Create the TimeSlotDTO
            TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
            timeSlotDTO.setPersonId(personId);
            timeSlotDTO.setDate(date);
            timeSlotDTO.setStartTime(LocalTime.parse(startTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            timeSlotDTO.setEndTime(LocalTime.parse(endTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"))));
            timeSlotDTO.setDescription(description);

            // Create an instance of the API client
            TimeSlotJsonControllerApi timeSlotApi = new TimeSlotJsonControllerApi(); // You might need to configure the ApiClient

            // Make the API call
            TimeSlotDetailDTO createdTimeSlot = timeSlotApi.createTimeSlotJSON(timeSlotDTO);

            System.out.println("Time slot created successfully!");
            System.out.println("Created Time Slot ID: " + createdTimeSlot.getId());
            // Print other details as needed

        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid number format for personId or durationMinutes.");
            System.err.println("Usage: java CreateTimeSlotClient <personId> <durationMinutes> <description>");
            System.exit(1);
        } catch (ApiException e) {
            System.err.println("Error: API call failed: " + e.getMessage());
            System.err.println("Response code: " + e.getCode());
            System.exit(1);
        }
    }
}
