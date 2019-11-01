package org.adorno.learning.dto;

import lombok.Getter;
import lombok.Setter;
import org.adorno.learning.domain.Status;

import javax.json.bind.annotation.JsonbDateFormat;
import java.time.LocalDate;

@Getter
@Setter
public class PersonDTO {

    private String name;
    private Status status;
    @JsonbDateFormat(value = "yyyy-MM-dd")
    private LocalDate birth;

}