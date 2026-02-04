package gh.gustass.api.controller;

import gh.gustass.api.dto.TaskDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tasks")
public class ApiController {

    private final List<TaskDTO> tasks = new ArrayList<>();
    private Long nextId = 1L;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> index() {
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO task) {
        task.setId(nextId++);
        tasks.add(task);

        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable Long id,
            @RequestBody TaskDTO updatedTask
    ) {
        Optional<TaskDTO> existingTask = tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();

        if (existingTask.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TaskDTO task = existingTask.get();
        task.setDescription(updatedTask.getDescription());

        return ResponseEntity.ok(task);
    }

    @DeleteMapping
    public ResponseEntity<Void> clearTasks() {
        tasks.clear();
        return ResponseEntity.noContent().build();
    }
}
