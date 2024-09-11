package org.yvynnyk.timetrack.mapper;

import org.springframework.stereotype.Component;
import org.yvynnyk.timetrack.dto.TaskCreateDTO;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.model.Task;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper class for converting between Task entities and TaskDTOs.
 *
 * <p>This class provides methods to transform {@link Task} entities into their
 * respective {@link TaskDTO} representations and vice versa. It supports
 * conversions for both single objects and lists of entities or DTOs. The
 * methods handle cases where the input is {@code null} or empty, ensuring that
 * the application handles conversions gracefully.</p>
 *
 * <p>This class is annotated with {@link Component} to indicate that it is a
 * Spring-managed bean and can be injected into other components.</p>
 */
@Component
public class TaskMapper {

	/**
	 * Converts a Task entity to a TaskDTO.
	 *
	 * <p>This method converts a {@link Task} entity to its corresponding
	 * {@link TaskDTO} object. If the input {@code task} is {@code null}, the method
	 * returns {@code null}.</p>
	 *
	 * @param task the {@link Task} entity to convert
	 * @return the corresponding {@link TaskDTO}, or {@code null} if the task is {@code null}
	 */
	public TaskDTO toDto(Task task) {
		if (task == null) {
			return null;
		}
		return new TaskDTO(
				task.getId(),
				task.getName(),
				task.getDescription(),
				task.getStatus(),
				task.getCreatedAt(),
				task.getUpdatedAt()
		);
	}

	/**
	 * Converts a list of Task entities to a list of TaskDTOs.
	 *
	 * <p>This method converts a list of {@link Task} entities into a list of
	 * corresponding {@link TaskDTO} objects. If the input list is {@code null} or
	 * empty, an empty list is returned.</p>
	 *
	 * @param tasks the list of {@link Task} entities to convert
	 * @return a list of corresponding {@link TaskDTO}s, or an empty list if the input list is {@code null} or empty
	 */
	public List<TaskDTO> toDto(List<Task> tasks) {
		if (tasks == null || tasks.isEmpty()) {
			return Collections.emptyList();
		}
		return tasks.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	/**
	 * Converts a TaskCreateDTO to a Task entity.
	 *
	 * <p>This method converts a {@link TaskCreateDTO} into a new {@link Task} entity.
	 * If the input {@code taskCreateDTO} is {@code null}, the method returns
	 * {@code null}.</p>
	 *
	 * @param taskCreateDTO the {@link TaskCreateDTO} to convert
	 * @return the corresponding {@link Task} entity, or {@code null} if the taskCreateDTO is {@code null}
	 */
	public Task toEntity(TaskCreateDTO taskCreateDTO) {
		if (taskCreateDTO == null) {
			return null;
		}
		Task task = new Task();
		task.setName(taskCreateDTO.getName());
		task.setDescription(taskCreateDTO.getDescription());
		return task;
	}

	/**
	 * Converts a TaskDTO to a Task entity.
	 *
	 * <p>This method transforms a {@link TaskDTO} object into its corresponding
	 * {@link Task} entity. If the provided {@code taskDTO} is {@code null}, the
	 * method will return {@code null}.</p>
	 *
	 * @param taskDTO the {@link TaskDTO} to convert
	 * @return the corresponding {@link Task} entity, or {@code null} if the taskDTO is {@code null}
	 */
	public Task toEntity(TaskDTO taskDTO) {
		if (taskDTO == null) {
			return null;
		}
		return new Task(
				taskDTO.getId(),
				taskDTO.getName(),
				taskDTO.getDescription(),
				taskDTO.getStatus(),
				taskDTO.getCreatedAt(),
				taskDTO.getUpdatedAt()
		);
	}

	/**
	 * Converts a list of TaskCreateDTOs to a list of Task entities.
	 *
	 * <p>This method converts a list of {@link TaskCreateDTO} objects into a list of
	 * corresponding {@link Task} entities. If the input list is {@code null} or empty,
	 * an empty list is returned.</p>
	 *
	 * @param taskCreateDTOs the list of {@link TaskCreateDTO}s to convert
	 * @return a list of corresponding {@link Task} entities, or an empty list if the input list is {@code null} or empty
	 */
	public List<Task> toEntity(List<TaskCreateDTO> taskCreateDTOs) {
		if (taskCreateDTOs == null || taskCreateDTOs.isEmpty()) {
			return Collections.emptyList();
		}
		return taskCreateDTOs.stream()
				.map(this::toEntity)
				.collect(Collectors.toList());
	}
}