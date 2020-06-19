package pl.ske.project1.entity;

import javax.persistence.*;

@Entity
public class Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double timeFrame;
    private String description;
    private boolean possibilityOfParole;
    @OneToOne(mappedBy = "sentence")
    private CourtCase courtCase;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(double timeFrame) {
        this.timeFrame = timeFrame;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPossibilityOfParole() {
        return possibilityOfParole;
    }

    public void setPossibilityOfParole(boolean possibilityOfParole) {
        this.possibilityOfParole = possibilityOfParole;
    }

    public CourtCase getCourtCase() {
        return courtCase;
    }

    public void setCourtCase(CourtCase courtCase) {
        this.courtCase = courtCase;
    }
}
