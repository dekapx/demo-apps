package net.dekapx.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.dekapx.core.mapper.Mapper;
import net.dekapx.todo.domain.Todo;
import net.dekapx.todo.model.TodoModel;
import net.dekapx.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class TodoController {
    @Autowired
    private TodoService todoService;

    @Autowired
    @Qualifier("todoMapper")
    private Mapper<Todo, TodoModel> todoMapper;

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
        final Todo todo = this.todoService.findById(id);
        return new ResponseEntity<>(this.todoMapper.toModel(todo), HttpStatus.OK);
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
        final Todo todo = this.todoService.create(this.todoMapper.toEntity(todoModel));
        return new ResponseEntity<TodoModel>(this.todoMapper.toModel(todo), HttpStatus.OK);
    }
}
