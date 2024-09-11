package org.yvynnyk.timetrack.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yvynnyk.timetrack.dto.TaskCreateDTO;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.mapper.TaskMapper;
import org.yvynnyk.timetrack.model.Task;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;
import org.yvynnyk.timetrack.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

	@Mock
	private TaskRepository taskRepository;

	@Mock
	private TaskMapper taskMapper;

	@InjectMocks
	private TaskServiceImpl taskService;

	private Task task;
	private TaskDTO taskDTO;
	private TaskCreateDTO taskCreateDTO;

	@BeforeEach
	void setUp() {
		task = new Task(
				1L,
				"New Task",
				"Description of the new task",
				TaskStatus.CREATE,
				LocalDateTime.now(),
				LocalDateTime.now());

		taskDTO = new TaskDTO(
				1L,
				"New Task",
				"Description of the new task",
				TaskStatus.CREATE,
				null,
				null);

		taskCreateDTO = new TaskCreateDTO(
				"New Task",
				"Description of the new task");
	}


	@Test
	void create_shouldSaveTask() {
		when(taskMapper.toEntity(taskCreateDTO)).thenReturn(task);
		when(taskRepository.save(task)).thenReturn(task);
		when(taskMapper.toDto(task)).thenReturn(taskDTO);

		TaskDTO createdTaskDTO = taskService.create(taskCreateDTO);

		assertNotNull(createdTaskDTO);
		assertEquals(taskDTO.getName(), createdTaskDTO.getName());
		verify(taskMapper).toEntity(taskCreateDTO);
		verify(taskRepository).save(task);
		verify(taskMapper).toDto(task);
	}

	@Test
	void update_existingTask_shouldUpdateTask() {
		when(taskRepository.existsById(1L)).thenReturn(true);
		when(taskMapper.toEntity(taskDTO)).thenReturn(task);
		when(taskRepository.save(task)).thenReturn(task);
		when(taskMapper.toDto(task)).thenReturn(taskDTO);

		TaskDTO updatedTaskDTO = taskService.update(1L, taskDTO);

		assertNotNull(updatedTaskDTO);
		assertEquals(taskDTO.getId(), updatedTaskDTO.getId());
		verify(taskMapper).toEntity(taskDTO);
		verify(taskRepository).save(task);
		verify(taskMapper).toDto(task);
	}

	@Test
	void update_nonExistingTask_shouldThrowException() {
		when(taskRepository.existsById(1L)).thenReturn(false);

		assertThrows(ResourceNotFoundException.class, () -> taskService.update(1L, taskDTO));

		verify(taskRepository).existsById(1L);
	}

	@Test
	void getById_existingTask_shouldReturnTask() {
		when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
		when(taskMapper.toDto(task)).thenReturn(taskDTO);

		TaskDTO foundTaskDTO = taskService.getById(1L);

		assertNotNull(foundTaskDTO);
		assertEquals(taskDTO.getId(), foundTaskDTO.getId());
		verify(taskRepository).findById(1L);
		verify(taskMapper).toDto(task);
	}

	@Test
	void getById_nonExistingTask_shouldThrowException() {
		when(taskRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> taskService.getById(1L));

		verify(taskRepository).findById(1L);
	}

	@Test
	void getTasksInProgress_shouldReturnTasks() {
		List<Task> tasks = List.of(task);
		List<TaskDTO> taskDTOs = List.of(taskDTO);
		when(taskRepository.findAllByStatus(TaskStatus.IN_PROGRESS)).thenReturn(tasks);
		when(taskMapper.toDto(tasks)).thenReturn(taskDTOs);

		List<TaskDTO> tasksInProgress = taskService.getTasksInProgress(TaskStatus.IN_PROGRESS);

		assertNotNull(tasksInProgress);
		assertEquals(1, tasksInProgress.size());
		assertEquals(taskDTO.getStatus(), tasksInProgress.get(0).getStatus());
		verify(taskRepository).findAllByStatus(TaskStatus.IN_PROGRESS);
		verify(taskMapper).toDto(tasks);
	}
}