package org.adorno.learning.dto;

import lombok.Getter;
import lombok.Setter;
import org.adorno.learning.domain.Status;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class PersonDTO {

    @NotBlank(message = "Name may not be blank")
    private String name;
    @NotNull
    private Status status;
    @JsonbDateFormat(value = "yyyy-MM-dd")
    @NotNull
    private LocalDate birth;

}