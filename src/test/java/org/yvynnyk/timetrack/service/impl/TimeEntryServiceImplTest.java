package org.yvynnyk.timetrack.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.model.Task;
import org.yvynnyk.timetrack.model.TimeEntry;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;
import org.yvynnyk.timetrack.repository.TimeEntryRepository;
import org.yvynnyk.timetrack.service.TaskService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.yvynnyk.timetrack.constant.ExceptionConstants.TimeEntry.TASK_STATUS_ERROR;

@ExtendWith(MockitoExtension.class)
class TimeEntryServiceImplTest {

	@Mock
	private TimeEntryRepository timeEntryRepository;

	@Mock
	private TaskService taskService;

	@InjectMocks
	private TimeEntryServiceImpl timeEntryService;

	private Task task;
	private TimeEntry timeEntry;
	private TaskDTO taskDTO;

	@BeforeEach
	void setUp() {
		task = new Task(
				1L,
				"New Task",
				"Description of the new task",
				TaskStatus.CREATE,
				LocalDateTime.now(),
				LocalDateTime.now());
		timeEntry = new TimeEntry(
				1L,
				task,
				LocalDateTime.now(),
				LocalDateTime.now().plusHours(1L));

		taskDTO = new TaskDTO(
				1L,
				"New Task",
				"Description of the new task",
				TaskStatus.CREATE,
				null,
				null);
	}

	@Test
	void start_success() {
		when(taskService.getById(1L)).thenReturn(taskDTO);
		when(timeEntryRepository.save(any(TimeEntry.class))).thenReturn(timeEntry);

		timeEntryService.start(1L);

		verify(taskService, times(1)).getById(1L);
		verify(timeEntryRepository, times(1)).save(any(TimeEntry.class));
		assertEquals(TaskStatus.IN_PROGRESS, taskDTO.getStatus());
	}

	@Test
	void start_taskAlreadyInProgress() {
		taskDTO.setStatus(TaskStatus.IN_PROGRESS);
		when(taskService.getById(1L)).thenReturn(taskDTO);

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> timeEntryService.start(1L));

		assertEquals(TASK_STATUS_ERROR, thrown.getMessage());
		verify(taskService, times(1)).getById(1L);
		verify(timeEntryRepository, never()).save(any(TimeEntry.class));
	}

	@Test
	void start_taskNotFound() {
		taskDTO.setStatus(TaskStatus.COMPLETED);
		when(taskService.getById(1L)).thenReturn(taskDTO);

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> timeEntryService.start(1L));

		assertEquals(TASK_STATUS_ERROR, thrown.getMessage());

		verify(taskService, times(1)).getById(1L);

		verify(timeEntryRepository, never()).save(any(TimeEntry.class));
	}


	@Test
	void stop_success() {
		taskDTO.setStatus(TaskStatus.IN_PROGRESS);
		when(taskService.getById(1L)).thenReturn(taskDTO);
		when(timeEntryRepository.findByTaskIdAndEndTimeIsNull(1L)).thenReturn(Optional.of(timeEntry));

		timeEntryService.stop(1L);

		verify(taskService, times(1)).getById(1L);
		verify(timeEntryRepository, times(1)).findByTaskIdAndEndTimeIsNull(1L);
		verify(timeEntryRepository, times(1)).save(timeEntry);
		assertEquals(TaskStatus.COMPLETED, taskDTO.getStatus());
		assertNotNull(timeEntry.getEndTime());
	}

	@Test
	void stop_taskNotInProgress() {
		taskDTO.setStatus(TaskStatus.CREATE);
		when(taskService.getById(1L)).thenReturn(taskDTO);

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> timeEntryService.stop(1L));

		assertEquals(TASK_STATUS_ERROR, thrown.getMessage());
		verify(taskService, times(1)).getById(1L);
		verify(timeEntryRepository, never()).findByTaskIdAndEndTimeIsNull(1L);
		verify(timeEntryRepository, never()).save(any(TimeEntry.class));
	}

	@Test
	void stop_taskNotFound() {
		when(taskService.getById(1L)).thenReturn(taskDTO);

		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> timeEntryService.stop(1L));

		assertEquals(TASK_STATUS_ERROR, thrown.getMessage());
		verify(taskService, times(1)).getById(1L);
		verify(timeEntryRepository, never()).save(any(TimeEntry.class));
	}

	@Test
	void closeTasksAutomatically_noTasksInProgress() {
		when(taskService.getTasksInProgress(TaskStatus.IN_PROGRESS)).thenReturn(Collections.emptyList());

		timeEntryService.closeTasksAutomatically();

		verify(taskService, times(1)).getTasksInProgress(TaskStatus.IN_PROGRESS);

		verify(timeEntryRepository, never()).findByTaskId(anyLong());
	}

	@Test
	void closeTasksAutomatically_taskStartedToday() {
		task.setStatus(TaskStatus.IN_PROGRESS);
		taskDTO.setStatus(TaskStatus.IN_PROGRESS);
		when(taskService.getTasksInProgress(TaskStatus.IN_PROGRESS)).thenReturn(List.of(taskDTO));
		when(timeEntryRepository.findByTaskId(taskDTO.getId())).thenReturn(Optional.of(timeEntry));
		when(timeEntryRepository.findByTaskIdAndEndTimeIsNull(task.getId())).thenReturn(Optional.of(timeEntry));
		when(taskService.getById(task.getId())).thenReturn(taskDTO);

		timeEntryService.closeTasksAutomatically();

		verify(taskService, times(1)).getTasksInProgress(TaskStatus.IN_PROGRESS);
		verify(timeEntryRepository, times(1)).findByTaskId(taskDTO.getId());
		assertEquals(TaskStatus.COMPLETED, taskDTO.getStatus());
		assertNotNull(timeEntry.getEndTime());
	}
}