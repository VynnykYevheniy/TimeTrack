package org.yvynnyk.timetrack.constant;

/**
 * Utility class that contains constants for exception messages used throughout the application.
 * <p>
 * This class is designed to provide a centralized location for exception message formats. It should not be instantiated,
 * and all fields should be declared as {@code public static final}.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     throw new ResourceNotFoundException(ExceptionConstants.Task.TASK_NOT_FOUND_WITH_ID.formatted(taskId));
 * </pre>
 * </p>
 */
public final class ExceptionConstants {

	/**
	 * Private constructor to prevent instantiation.
	 * <p>
	 * This constructor is private because this class should not be instantiated.
	 * Attempting to create an instance will result in an {@code UnsupportedOperationException}.
	 * </p>
	 */
	private ExceptionConstants() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/**
	 * Constants for exception messages related to tasks.
	 */
	public static class Task {
		/**
		 * Exception message indicating that a task was not found with a specific ID.
		 */
		public static final String TASK_NOT_FOUND_WITH_ID = "Task not found with id: %d";
	}

	/**
	 * Constants for exception messages related to time entries.
	 */
	public static class TimeEntry {
		/**
		 * Exception message indicating that a task is not in progress or is already completed.
		 */
		public static final String TASK_STATUS_ERROR = "Task is not in progress or already completed";

		/**
		 * Exception message indicating that an active time entry was not found for a task with a specific ID.
		 */
		public static final String ACTIVE_TIME_ENTRY_NOT_FOUND = "Active time entry not found for task ID: %d";

		/**
		 * Exception message indicating that a time entry was not found for a task with a specific ID.
		 */
		public static final String TIME_ENTRY_NOT_FOUND_FOR_TASK_ID = "Time entry not found for task id: %d";
	}
}