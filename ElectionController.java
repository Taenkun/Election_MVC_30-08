package controller;
import model.*;
import view.ElectionView;

public class ElectionController {
    public Election model;
    public View Electionview;
    
    public ElectionController(Election m, View v) { model = m; view = v; }

    public void Vote() {
        try {
            System.out.print("ลงคะแนน (Voter,R1,R2,R3) ตัวอย่าง V04,C01,C02,C03 : ");
            String[] in = view.sc.nextLine().split(",");
            model.addVotingCards(in[0], new String[]{in[1], in[2], in[3]});
            System.out.println("รับบัตรสำเร็จ");
        } catch(Exception e) { view.Error(e.getMessage()); }
    }

    public void VoteClose() {
        model.closeElection();
        model.findSupiciousGroups();
        System.out.println("หยุดรับบัตรใหม่ ปิดรับคะแนน");
        view.SupiciousGroups(model.suspiciousGroups);
        view.Result(model.calculateScores());
    }

    public void Resolve() {
        System.out.print("ตัดสินกลุ่ม (Pattern,รับรอง/ไม่นับ) ตัวอย่าง C01>C02>C03,รับรอง : ");
        String[] in = view.sc.nextLine().split(",");
        model.resolveGroup(in[0], in[1]);
        System.out.println("บันทึกคำตัดสินสำเร็จ");
        if (model.status.equals("FINAL")) System.out.println("การเลือกตั้งสรุปผลแล้ว");
    }

    public void start() {
        while(true) {
            System.out.println("\nสถานะ: " + model.status + " | บัตรทั้งหมด: " + model.votingCards.size());
            System.out.print("เมนู: [1] ดูผู้สมัคร [2] ลงคะแนน [3] ปิดรับคะแนน [4] ตัดสินกลุ่มบัตรซ้ำ [5] ดูผลรวม [0] ออก \n> ");
            String choice = view.sc.nextLine();
            if (choice.equals("1")) view.Candidate(model.candidates.values());
            else if (choice.equals("2")) Vote();
            else if (choice.equals("3")) VoteClose();
            else if (choice.equals("4")) Resolve();
            else if (choice.equals("5")) view.Result(model.calculateScores());
            else if (choice.equals("0")) break;
        }
    }
}