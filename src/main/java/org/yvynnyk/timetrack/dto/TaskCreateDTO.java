package org.yvynnyk.timetrack.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Data Transfer Object (DTO) for creating a new task.
 * This DTO is used to transfer the necessary information for creating a task,
 * such as the name and description, from the client to the server.
 *
 * <p> This class is annotated with Lombok's {@code @Data}, {@code @AllArgsConstructor},
 * and {@code @NoArgsConstructor} annotations to automatically generate getters, setters,
 * constructors, and other utility methods.
 *
 * <p> The {@code name} field is required, whereas the {@code description} field is optional.
 *
 * <p> Swagger annotations are used to document the API schema, indicating required fields
 * and providing example values.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "TaskCreateDTO", description = "DTO for creating a Task")
public class TaskCreateDTO {

	/**
	 * Name of the task.
	 * <p> This field is mandatory for creating a task and represents the name or title
	 * of the task.
	 * <p> The {@code @NonNull} annotation ensures that the name cannot be null.
	 */
	@Schema(description = "Name of the task", example = "My Task", requiredMode = Schema.RequiredMode.REQUIRED)
	@NonNull
	private String name;

	/**
	 * Description of the task.
	 * <p> This field is optional and provides additional details or explanations
	 * about the task.
	 */
	@Schema(description = "Description of the task", example = "This is a detailed description")
	private String description;
}