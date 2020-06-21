package pl.ske.project1.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CourtCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String caseCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "accused_id", nullable = false)
    private Accused accused;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="COURTCASE_CHARGES",
            joinColumns = @JoinColumn(name="COURTCASE_ID"),
            inverseJoinColumns = @JoinColumn(name="CHARGE_ID")
    )
    private Set<Charge> charges;

    @OneToMany(mappedBy = "courtcase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<Hearing> hearings;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sentence_id", referencedColumnName = "id")
    private Sentence sentence;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "defender_id", nullable = true) //zmienione z false
    private Defender defender;

    public CourtCase() {
    }
    public CourtCase(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaseCode() {
        return caseCode;
    }

    public void setCaseCode(String caseCode) {
        this.caseCode = caseCode;
    }

    public Accused getAccused() {
        return accused;
    }

    public void setAccused(Accused accused) {
        this.accused = accused;
    }

    public Set<Charge> getCharges() {
        return charges;
    }

    public void setCharges(Set<Charge> charges) {
        this.charges = charges;
    }

    public Set<Hearing> getHearings() {
        return hearings;
    }

    public void setHearings(Set<Hearing> hearings) {
        this.hearings = hearings;
    }

    public Sentence getSentence() {
        return sentence;
    }

    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
    }

    public Defender getDefender() {
        return defender;
    }

    public void setDefender(Defender defender) {
        this.defender = defender;
    }
}
