package view;
import model.*;
import java.util.*;

public class ElectionView {
    public Scanner sc = new Scanner(System.in);
    
    public void Candidate(Collection<Candidate> c) { 
        c.forEach(x -> System.out.println(x.id + " : " + x.name)); 
    }
    
    public void Error(String e) { System.out.println("ปฏิเสธ: " + e); }
    
    public void SupiciousGroups(List<VotingCardPattern> groups) {
        System.out.println("\n กลุ่มบัตรที่รอตรวจสอบ ");
        groups.forEach(g -> System.out.println(g.pattern + " จำนวน " + g.VCards.size() + " ใบ (สถานะ: " + g.status + ")"));
    }
    
    public void Result(Map<String, Integer> scores) {
        System.out.println("\n  ผลคะแนน ");
        scores.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(e -> System.out.println(e.getKey() + " = " + e.getValue()));
    }
}