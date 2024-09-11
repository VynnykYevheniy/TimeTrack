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
import org.yvynnyk.timetrack.dto.TaskCreateDTO;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;
import org.yvynnyk.timetrack.service.TaskService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TaskService taskService;

	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		objectMapper = new ObjectMapper();
	}

	@Test
	void createTask_Success() throws Exception {
		TaskCreateDTO taskCreateDTO = new TaskCreateDTO("Task Name", "Task Description");
		TaskDTO createdTaskDTO = new TaskDTO(1L, "Task Name", "Task Description", TaskStatus.CREATE, LocalDateTime.now(), LocalDateTime.now());

		when(taskService.create(any(TaskCreateDTO.class))).thenReturn(createdTaskDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskCreateDTO)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("Task Name"))
				.andExpect(jsonPath("$.description").value("Task Description"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void updateTask_Success() throws Exception {
		TaskDTO taskDTO = new TaskDTO(1L, "Updated Task Name", "Updated Task Description", TaskStatus.IN_PROGRESS, null, null);

		when(taskService.update(anyLong(), any(TaskDTO.class))).thenReturn(taskDTO);

		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDTO)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("Updated Task Name"))
				.andExpect(jsonPath("$.description").value("Updated Task Description"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void updateTask_TaskNotFound() throws Exception {
		TaskDTO taskDTO = new TaskDTO(1L, "Updated Task Name", "Updated Task Description", TaskStatus.IN_PROGRESS, null, null);

		when(taskService.update(anyLong(), any(TaskDTO.class)))
				.thenThrow(new ResourceNotFoundException("Task not found"));

		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(taskDTO)))
				.andExpect(status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void getAllTasks_Success() throws Exception {
		List<TaskDTO> taskList = List.of(
				new TaskDTO(1L, "Task 1", "Description 1", TaskStatus.CREATE, null, null),
				new TaskDTO(2L, "Task 2", "Description 2", TaskStatus.IN_PROGRESS, null, null)
		);

		when(taskService.getAll()).thenReturn(taskList);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].name").value("Task 1"))
				.andExpect(jsonPath("$[1].name").value("Task 2"))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	void getAllTasks_NoContent() throws Exception {
		when(taskService.getAll()).thenReturn(Collections.emptyList());

		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/all")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent())
				.andDo(MockMvcResultHandlers.print());
	}
}