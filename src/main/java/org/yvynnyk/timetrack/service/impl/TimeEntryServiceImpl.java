package org.yvynnyk.timetrack.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.mapper.TaskMapper;
import org.yvynnyk.timetrack.model.TimeEntry;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;
import org.yvynnyk.timetrack.repository.TimeEntryRepository;
import org.yvynnyk.timetrack.service.TaskService;
import org.yvynnyk.timetrack.service.TimeEntryService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.yvynnyk.timetrack.constant.ExceptionConstants.TimeEntry.*;
import static org.yvynnyk.timetrack.constant.LoggingConstants.TimeEntry.Service.*;

/**
 * Implementation of {@link TimeEntryService} that manages time entries for tasks.
 * <p>
 * This class provides functionality to start and stop time entries for tasks and
 * automatically close tasks that remain in progress at the end of the day. It uses
 * a task repository and time entry repository to persist and update data.
 * </p>
 */
@Service
public class TimeEntryServiceImpl implements TimeEntryService {
	private static final Logger logger = LoggerFactory.getLogger(TimeEntryServiceImpl.class);
	private final TimeEntryRepository timeEntryRepository;
	private final TaskService taskService;
	private final TaskMapper taskMapper;

	/**
	 * Constructs a new {@code TimeEntryServiceImpl} with the given repositories.
	 *
	 * @param timeEntryRepository the repository used for time entry persistence
	 * @param taskService         the service used to manage tasks
	 */
	public TimeEntryServiceImpl(TimeEntryRepository timeEntryRepository, TaskService taskService, TaskMapper taskMapper) {
		this.timeEntryRepository = timeEntryRepository;
		this.taskService = taskService;
		this.taskMapper = taskMapper;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Starts a time entry for the specified task. If the task's status is {@code CREATE}
	 * or {@code PENDING}, it updates the status to {@code IN_PROGRESS} and creates a
	 * new time entry. Throws {@link IllegalStateException} if the task is already in progress
	 * or completed. Throws {@link ResourceNotFoundException} if the task does not exist.
	 * </p>
	 */
	@Override
	public void start(Long taskId) {
		TaskDTO task = taskService.getById(taskId);
		if (task.getStatus() == TaskStatus.CREATE || task.getStatus() == TaskStatus.PENDING) {
			updateTaskStatus(task, TaskStatus.IN_PROGRESS);
			createAndSaveTimeEntry(task);
			logger.info(TIME_ENTRY_STARTED.formatted(task.getId()));
		} else {
			throw new IllegalStateException(TASK_STATUS_ERROR);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Stops the time entry for the specified task. If the task's status is {@code IN_PROGRESS},
	 * it updates the status to {@code COMPLETED} and sets the end time of the time entry.
	 * Throws {@link IllegalStateException} if the task is not in progress. Throws
	 * {@link ResourceNotFoundException} if the task does not exist.
	 * </p>
	 */
	@Override
	public void stop(Long taskId) {
		TaskDTO task = taskService.getById(taskId);
		if (task.getStatus() == TaskStatus.IN_PROGRESS) {
			updateTaskStatus(task, TaskStatus.COMPLETED);
			updateTimeEntryEndTime(taskId);
			logger.info(TIME_ENTRY_STOPPED.formatted(taskId));
		} else {
			throw new IllegalStateException(TASK_STATUS_ERROR);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Automatically closes tasks that are still in progress at the end of the day.
	 * This method is triggered by a scheduled task and processes tasks with the status
	 * {@code IN_PROGRESS} that were started today.
	 * </p>
	 */
	@Override
	@Scheduled(cron = "59 59 23 * * *")
	public void closeTasksAutomatically() {
		LocalDate today = LocalDate.now();
		logger.info(AUTOMATIC_TASK_CLOSURE_STARTED.formatted(LocalDateTime.now()));
		List<TaskDTO> tasks = taskService.getTasksInProgress(TaskStatus.IN_PROGRESS);
		tasks.forEach(task -> processTask(task, today));
		logger.info(AUTOMATIC_TASK_CLOSURE_COMPLETED.formatted(LocalDateTime.now()));
	}

	/**
	 * Updates the status of the given task to the specified status.
	 *
	 * @param task      the task to update
	 * @param newStatus the new status for the task
	 */
	private void updateTaskStatus(TaskDTO task, TaskStatus newStatus) {
		task.setStatus(newStatus);
		taskService.update(task);
	}

	/**
	 * Creates and saves a new time entry for the given task.
	 *
	 * @param task the task for which to create the time entry
	 */
	private void createAndSaveTimeEntry(TaskDTO task) {
		TimeEntry timeEntry = new TimeEntry();
		timeEntry.setTask(taskMapper.toEntity(task));
		timeEntry.setStartTime(LocalDateTime.now());
		timeEntryRepository.save(timeEntry);
	}

	/**
	 * Updates the end time of the time entry associated with the given task ID.
	 *
	 * @param taskId the ID of the task for which to update the time entry
	 * @throws RuntimeException if no active time entry is found for the task
	 */
	private void updateTimeEntryEndTime(Long taskId) {
		TimeEntry timeEntry = timeEntryRepository.findByTaskIdAndEndTimeIsNull(taskId)
				.orElseThrow(() -> new RuntimeException(ACTIVE_TIME_ENTRY_NOT_FOUND.formatted(taskId)));
		timeEntry.setEndTime(LocalDateTime.now());
		timeEntryRepository.save(timeEntry);
	}

	/**
	 * Processes the task and closes it if it was started today.
	 *
	 * @param task  the task to process
	 * @param today the current date
	 */
	private void processTask(TaskDTO task, LocalDate today) {
		TimeEntry timeEntries = timeEntryRepository.findByTaskId(task.getId())
				.orElseThrow(() -> new RuntimeException(TIME_ENTRY_NOT_FOUND_FOR_TASK_ID.formatted(task.getId())));
		boolean taskStartedToday = timeEntries.getStartTime().toLocalDate().isEqual(today);
		if (timeEntries.getTask().getStatus() == TaskStatus.IN_PROGRESS && taskStartedToday) {
			stop(task.getId());
			logger.info(TASK_AUTOMATICALLY_CLOSED.formatted(task.getId()));
		}
	}
}