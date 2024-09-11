package org.yvynnyk.timetrack.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.yvynnyk.timetrack.dto.TaskCreateDTO;
import org.yvynnyk.timetrack.dto.TaskDTO;
import org.yvynnyk.timetrack.exception.ResourceNotFoundException;
import org.yvynnyk.timetrack.mapper.TaskMapper;
import org.yvynnyk.timetrack.model.Task;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;
import org.yvynnyk.timetrack.repository.TaskRepository;
import org.yvynnyk.timetrack.service.TaskService;

import java.util.List;

import static org.yvynnyk.timetrack.constant.ExceptionConstants.Task.TASK_NOT_FOUND_WITH_ID;
import static org.yvynnyk.timetrack.constant.LoggingConstants.Task.Service.TASK_CREATED;
import static org.yvynnyk.timetrack.constant.LoggingConstants.Task.Service.TASK_UPDATED;

/**
 * Implementation of {@link TaskService} that manages task persistence using a repository.
 * <p>
 * This class provides concrete implementations for task creation, updating, and retrieval
 * of tasks from a database using JPA.
 * </p>
 */
@Service
public class TaskServiceImpl implements TaskService {
	private static final Logger logger = LoggerFactory.getLogger(TaskServiceImpl.class);
	private final TaskRepository taskRepository;
	private final TaskMapper taskMapper;


	/**
	 * Constructs a new {@code TaskServiceImpl} with the given task repository.
	 *
	 * @param taskRepository the repository used for task persistence
	 */
	public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
		this.taskRepository = taskRepository;
		this.taskMapper = taskMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskDTO create(TaskCreateDTO taskCreateDTO) {
		Task task = taskMapper.toEntity(taskCreateDTO);
		task.setStatus(TaskStatus.CREATE);
		logger.info(TASK_CREATED.formatted(task.getName()));
		return taskMapper.toDto(taskRepository.save(task));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskDTO update(Long taskId, TaskDTO taskDTO) {
		Task task = taskMapper.toEntity(taskDTO);
		if (taskRepository.existsById(taskId)) {
			task.setId(taskId);
			logger.info(TASK_UPDATED.formatted(taskId));
			return taskMapper.toDto(taskRepository.save(task));
		} else {
			throw new ResourceNotFoundException(TASK_NOT_FOUND_WITH_ID.formatted(taskId));
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskDTO update(TaskDTO taskDTO) {
		return update(taskDTO.getId(), taskDTO);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskDTO getById(Long taskId) {
		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new ResourceNotFoundException(TASK_NOT_FOUND_WITH_ID.formatted(taskId)));
		return taskMapper.toDto(task);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TaskDTO> getTasksInProgress(TaskStatus status) {
		return taskMapper.toDto(taskRepository.findAllByStatus(status));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<TaskDTO> getAll() {
		return taskMapper.toDto(taskRepository.findAll());
	}
}