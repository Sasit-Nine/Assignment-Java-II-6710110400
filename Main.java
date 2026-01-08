import java.util.*;

public class Main {

    static int sumDigits(String studentId) {
        int sum = 0;
        for (int i = 0; i < studentId.length(); i++) {
            char c = studentId.charAt(i);
            if (Character.isDigit(c)) sum += (c - '0');
        }
        return sum;
    }

    // Java II signature (เปลี่ยนสูตรจาก Java I เล็กน้อย)
    static String makeSignatureV2(String studentId, int score, int completed) {
        int checksum = 7;
        for (int i = 0; i < studentId.length(); i++) {
            checksum = (checksum * 37 + studentId.charAt(i)) % 100000;
        }
        checksum = (checksum + score * 89 + completed * 101) % 100000;
        return "SIG2-" + String.format("%05d", checksum);
    }

    static int askInt(Scanner sc, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v >= min && v <= max) return v;
            } catch (Exception ignored) {}
            System.out.println("ค่าที่กรอกไม่ถูกต้อง (" + min + "-" + max + ")");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Java II: Quest Manager (OOP) ===");
        System.out.print("กรอกรหัสนักศึกษา (ตัวเลข): ");
        String studentId = sc.nextLine().trim();

        int key = sumDigits(studentId) % 97;
        int energy = (key * 7 + 13) % 100;
        int logic  = (key * 11 + 5) % 100;
        int luck   = (key * 17 + 19) % 100;

        QuestService qs = new QuestService();
        qs.seedQuests(key);

        int totalScore = 0;

        while (true) {
            System.out.println("\n[Menu] 1) List 2) Do quest 3) Stats 4) Exit");
            int m = askInt(sc, "เลือกเมนู: ", 1, 4);

            if (m == 1) {
                qs.listQuests();

            } else if (m == 2) {
                System.out.print("พิมพ์ quest id: ");
                String id = sc.nextLine().trim();

                Quest q = qs.findById(id);
                if (q == null) {
                    System.out.println("ไม่พบ quest id นี้");
                    continue;
                }

                boolean ok = q.canComplete(energy, logic, luck);
                if (ok) {
                    int r = q.rewardPoints(key);
                    totalScore += r;
                    qs.stats.put("completed", qs.stats.get("completed") + 1);
                    System.out.println("สำเร็จ! +" + r + " points");
                } else {
                    qs.stats.put("failed", qs.stats.get("failed") + 1);
                    System.out.println("ยังทำไม่ได้ (ต้องมี energy>75 หรือ logic>60 หรือ luck>70)");
                }

            } else if (m == 3) {
                System.out.println("completed=" + qs.stats.get("completed") + ", failed=" + qs.stats.get("failed"));
                System.out.println("score=" + totalScore);

            } else {
                break;
            }
        }

        System.out.println("\n=== Result ===");
        System.out.println("score=" + totalScore);
        System.out.println("signature=" + makeSignatureV2(studentId, totalScore, qs.stats.get("completed")));

        // TODO: พิมพ์ 1-2 ประโยคในโปรแกรมว่า OOP ช่วยให้โค้ดดูแลง่ายขึ้นอย่างไร
        // การใช้ OOP ช่วยแยกความรับผิดชอบของคลาสอย่างชัดเจน 
        // ทำให้สามารถแก้ไขหรือเพิ่มประเภทเควสต์ใหม่ได้โดยไม่กระทบโค้ดส่วนอื่น

        sc.close();
    }
}
