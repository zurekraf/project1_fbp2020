package pl.ske.project1.DTO;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class CourtCaseDTO extends RepresentationModel<CourtCaseDTO> {
    private long id;
    private String caseCode;
    private long accusedId;
    private long defenderId;
    private long sentenceId;
}
