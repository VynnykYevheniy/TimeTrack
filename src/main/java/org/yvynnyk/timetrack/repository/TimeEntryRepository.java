package org.yvynnyk.timetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.yvynnyk.timetrack.model.TimeEntry;

import java.util.Optional;

/**
 * Repository interface for {@link TimeEntry} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations and
 * custom query methods for {@link TimeEntry} entities.</p>
 */
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {

	/**
	 * Finds a {@link TimeEntry} by its associated task ID where the end time is {@code null}.
	 *
	 * @param taskId the ID of the task associated with the time entry
	 * @return an {@link Optional} containing the {@link TimeEntry} if found, or {@code empty} if not found
	 */
	Optional<TimeEntry> findByTaskIdAndEndTimeIsNull(Long taskId);

	/**
	 * Finds a {@link TimeEntry} by its associated task ID.
	 *
	 * @param id the ID of the task associated with the time entry
	 * @return an {@link Optional} containing the {@link TimeEntry} if found, or {@code empty} if not found
	 */
	Optional<TimeEntry> findByTaskId(Long id);
}