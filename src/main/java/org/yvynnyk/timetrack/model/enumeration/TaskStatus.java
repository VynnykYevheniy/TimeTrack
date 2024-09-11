package org.yvynnyk.timetrack.model.enumeration;

/**
 * Enum representing the various statuses a task can have.
 *
 * <p>This enum defines the different stages a task can go through during its
 * lifecycle, from creation to completion.</p>
 */
public enum TaskStatus {

	/**
	 * The task has been created but not yet started or assigned.
	 */
	CREATE,

	/**
	 * The task is pending and waiting to be processed or started.
	 */
	PENDING,

	/**
	 * The task is currently in progress and being worked on.
	 */
	IN_PROGRESS,

	/**
	 * The task has been completed successfully.
	 */
	COMPLETED
}