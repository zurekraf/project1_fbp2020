package pl.ske.project1.DTO;

import lombok.Data;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import pl.ske.project1.entity.Defender;

@Data
public class DefenderDTO extends RepresentationModel<DefenderDTO>{
    private long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String officeAddress;
}
