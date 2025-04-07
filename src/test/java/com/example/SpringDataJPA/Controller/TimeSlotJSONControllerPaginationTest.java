package com.example.SpringDataJPA.Controller;

import com.example.SpringDataJPA.controller.html.TimeSlotController;
import com.example.SpringDataJPA.controller.json.TimeSlotJSONController;
import com.example.SpringDataJPA.model.TimeSlot;
import com.example.SpringDataJPA.service.TimeSlotService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*; // For Hamcrest matchers like hasSize, is

// Test JSON Controller
@WebMvcTest(TimeSlotJSONController.class)
public class TimeSlotJSONControllerPaginationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean // Mocks the service dependency
    private TimeSlotService timeSlotService;

    @Test
    void testGetAllTimeSlotsJSONPaginated() throws Exception {
        // Arrange: Mock service response
        TimeSlot ts1 = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(17, 0), "Description 1", null, null);
        TimeSlot ts2 = new TimeSlot(LocalDate.now().minusDays(1), LocalTime.of(9, 0), LocalTime.of(17, 0), "Description 2", null, null);
        List<TimeSlot> timeSlots = Arrays.asList(ts1, ts2);
        // Create a Page object to return from the mock service
        // PageRequest: page 0, size 2. PageImpl: content, pageable, total elements
        Page<TimeSlot> mockPage = new PageImpl<>(timeSlots, PageRequest.of(0, 2), 5);

        when(timeSlotService.getAllTimeSlotsPaginated(any(Pageable.class))).thenReturn(mockPage);

        // Act & Assert: Perform GET request and check JSON response
        mockMvc.perform(get("/timeSlot/list.json")
                        .param("page", "0")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                // Use JsonPath assertions to check the structure and values
                .andExpect(jsonPath("$.content", hasSize(2)))
                .andExpect(jsonPath("$.totalElements", is(5)))
                .andExpect(jsonPath("$.totalPages", is(3))) // ceil(5/2) = 3
                .andExpect(jsonPath("$.number", is(0)))
                .andExpect(jsonPath("$.size", is(2)));
    }

    // Add similar tests for getProjectTimeSlotsJSONPaginated(...)

}

// Test HTML Controller (Similar structure, but assert view name, model attributes, potentially HTML content)
@WebMvcTest(TimeSlotController.class)
class TimeSlotControllerPaginationTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private TimeSlotService timeSlotService;

    @Test
    void testShowAllTimeSlotsHTMLPaginated() throws Exception {
        // Arrange
        TimeSlot ts1 = new TimeSlot(LocalDate.now(), LocalTime.of(9, 0), LocalTime.of(17, 0), "Description 1", null, null);
        List<TimeSlot> timeSlots = List.of(ts1);
        Page<TimeSlot> mockPage = new PageImpl<>(timeSlots, PageRequest.of(0, 5), 1);
        when(timeSlotService.getAllTimeSlotsPaginated(any(Pageable.class))).thenReturn(mockPage);

        // Act & Assert
        mockMvc.perform(get("/timeSlot/")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(view().name("timeSlot-index")) // Check correct template is returned
                .andExpect(model().attributeExists("timeSlotPage")) // Check model contains the page
                .andExpect(model().attribute("timeSlotPage", hasProperty("totalElements", is(1L))))
                .andExpect(model().attribute("timeSlotPage", hasProperty("content", hasSize(1))));
        // Optionally use HtmlUnit or similar to assert rendered HTML content
    }

    // Add similar tests for showProjectTimeSlots(...)
}
