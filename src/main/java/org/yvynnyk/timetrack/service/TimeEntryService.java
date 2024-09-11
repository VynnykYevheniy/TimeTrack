package org.yvynnyk.timetrack.service;

import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.model.TimeEntry;

/**
 * Service interface for managing time entries and tasks.
 * <p>
 * This interface defines the operations for starting, stopping, and automatically
 * closing tasks based on time entries.
 * </p>
 */
public interface TimeEntryService {

	/**
	 * Starts a time entry for the given task.
	 * <p>
	 * The task's status will be updated to {@code IN_PROGRESS} if it is currently
	 * in the {@code CREATE} or {@code PENDING} state. A new {@link TimeEntry} will be
	 * created and associated with the task. Throws {@link IllegalStateException} if
	 * the task is already in progress or completed. Throws {@link ResourceNotFoundException}
	 * if the task with the specified ID does not exist.
	 * </p>
	 *
	 * @param taskId the ID of the task to start
	 * @throws IllegalStateException     if the task cannot be started due to its current status
	 * @throws ResourceNotFoundException if the task with the specified ID does not exist
	 */
	void start(Long taskId);

	/**
	 * Stops the current time entry for the given task.
	 * <p>
	 * The task's status will be updated to {@code COMPLETED}, and the associated
	 * {@link TimeEntry} will have its end time set. Throws {@link IllegalStateException}
	 * if the task is not in progress. Throws {@link ResourceNotFoundException} if the
	 * task with the specified ID does not exist.
	 * </p>
	 *
	 * @param taskId the ID of the task to stop
	 * @throws IllegalStateException     if the task cannot be stopped due to its current status
	 * @throws ResourceNotFoundException if the task with the specified ID does not exist
	 */
	void stop(Long taskId);

	/**
	 * Automatically closes tasks that are still in progress at the end of the day.
	 * <p>
	 * This method is triggered by a scheduled task and checks for tasks with the
	 * status {@code IN_PROGRESS}. It then stops the task if it was started today.
	 * </p>
	 */
	void closeTasksAutomatically();
}