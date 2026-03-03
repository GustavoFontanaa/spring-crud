package gh.gustass.api.controller;

import gh.gustass.api.model.Task;
import gh.gustass.api.repository.TaskRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class ApiController {

    private final TaskRepository taskRepository;

    public ApiController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public ResponseEntity<List<Task>> index() {
        return ResponseEntity.ok(taskRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<Task> addTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task);
        return ResponseEntity.ok(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long id,
            @RequestBody Task updatedTask
    ) {
        Optional<Task> existingTask = taskRepository.findById(id);

        if (existingTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Task task = existingTask.get();
        task.setDescription(updatedTask.getDescription());
        taskRepository.save(task);

        return ResponseEntity.ok(task);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearTasks() {
        taskRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
