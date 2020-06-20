package pl.ske.project1.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Hearing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private java.sql.Timestamp hearingDate;
    private String courtroom;
    private boolean isPublic;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "courtcase_id", nullable = false)
    private CourtCase courtcase;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getHearingDate() {
        return hearingDate;
    }

    public void setHearingDate(Timestamp hearingDate) {
        this.hearingDate = hearingDate;
    }

    public String getCourtroom() {
        return courtroom;
    }

    public void setCourtroom(String courtroom) {
        this.courtroom = courtroom;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public CourtCase getCourtcase() {
        return courtcase;
    }

    public void setCourtcase(CourtCase courtcase) {
        this.courtcase = courtcase;
    }
}
