package org.yvynnyk.timetrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yvynnyk.timetrack.constant.LoggingConstants;
import org.yvynnyk.timetrack.constant.UrlConstants;
import org.yvynnyk.timetrack.dto.TaskCreateDTO;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.service.TaskService;

import java.util.List;

import static org.yvynnyk.timetrack.constant.SwaggerConstants.*;
import static org.yvynnyk.timetrack.constant.SwaggerConstants.Task.*;
import static org.yvynnyk.timetrack.constant.UrlConstants.Task.TASK_ALL;
import static org.yvynnyk.timetrack.constant.UrlConstants.Task.TASK_BY_ID;

/**
 * Controller for managing tasks.
 * <p>
 * This controller provides endpoints for creating and updating tasks.
 * </p>
 */
@RestController
@RequestMapping(value = UrlConstants.Task.API)
public class TaskController {

	private static final Logger logger = LoggerFactory.getLogger(TaskController.class);

	private final TaskService taskService;

	/**
	 * Constructs a TaskController with the specified TaskService.
	 *
	 * @param taskService the service for managing tasks
	 */
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * Creates a new task.
	 *
	 * @param task the task to be created
	 * @return the created task with a 201 Created status code
	 */
	@Operation(summary = CREATE_TASK_SUMMARY,
			description = CREATE_TASK_DESCRIPTION)
	@ApiResponses(value = {
			@ApiResponse(responseCode = RESPONSE_CODE_201, description = CREATE_TASK_CREATED_DESCRIPTION,
					content = @Content(
							schema = @Schema(implementation = TaskDTO.class),
							examples = @ExampleObject(value = CREATE_TASK_EXAMPLE))),
			@ApiResponse(responseCode = RESPONSE_CODE_400, description = CREATE_TASK_INVALID_INPUT_DESCRIPTION, content = @Content)
	})
	@PostMapping
	public ResponseEntity<TaskDTO> create(@RequestBody TaskCreateDTO task) {
		logger.info(LoggingConstants.Task.Controller.TASK_CREATE_REQUEST, task.getName());
		TaskDTO createdTask = taskService.create(task);
		return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
	}

	/**
	 * Updates an existing task.
	 *
	 * @param taskId the ID of the task to be updated
	 * @param task   the new task data
	 * @return the updated task with a 200 OK status code
	 * @throws ResourceNotFoundException if the task is not found
	 */
	@Operation(summary = UPDATE_TASK_SUMMARY,
			description = UPDATE_TASK_DESCRIPTION)
	@ApiResponses(value = {
			@ApiResponse(responseCode = RESPONSE_CODE_200, description = UPDATE_TASK_UPDATED_DESCRIPTION,
					content = @Content(
							schema = @Schema(implementation = TaskDTO.class),
							examples = @ExampleObject(value = UPDATE_TASK_EXAMPLE))),
			@ApiResponse(responseCode = RESPONSE_CODE_400, description = UPDATE_TASK_INVALID_INPUT_DESCRIPTION, content = @Content),
			@ApiResponse(responseCode = RESPONSE_CODE_404, description = UPDATE_TASK_NOT_FOUND_DESCRIPTION, content = @Content)
	})
	@PutMapping(TASK_BY_ID)
	public ResponseEntity<TaskDTO> update(@PathVariable Long taskId, @RequestBody TaskDTO task) {
		logger.info(LoggingConstants.Task.Controller.TASK_UPDATE_REQUEST, taskId);
		TaskDTO updatedTask = taskService.update(taskId, task);
		return new ResponseEntity<>(updatedTask, HttpStatus.OK);
	}


	/**
	 * Retrieves all tasks.
	 *
	 * @return a list of TaskDTO objects with a 200 OK status code if tasks are found,
	 * or a 204 No Content status code if no tasks are available
	 */
	@Operation(summary = GET_ALL_TASKS_SUMMARY,
			description = GET_ALL_TASKS_DESCRIPTION)
	@ApiResponses(value = {
			@ApiResponse(responseCode = RESPONSE_CODE_200, description = GET_ALL_TASKS_OK_DESCRIPTION,
					content = @Content(
							schema = @Schema(implementation = TaskDTO.class),
							examples = @ExampleObject(value = GET_ALL_TASKS_EXAMPLE))),
			@ApiResponse(responseCode = RESPONSE_CODE_204, description = GET_ALL_TASKS_NO_CONTENT_DESCRIPTION, content = @Content)
	})
	@GetMapping(TASK_ALL)
	public ResponseEntity<List<TaskDTO>> getAll() {
		logger.info(LoggingConstants.Task.Controller.TASK_GET_ALL);
		List<TaskDTO> tasks = taskService.getAll();
		return tasks.isEmpty()
				? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(tasks, HttpStatus.OK);
	}

}