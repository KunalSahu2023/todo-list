package todolist.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequest {

    @NotBlank(message = "Title is mandatory")
    @Size(min= 1, max = 50, message = "Title must be between 1 and characters")
    private String title;

    private boolean completed = false;
}
