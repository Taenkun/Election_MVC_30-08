package model;
import java.util.List;

public class VotingCardPattern {
    public String pattern;
    public List<VotingCard> VCards;
    public String status = "รอตรวจสอบ";
    
    public VotingCardPattern(String pattern, List<VotingCard> VCards) {
        this.pattern = pattern; this.VCards = VCards;
    }
}