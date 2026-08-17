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
    @Size(max = 50, message = "Title cannot exceed 50 characters")
    private String title;

    private boolean completed = false;
}
