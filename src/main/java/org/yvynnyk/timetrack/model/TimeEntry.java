package org.yvynnyk.timetrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a time entry for a task in the system.
 *
 * <p>This entity is used to record the start and end times associated with a task.
 * It is mapped to a table in the database with the appropriate annotations for entity
 * lifecycle management.</p>
 *
 * <p>The {@code TimeEntry} entity includes fields for tracking the task, start time,
 * and optionally an end time.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "time_entry")
public class TimeEntry {

	/**
	 * Unique identifier for the time entry.
	 * Generated automatically using the {@link GenerationType#IDENTITY} strategy.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * The associated task for which this time entry is recorded.
	 * This field is required and cannot be {@code null}.
	 */
	@OneToOne
	@JoinColumn(name = "task_id", nullable = false)
	private Task task;

	/**
	 * The timestamp when the time entry started.
	 * This field is required and cannot be {@code null}.
	 */
	@Column(name = "start_time", nullable = false)
	private LocalDateTime startTime;

	/**
	 * The timestamp when the time entry ended.
	 * This field is optional and can be {@code null}.
	 */
	@Column(name = "end_time")
	private LocalDateTime endTime;
}