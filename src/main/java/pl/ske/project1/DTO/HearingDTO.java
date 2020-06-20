package pl.ske.project1.DTO;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class HearingDTO extends RepresentationModel<HearingDTO> {
    private long id;
    private String courtroom;
    private java.sql.Timestamp hearingDate;
    private boolean isPublic;
    private long courtcaseId;
    private String courtcaseCaseCode;
}
