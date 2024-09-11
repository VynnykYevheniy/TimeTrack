package org.yvynnyk.timetrack.constant;

/**
 * Utility class that contains constants for logging messages used throughout the application.
 * <p>
 * This class is designed to provide a centralized location for logging message formats. It should not be instantiated,
 * and all fields should be declared as {@code public static final}.
 * </p>
 * <p>
 * Example usage:
 * <pre>
 *     logger.info(LoggingConstants.Task.Service.TASK_CREATED, taskName);
 * </pre>
 * </p>
 */
public final class LoggingConstants {

	/**
	 * Private constructor to prevent instantiation.
	 * <p>
	 * This constructor is private because this class should not be instantiated.
	 * Attempting to create an instance will result in an {@code UnsupportedOperationException}.
	 * </p>
	 */
	private LoggingConstants() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/**
	 * Constants for logging related to tasks.
	 */
	public static class Task {

		/**
		 * Constants for logging related to task controllers.
		 */
		public static class Controller {
			/**
			 * Log message indicating a request to create a task with a specific name.
			 */
			public static final String TASK_CREATE_REQUEST = "Received request to create task with name: %s";

			/**
			 * Log message indicating a request to update a task with a specific ID.
			 */
			public static final String TASK_UPDATE_REQUEST = "Received request to update task with id: %d";

			/**
			 * Log message indicating a request to fetch all tasks.
			 */
			public static final String TASK_GET_ALL = "Received request to fetch all tasks";
		}

		/**
		 * Constants for logging related to task services.
		 */
		public static class Service {
			/**
			 * Log message indicating a task is being created with a specific name.
			 */
			public static final String TASK_CREATED = "Creating task with name: %s";

			/**
			 * Log message indicating a task is being updated with a specific ID.
			 */
			public static final String TASK_UPDATED = "Updating task with id: %d";
		}
	}

	/**
	 * Constants for logging related to time entries.
	 */
	public static class TimeEntry {

		/**
		 * Constants for logging related to time entry controllers.
		 */
		public static class Controller {
			/**
			 * Log message indicating a request to start a time entry for a task with a specific ID.
			 */
			public static final String TIME_ENTRY_START_REQUEST = "Received request to start time entry for task with id: %d";

			/**
			 * Log message indicating a request to stop a time entry for a task with a specific ID.
			 */
			public static final String TIME_ENTRY_STOP_REQUEST = "Received request to stop time entry for task with id: %d";
		}

		/**
		 * Constants for logging related to time entry services.
		 */
		public static class Service {
			/**
			 * Log message indicating a time entry has started for a task with a specific ID.
			 */
			public static final String TIME_ENTRY_STARTED = "Time entry started for task with id: %d";

			/**
			 * Log message indicating a time entry has stopped for a task with a specific ID.
			 */
			public static final String TIME_ENTRY_STOPPED = "Time entry stopped for task with id: %d";

			/**
			 * Log message indicating that automatic task closure has started at a specific time.
			 */
			public static final String AUTOMATIC_TASK_CLOSURE_STARTED = "Automatic task closure started at: %s";

			/**
			 * Log message indicating that automatic task closure has been completed at a specific time.
			 */
			public static final String AUTOMATIC_TASK_CLOSURE_COMPLETED = "Automatic task closure completed at: %s";

			/**
			 * Log message indicating that a task with a specific ID has been automatically closed.
			 */
			public static final String TASK_AUTOMATICALLY_CLOSED = "Task with id %d automatically closed.";
		}
	}
}