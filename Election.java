package model;
import java.util.*;

public class Election {
    public String status = "OPEN";
    public Map<String, Candidate> candidates = new HashMap<>();
    public Map<String, Voter> voters = new HashMap<>();
    public List<VotingCard> votingCards = new ArrayList<>();
    public List<VotingCardPattern> suspiciousGroups = new ArrayList<>();

    public Election() {
        candidates.put("C01", new Candidate("C01", "Null Pointer"));
        candidates.put("C02", new Candidate("C02", "Merge Conflict"));
        candidates.put("C03", new Candidate("C03", "Works on My Machine"));
        candidates.put("C04", new Candidate("C04", "404 Policy Not Found"));
        candidates.put("C05", new Candidate("C05", "Ctrl+Z Nation"));
        for (int i = 1; i <= 7; i++) voters.put("V0" + i, new Voter("V0" + i));
        
        addSeed("V01", "C01", "C02", "C03");
        addSeed("V02", "C01", "C02", "C03");
        addSeed("V03", "C02", "C03", "C04");
    }

    private void addSeed(String v, String c1, String c2, String c3) {
        voters.get(v).hasVoted = true;
        votingCards.add(new VotingCard(voters.get(v), candidates.get(c1), candidates.get(c2), candidates.get(c3)));
    }

    public void addVotingCards(String vId, String[] ranks) throws Exception {
        if (!status.equals("OPEN")) throw new Exception("การเลือกตั้งไม่ได้อยู่ในสถานะเปิดรับคะแนน");
        Voter v = voters.get(vId);
        if (v == null || v.hasVoted) throw new Exception("ไม่มีสิทธิ์หรือเคยลงคะแนนแล้ว");
        if (ranks[0].equals(ranks[1]) || ranks[0].equals(ranks[2]) || ranks[1].equals(ranks[2])) throw new Exception("ผู้สมัครในบัตรต้องแตกต่างกัน");
        
        votingCards.add(new VotingCard(v, candidates.get(ranks[0]), candidates.get(ranks[1]), candidates.get(ranks[2])));
        v.hasVoted = true;
    }

    public void closeElection() { status = "CLOSED"; }

    public void findSupiciousGroups() {
        Map<String, List<VotingCard>> map = new HashMap<>();
        for (VotingCard c : votingCards) map.computeIfAbsent(c.getPattern(), k -> new ArrayList<>()).add(c);
        
        for (var e : map.entrySet()) {
            if (e.getValue().size() >= 3) {
                suspiciousGroups.add(new VotingCardPattern(e.getKey(), e.getValue()));
                for (VotingCard c : e.getValue()) c.status = "รอตรวจสอบ";
            } else {
                for (VotingCard c : e.getValue()) c.status = "รับรองแล้ว";
            }
        }
    }

    public Map<String, Integer> calculateScores() {
        Map<String, Integer> scores = new HashMap<>();
        for (String k : candidates.keySet()) scores.put(k, 0);
        
        for (VotingCard c : votingCards) {
            if (c.status.equals("รับรองแล้ว") || c.status.equals("รับรอง")) {
                scores.put(c.r1.id, scores.get(c.r1.id) + 3);
                scores.put(c.r2.id, scores.get(c.r2.id) + 2);
                scores.put(c.r3.id, scores.get(c.r3.id) + 1);
            }
        }
        return scores;
    }

    public void resolveGroup(String pattern, String decision) {
        for (VotingCardPattern g : suspiciousGroups) {
            if (g.pattern.equals(pattern)) {
                g.status = decision;
                for (VotingCard c : g.VCards) c.status = decision;
            }
        }
        if (suspiciousGroups.stream().allMatch(g -> !g.status.equals("รอตรวจสอบ"))) status = "FINAL";
    }
}