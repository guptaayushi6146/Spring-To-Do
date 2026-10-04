package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestHandler {

	final TaskRepo tr;

	RequestHandler(TaskRepo tr) {
		this.tr = tr;
	}

	@GetMapping("/getTasks")
	List<Task> getTasks() {
		return tr.findAll();
	}

	@GetMapping("/getTaskById/{id}")
	ResponseEntity<?> getTaskById(@PathVariable int id) {

		Optional<Task> task = tr.findById(id);

		if (task.isPresent()) {
			return ResponseEntity.ok(task.get());
		}

		return ResponseEntity.status(404).body("Task with id " + id + " does not exist");
	}

	@PostMapping("/addTask")
	Task addTask(@RequestBody Task t) {
		return tr.save(t);
	}

	@PutMapping("/updateTask/{id}")
	ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody Task t) {

		Optional<Task> task = tr.findById(id);

		if (task.isPresent()) {

			Task obj = task.get();

			obj.setTaskDesc(t.getTaskDesc());
			obj.setTaskStatus(t.getTaskStatus());

			return ResponseEntity.ok(tr.save(obj));
		}

		return ResponseEntity.status(404).body("Task with id " + id + " does not exist");
	}

	@DeleteMapping("/deleteTask/{id}")
	ResponseEntity<String> deleteTask(@PathVariable int id) {

		if (tr.existsById(id)) {

			tr.deleteById(id);

			return ResponseEntity.ok("Task with id " + id + " deleted successfully");
		}

		return ResponseEntity.status(404).body("Task with id " + id + " does not exist");
	}
}