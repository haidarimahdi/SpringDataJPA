import javax.annotation.processing.Generated;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Generated("jsonschema2pojo")
public class TimeSlotClient {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static void main(String[] args) {

        if (args.length != 3) {
            System.err.println("Usage: java TimeSlotClient <userID> <duration> <description>");
            System.err.println(" userID: The ID of the person to creat the time slot for (Integer).");
            System.err.println(" duration: The duration of the time slot in hours (Integer).");
            System.err.println(" description: The description to be displayed in the time slot (String).");
            System.exit(1);
        }

        int userId = 0;
        int duration = 0;

        try {
            userId = Integer.parseInt(args[0]);
            duration = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid input. Please enter valid integers for userID and duration.");
            System.exit(1);
        }
        String description = args[2];

        createTimeSlot(userId, duration, description);
    }

    private static void createTimeSlot(int userId, int duration, String description) {
        try {
            // Construct the URL
            URL url = new URL("http://localhost:8080/timeSlot/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            connection.setDoOutput(true);

            // Create the JSON payload
            LocalDateTime now = LocalDateTime.now();
            String startTimeString = now.format(DATE_TIME_FORMATTER);
            String endTimeString = now.plusSeconds(duration).format(DATE_TIME_FORMATTER);

            // Extract hour, minute, second, nano from LocalDateTime
            int startHour = now.getHour();
            int startMinute = now.getMinute();
            int startSecond = now.getSecond();
            int startNano = now.getNano();

            LocalDateTime endTime = now.plusSeconds(duration);
            int endHour = endTime.getHour();
            int endMinute = endTime.getMinute();
            int endSecond = endTime.getSecond();
            int endNano = endTime.getNano();

            String jsonInputString = String.format(
                    "{" +
                            "\"date\": \"%s\"," +
                            "\"startTime\": {" +
                            "\"hour\": %d," +
                            "\"minute\": %d," +
                            "\"second\": %d," +
                            "\"nano\": %d" +
                            "}," +
                            "\"endTime\": {" +
                            "\"hour\": %d," +
                            "\"minute\": %d," +
                            "\"second\": %d," +
                            "\"nano\": %d" +
                            "}," +
                            "\"description\": \"%s\"," +
                            "\"personId\": %d" +
                            "}",
                    now.toLocalDate(),
                    startHour, startMinute, startSecond, startNano,
                    endHour, endMinute, endSecond, endNano,
                    description, userId);

            try (OutputStream os = connection.getOutputStream()) {
                // Send the JSON payload
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
//                os.write(input, 0, input.length);
                os.write(input);
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response code: " + responseCode);

//            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
//                System.out.println("Response Headers: ==========================");
//                StringBuilder response = new StringBuilder();
//                String responseLine = null;
//                while ((responseLine = br.readLine()) != null) {
//                    response.append(responseLine.trim());
//                }
//                System.out.println("Response Body: " + response.toString());
//            }
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            System.out.println("Response Headers: ==========================");
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("Response Body: " + response.toString());

        } catch (IOException e) {
            System.err.println("Error: An error occurred while communicating with the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
