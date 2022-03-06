package net.dekapx.todo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoModel {
    private Long id;
    @Schema(description = "Todo Task", required = true)
    @NotNull(message = "Todo Task must not be null or empty.")
    @Size(min = 5, max = 25, message = "Todo Task must be between 5 and 25 characters")
    private String task;

    @Schema(description = "Todo Task Description", required = true)
    @NotNull(message = "Todo Task Description must not be null or empty.")
    @Size(min = 5, max = 75, message = "Todo Task Description must be between 5 and 75 characters")
    private String description;

    @Schema(description = "Todo Task Status", required = true)
    private boolean completed;

    @Schema(description = "Todo Task Modified Date", required = true)
    private Date lastModifiedDate;
}
