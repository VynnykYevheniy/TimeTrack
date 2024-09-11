package org.yvynnyk.timetrack.constant;

public final class SwaggerConstants {
	private SwaggerConstants() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	public static final String RESPONSE_CODE_200 = "200";
	public static final String RESPONSE_CODE_201 = "201";
	public static final String RESPONSE_CODE_204 = "204";
	public static final String RESPONSE_CODE_400 = "400";
	public static final String RESPONSE_CODE_404 = "404";

	public static class Task {
		public static final String CREATE_TASK_SUMMARY = "Create a new task";
		public static final String CREATE_TASK_DESCRIPTION = "Creates a new task and returns the created task";
		public static final String CREATE_TASK_CREATED_DESCRIPTION = "Task created";
		public static final String CREATE_TASK_INVALID_INPUT_DESCRIPTION = "Invalid input";
		public static final String CREATE_TASK_EXAMPLE =
				"""
						{
						  "name": "Create Task",
						  "description": "This is an updated task description"
						}
						""";

		public static final String UPDATE_TASK_SUMMARY = "Update an existing task";
		public static final String UPDATE_TASK_DESCRIPTION = "Updates an existing task based on the provided task ID and returns the updated task";
		public static final String UPDATE_TASK_UPDATED_DESCRIPTION = "Task updated";
		public static final String UPDATE_TASK_INVALID_INPUT_DESCRIPTION = "Invalid input";
		public static final String UPDATE_TASK_NOT_FOUND_DESCRIPTION = "Task not found";
		public static final String UPDATE_TASK_EXAMPLE =
				"""
						{
						  "id": 1,
						  "name": "Updated Task",
						  "description": "This is an updated task description",
						  "status": "COMPLETED",
						  "updatedAt": "2024-09-10T11:00:00"
						}
						""";

		public static final String GET_ALL_TASKS_SUMMARY = "Retrieve all tasks";
		public static final String GET_ALL_TASKS_DESCRIPTION = "Fetch a list of all tasks.";
		public static final String GET_ALL_TASKS_OK_DESCRIPTION = "Successfully retrieved the list of tasks.";
		public static final String GET_ALL_TASKS_NO_CONTENT_DESCRIPTION = "No tasks found in the system.";
		public static final String GET_ALL_TASKS_EXAMPLE =
				"""
						[
						  {
						    "id": 1,
						    "name": "Task 1",
						    "description": "This is a detailed description for Task 1",
						    "status": "IN_PROGRESS",
						    "createdAt": "2024-09-10T12:34:56",
						    "updatedAt": "2024-09-10T12:34:56"
						  },
						  {
						    "id": 2,
						    "name": "Task 2",
						    "description": "This is a detailed description for Task 2",
						    "status": "COMPLETED",
						    "createdAt": "2024-09-10T12:00:00",
						    "updatedAt": "2024-09-10T14:00:00"
						  }
						]
						    
						""";

	}

	public static class TimeEntry {
		public static final String START_TIME_ENTRY_SUMMARY = "Start a time entry";
		public static final String START_TIME_ENTRY_DESCRIPTION = "Starts a time entry for the task with the specified ID. Throws an exception if the task is not found or cannot be started.";
		public static final String TIME_ENTRY_STARTED_SUCCESSFULLY = "Time entry started successfully";
		public static final String INVALID_STATE_FOR_STARTING_TIME_ENTRY = "Invalid state for starting time entry";
		public static final String TASK_ALREADY_IN_PROGRESS_OR_COMPLETED = "Task is already in progress or completed";
		public static final String TASK_NOT_FOUND = "Task not found";
		public static final String TASK_NOT_FOUND_WITH_ID = "Task not found with the provided ID";

		public static final String STOP_TIME_ENTRY_SUMMARY = "Stop a time entry";
		public static final String STOP_TIME_ENTRY_DESCRIPTION = "Stops the time entry for the specified task ID";

		public static final String TIME_ENTRY_STOPPED_SUCCESSFULLY = "Time entry stopped successfully";
		public static final String INVALID_STATE_FOR_STOPPING_TIME_ENTRY = "Invalid state for stopping time entry";
		public static final String TASK_NOT_IN_PROGRESS = "Task is not in progress";
	}
}