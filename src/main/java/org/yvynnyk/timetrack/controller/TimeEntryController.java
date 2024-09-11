package org.yvynnyk.timetrack.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yvynnyk.timetrack.constant.UrlConstants;
import org.yvynnyk.timetrack.exception.GlobalExceptionHandler;
import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.service.TimeEntryService;

import static org.yvynnyk.timetrack.constant.LoggingConstants.TimeEntry.Controller.TIME_ENTRY_START_REQUEST;
import static org.yvynnyk.timetrack.constant.LoggingConstants.TimeEntry.Controller.TIME_ENTRY_STOP_REQUEST;
import static org.yvynnyk.timetrack.constant.SwaggerConstants.*;
import static org.yvynnyk.timetrack.constant.SwaggerConstants.TimeEntry.*;

/**
 * Controller for managing time entries.
 * <p>
 * This controller provides endpoints for starting and stopping time entries for tasks.
 * It handles exceptions globally through {@link GlobalExceptionHandler}.
 * </p>
 */
@RestController
@RequestMapping(value = UrlConstants.TimeEntry.API)
public class TimeEntryController {

	private static final Logger logger = LoggerFactory.getLogger(TimeEntryController.class);

	private final TimeEntryService timeEntryService;

	/**
	 * Constructs a TimeEntryController with the specified TimeEntryService.
	 *
	 * @param timeEntryService the service for managing time entries
	 */
	public TimeEntryController(TimeEntryService timeEntryService) {
		this.timeEntryService = timeEntryService;
	}

	/**
	 * Starts a time entry for a specified task.
	 *
	 * @param taskId the ID of the task to start time entry for
	 * @return a response with a 204 No Content status code if successful
	 * @throws ResourceNotFoundException if the task with the given ID does not exist
	 * @throws IllegalStateException     if the task cannot be started due to its current status
	 */
	@Operation(summary = START_TIME_ENTRY_SUMMARY,
			description = START_TIME_ENTRY_DESCRIPTION)
	@ApiResponses(value = {
			@ApiResponse(responseCode = RESPONSE_CODE_204, description = TIME_ENTRY_STARTED_SUCCESSFULLY, content = @Content(
					examples = @ExampleObject(value = TIME_ENTRY_STARTED_SUCCESSFULLY))),
			@ApiResponse(responseCode = RESPONSE_CODE_400, description = INVALID_STATE_FOR_STARTING_TIME_ENTRY, content = @Content(
					examples = @ExampleObject(value = TASK_ALREADY_IN_PROGRESS_OR_COMPLETED))),
			@ApiResponse(responseCode = RESPONSE_CODE_404, description = TASK_NOT_FOUND, content = @Content(
					examples = @ExampleObject(value = TASK_NOT_FOUND_WITH_ID)))
	})
	@PostMapping(UrlConstants.TimeEntry.START)
	public ResponseEntity<Void> start(@RequestBody Long taskId) {
		logger.info(TIME_ENTRY_START_REQUEST.formatted(taskId));
		timeEntryService.start(taskId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Stops a time entry for a specified task.
	 *
	 * @param taskId the ID of the task to stop time entry for
	 * @return a response with a 204 No Content status code if successful
	 * @throws ResourceNotFoundException if the task with the given ID does not exist
	 * @throws IllegalStateException     if the task cannot be stopped due to its current status
	 */
	@Operation(summary = STOP_TIME_ENTRY_SUMMARY,
			description = STOP_TIME_ENTRY_DESCRIPTION)
	@ApiResponses(value = {
			@ApiResponse(responseCode = RESPONSE_CODE_204, description = TIME_ENTRY_STOPPED_SUCCESSFULLY, content = @Content(
					examples = @ExampleObject(value = TIME_ENTRY_STARTED_SUCCESSFULLY))),
			@ApiResponse(responseCode = RESPONSE_CODE_400, description = INVALID_STATE_FOR_STOPPING_TIME_ENTRY, content = @Content(
					examples = @ExampleObject(value = TASK_ALREADY_IN_PROGRESS_OR_COMPLETED))),
			@ApiResponse(responseCode = RESPONSE_CODE_404, description = TASK_NOT_IN_PROGRESS, content = @Content(
					examples = @ExampleObject(value = TASK_NOT_FOUND_WITH_ID)))
	})
	@PostMapping(UrlConstants.TimeEntry.STOP)
	public ResponseEntity<Void> stop(@PathVariable Long taskId) {
		logger.info(TIME_ENTRY_STOP_REQUEST.formatted(taskId));
		timeEntryService.stop(taskId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}