package my.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentSaveDto {

    @NotBlank
    private String author;

    @NotBlank
    private String text;
}
