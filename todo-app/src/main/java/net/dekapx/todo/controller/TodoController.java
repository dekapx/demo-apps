package net.dekapx.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.dekapx.todo.delegate.TodoDelegate;
import net.dekapx.todo.model.TodoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TodoController {
    @Autowired
    private TodoDelegate todoDelegate;

    @Operation(summary = "Find a Todo Task by ID", tags = {"Todo"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Todo",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TodoModel.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
            @ApiResponse(responseCode = "404", description = "Todo with ID [x] not found.", content = @Content)
    })
    @GetMapping(value = "/todos/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoModel> findById(@PathVariable Long id) {
        log.info("Find Todo Task for ID [{}]", id);
        final TodoModel todoModel = this.todoDelegate.findById(id);
        return new ResponseEntity<>(todoModel, HttpStatus.OK);
    }

    @Operation(summary = "Find All Todo Tasks", tags = {"Todo"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Find All Todo Task",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TodoModel.class))
                    }),
            @ApiResponse(responseCode = "204", description = "No Content.", content = @Content)
    })
    @GetMapping(value = "/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TodoModel>> findAll() {
        log.info("Find All Todo Tasks");
        final List<TodoModel> todos = this.todoDelegate.findAll();
        return new ResponseEntity<>(todos, HttpStatus.OK);
    }

    @Operation(summary = "Create new Todo Task", tags = {"Todo"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "New Todo Task created",
                    content = @Content(schema = @Schema(implementation = TodoModel.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Todo Task already exists"),
            @ApiResponse(responseCode = "405", description = "Validation exception")})
    @PostMapping(value = "/todos", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoModel> create(@Valid @RequestBody TodoModel todoModel) {
        log.info("Create new Todo Task...");
        todoModel = this.todoDelegate.create(todoModel);
        return new ResponseEntity<TodoModel>(todoModel, HttpStatus.OK);
    }

    @Operation(summary = "Update an existing Todo Task", description = "", tags = {"Todo"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Todo Task with ID [x] not found."),
            @ApiResponse(responseCode = "405", description = "Validation exception")})
    @PutMapping(value = "/todos",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TodoModel> update(@PathVariable Long id, @Valid @RequestBody TodoModel todoModel) {
        log.info("Update Todo Task for ID [{}]...", id);
        return new ResponseEntity<>(this.todoDelegate.update(id, todoModel), HttpStatus.OK);
    }

    @Operation(summary = "Deletes a Todo Task", description = "", tags = {"Todo"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Todo Task not found")})
    @DeleteMapping(path = "/todos/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        log.info("Delete Todo Task for ID [{}]...", id);
        this.todoDelegate.delete(id);
        return new ResponseEntity<>(String.format("Todo Task for id [%s] removed successfully...", id), HttpStatus.OK);
    }
}
