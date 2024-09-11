package org.yvynnyk.timetrack.constant;

/**
 * Utility class that contains URL constants used for defining API endpoints in the application.
 * <p>
 * This class provides a centralized location for storing the URL patterns used in the application's REST APIs.
 * It should not be instantiated, and all fields should be declared as {@code public static final}.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     @GetMapping(UrlConstants.Task.API + UrlConstants.Task.TASK_ALL)
 *     public ResponseEntity<List<TaskDTO>> getAllTasks() {
 *         // Implementation
 *     }
 * </pre>
 * </p>
 */
public final class UrlConstants {

	/**
	 * Private constructor to prevent instantiation.
	 * <p>
	 * This constructor is private because this class should not be instantiated.
	 * Attempting to create an instance will result in an {@code UnsupportedOperationException}.
	 * </p>
	 */
	private UrlConstants() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/**
	 * Constants for URL patterns related to tasks.
	 */
	public static class Task {
		/**
		 * Base URL for task-related API endpoints.
		 */
		public static final String API = "api/tasks";

		/**
		 * URL pattern for retrieving all tasks.
		 */
		public static final String TASK_ALL = "/all";

		/**
		 * URL pattern for retrieving a specific task by its ID.
		 * <p>
		 * Placeholder for the task ID: {@code {taskId}}.
		 * </p>
		 */
		public static final String TASK_BY_ID = "/{taskId}";
	}

	/**
	 * Constants for URL patterns related to time entries.
	 */
	public static class TimeEntry {
		/**
		 * Base URL for time entry-related API endpoints.
		 */
		public static final String API = "api/timeEntry";

		/**
		 * URL pattern for starting a time entry.
		 */
		public static final String START = "/start";

		/**
		 * URL pattern for stopping a time entry for a specific task.
		 * <p>
		 * Placeholder for the task ID: {@code {taskId}}.
		 * </p>
		 */
		public static final String STOP = "/{taskId}/stop";
	}
}