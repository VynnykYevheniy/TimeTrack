package org.yvynnyk.timetrack.service;

import org.yvynnyk.timetrack.dto.TaskCreateDTO;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;

import java.util.List;

/**
 * Service interface for managing tasks.
 * <p>
 * This interface defines the operations for creating, updating, retrieving, and
 * fetching tasks with a specific status.
 * </p>
 */
public interface TaskService {

	/**
	 * Creates a new task.
	 *
	 * @param task the task to be created
	 * @return the created task
	 */
	TaskDTO create(TaskCreateDTO task);

	/**
	 * Updates an existing task by its ID.
	 *
	 * @param taskId the ID of the task to update
	 * @param task   the updated task details
	 * @return the updated task, or {@code null} if the task with the given ID does not exist
	 */
	TaskDTO update(Long taskId, TaskDTO task);

	/**
	 * Updates an existing task using its ID from the task object.
	 *
	 * @param task the task to update
	 * @return the updated task
	 */
	TaskDTO update(TaskDTO task);

	/**
	 * Retrieves a task by its ID.
	 *
	 * @param taskId the ID of the task to retrieve
	 * @return the task with the specified ID
	 * @throws RuntimeException if no task with the given ID is found
	 */
	TaskDTO getById(Long taskId);

	/**
	 * Retrieves a list of tasks that are in progress, filtered by their status.
	 *
	 * @param status the status of tasks to filter by
	 * @return a list of tasks with the given status
	 */
	List<TaskDTO> getTasksInProgress(TaskStatus status);

	/**
	 * Retrieves a list of all tasks.
	 *
	 * @return a list containing all tasks
	 */
	List<TaskDTO> getAll();
}