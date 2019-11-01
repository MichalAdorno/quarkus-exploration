package org.adorno.learning.dto;

import lombok.Getter;
import lombok.Setter;
import org.adorno.learning.domain.Status;

import java.time.LocalDate;

@Getter
@Setter
public class PersonDTO {
    private String name;
    private Status status;
    private LocalDate birth;
}
