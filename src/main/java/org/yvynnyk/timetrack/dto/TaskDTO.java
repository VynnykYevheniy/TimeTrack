package org.yvynnyk.timetrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for representing task information.
 * This DTO is used to return the details of a task, including its ID, name, description,
 * status, creation timestamp, and last update timestamp.
 *
 * <p> This class is annotated with Lombok's {@code @Data}, {@code @AllArgsConstructor},
 * and {@code @NoArgsConstructor} annotations to automatically generate getters, setters,
 * constructors, and other utility methods.
 *
 * <p> Fields such as {@code id}, {@code createdAt}, and {@code updatedAt} are read-only
 * and are set via the {@code @Setter(AccessLevel.NONE)} annotation, meaning they cannot
 * be modified externally.
 *
 * <p> The {@code TaskStatus} field defines the current status of the task.
 *
 * <p> Swagger annotations are used to describe the schema for API documentation purposes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TaskDTO", description = "DTO for returning a Task")
public class TaskDTO {

	/**
	 * Unique identifier of the task.
	 * <p> This field is read-only and cannot be modified externally.
	 */
	@Getter
	@Setter(AccessLevel.NONE)
	@Schema(description = "Unique identifier of the task", example = "1")
	private Long id;

	/**
	 * Name of the task.
	 * <p> This field is mandatory and represents the name or title of the task.
	 */
	@Schema(description = "Name of the task", example = "My Task")
	@NonNull
	private String name;

	/**
	 * Description of the task.
	 * <p> This field provides a detailed description of what the task is about.
	 */
	@Schema(description = "Description of the task", example = "This is a detailed description")
	private String description;

	/**
	 * Current status of the task.
	 * <p> The status could be values like {@code IN_PROGRESS}, {@code COMPLETED}, etc.,
	 * represented by the {@code TaskStatus} enum.
	 */
	@Schema(description = "Status of the task", example = "IN_PROGRESS")
	private TaskStatus status;

	/**
	 * The timestamp when the task was created.
	 * <p> This field is automatically set when the task is created and is read-only.
	 */
	@Getter
	@Setter(AccessLevel.NONE)
	@Schema(description = "Creation timestamp of the task", example = "2024-09-10T12:34:56")
	private LocalDateTime createdAt;

	/**
	 * The timestamp when the task was last updated.
	 * <p> This field is automatically updated whenever the task is modified and is read-only.
	 */
	@Getter
	@Setter(AccessLevel.NONE)
	@Schema(description = "Last update timestamp of the task", example = "2024-09-10T12:34:56")
	private LocalDateTime updatedAt;
}