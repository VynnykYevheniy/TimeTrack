package org.yvynnyk.timetrack.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.service.TimeEntryService;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.yvynnyk.timetrack.constant.ExceptionConstants.Task.TASK_NOT_FOUND_WITH_ID;
import static org.yvynnyk.timetrack.constant.ExceptionConstants.TimeEntry.TASK_STATUS_ERROR;

@SpringBootTest
@AutoConfigureMockMvc
class TimeEntryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TimeEntryService timeEntryService;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void startTimeEntry_Success() throws Exception {
		Long taskId = 1L;

		doNothing().when(timeEntryService).start(taskId);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/timeEntry/start")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskId)))
				.andExpect(status().isNoContent())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void startTimeEntry_TaskNotFound() throws Exception {
		Long taskId = 1L;
		doThrow(new ResourceNotFoundException(TASK_NOT_FOUND_WITH_ID.formatted(taskId)))
				.when(timeEntryService).start(taskId);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/timeEntry/start")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskId)))
				.andExpect(status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
	}


	@Test
	void startTimeEntry_InvalidState() throws Exception {
		Long taskId = 1L;

		doThrow(new IllegalStateException(TASK_STATUS_ERROR))
				.when(timeEntryService).start(taskId);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/timeEntry/start")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskId)))
				.andExpect(status().isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void stopTimeEntry_Success() throws Exception {
		Long taskId = 1L;

		doNothing().when(timeEntryService).stop(taskId);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/timeEntry/{taskId}/stop", taskId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void stopTimeEntry_TaskNotFound() throws Exception {
		Long taskId = 1L;

		doThrow(new ResourceNotFoundException("Task not found")).when(timeEntryService).stop(taskId);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/timeEntry/{taskId}/stop", taskId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void stopTimeEntry_InvalidState() throws Exception {
		Long taskId = 1L;

		doThrow(new IllegalStateException("Invalid state")).when(timeEntryService).stop(taskId);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/timeEntry/{taskId}/stop", taskId)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}
}