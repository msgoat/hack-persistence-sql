package group.msg.at.cloud.cloudtrain.core.boundary;

import group.msg.at.cloud.cloudtrain.adapter.persistence.jpa.repository.TaskRepository;
import group.msg.at.cloud.cloudtrain.core.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Simple {@code Boundary} that manages {@code Task} entities.
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
@Secured("CLOUDTRAIN_USER")
public class TaskManagement {

    @Autowired
    private TaskRepository repository;

    @NotNull
    public UUID addTask(@NotNull @Valid Task newTask) {
        Task saved = this.repository.saveAndFlush(newTask);
        return saved.getId();
    }

    public void modifyTask(@NotNull @Valid Task modifiedTask) {
        this.repository.save(modifiedTask);
    }

    public Optional<Task> getTaskById(@NotNull UUID taskId) {
        return this.repository.findById(taskId);
    }

    public void removeTask(@NotNull UUID taskId) {
        this.repository.deleteById(taskId);
    }

    public List<Task> getAllTasks() {
        return this.repository.findAll();
    }
}
