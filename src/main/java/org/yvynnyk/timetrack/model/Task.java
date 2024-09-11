package org.yvynnyk.timetrack.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yvynnyk.timetrack.model.enumeration.TaskStatus;

import java.time.LocalDateTime;

/**
 * Represents a task entity in the system.
 *
 * <p>This entity is used to store task details such as name, description, status,
 * and timestamps for creation and last update. It is mapped to a table in the database
 * with the appropriate annotations for entity lifecycle management.</p>
 *
 * <p>The {@code Task} entity includes automatic timestamping for both creation
 * and update events via the {@code @PrePersist} and {@code @PreUpdate} annotations.</p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {

	/**
	 * Unique identifier for the task.
	 * Generated automatically using the {@link GenerationType#IDENTITY} strategy.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Name of the task.
	 * This field is required and cannot be {@code null}.
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * Description of the task.
	 * This field is optional and can be {@code null}.
	 */
	@Column
	private String description;

	/**
	 * Status of the task, represented by the {@link TaskStatus} enum.
	 * This field is required and is stored as a string in the database.
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TaskStatus status;

	/**
	 * The timestamp when the task was created.
	 * This field is set automatically when the task is persisted and cannot be updated.
	 */
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	/**
	 * The timestamp when the task was last updated.
	 * This field is updated automatically every time the task is modified.
	 */
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	/**
	 * Sets the {@code createdAt} field to the current time before the entity is persisted.
	 * This method is called automatically by the JPA lifecycle when the entity is created.
	 */
	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
	}

	/**
	 * Sets the {@code updatedAt} field to the current time before the entity is updated.
	 * This method is called automatically by the JPA lifecycle when the entity is updated.
	 */
	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}