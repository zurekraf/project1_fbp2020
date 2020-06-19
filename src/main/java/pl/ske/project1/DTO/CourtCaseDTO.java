package pl.ske.project1.DTO;

import lombok.Data;

@Data
public class CourtCaseDTO {
    private long id;
    private String caseCode;
    private long accusedId;
    private String sentenceId;
}
