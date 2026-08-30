package model;

public class VotingCard {
    public Voter voter;
    public Candidate r1, r2, r3;
    public String status = "บันทึกแล้ว";
    
    public VotingCard(Voter v, Candidate c1, Candidate c2, Candidate c3) {
        this.voter = v; this.r1 = c1; this.r2 = c2; this.r3 = c3;
    }
    public String getPattern() { return r1.id + ">" + r2.id + ">" + r3.id; }
}