package org.yvynnyk.timetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yvynnyk.timetrack.model.Task;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;

import java.util.List;

/**
 * Repository interface for {@link Task} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations and
 * custom query methods for {@link Task} entities.</p>
 */
public interface TaskRepository extends JpaRepository<Task, Long> {

	/**
	 * Retrieves all {@link Task} entities with the specified status.
	 *
	 * @param status the status of the tasks to retrieve
	 * @return a list of {@link Task} entities with the given status
	 */
	List<Task> findAllByStatus(TaskStatus status);
}