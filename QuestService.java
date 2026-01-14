import java.util.*;

public class QuestService {
    public ArrayList<Quest> quests = new ArrayList<>();
    public HashMap<String, Integer> stats = new HashMap<>();
    public HashMap<String, Integer> inventory = new HashMap<>();

    public void addItem(String item, int qty) {
        inventory.put(item, inventory.getOrDefault(item, 0) + qty);
    }
    
    public boolean useItem(String item, int qty) {
        int have = inventory.getOrDefault(item, 0);
        if (have < qty) return false;
        inventory.put(item, have - qty);
        return true;
    }

    public QuestService() {
        stats.put("completed", 0);
        stats.put("failed", 0);
    }

    public void seedQuests(int key) {
        // TODO: ต้องสร้าง 6 เควสต์แบบ “เฉพาะบุคคล” จาก key
        // แนะนำ: ใช้ key เป็นส่วนหนึ่งของ id/title เพื่อให้ไม่ซ้ำ
        // ตัวอย่าง id: D-<key>-1, S-<key>-1

        quests.add(new DailyQuest("D-" + key + "-1", "Scan Lab Tools", (key % 5) + 1));
        quests.add(new DailyQuest("D-" + key + "-2", "Clean Dataset", ((key + 1) % 5) + 1));
        quests.add(new DailyQuest("D-" + key + "-3", "Refactor Code", ((key + 2) % 5) + 1));

        quests.add(new StoryQuest("S-" + key + "-1", "Decrypt Archive", ((key + 3) % 5) + 1));
        quests.add(new StoryQuest("S-" + key + "-2", "Escape the Loop", ((key + 4) % 5) + 1));

        quests.add(new StoryQuest("S-" + key + "-3",
            "Boss: NullPointer",
            ((key + 5) % 5) + 1,
            "KeyCard", 1));
    }

    public void listQuests() {
        for (Quest q : quests) {
            System.out.println(q.toString());
        }
    }

    public Quest findById(String id) {
        if (!id.matches("[DS]-\\d+-\\d+")) {
            throw new InvalidQuestException("รูปแบบ Quest ID ไม่ถูกต้อง");
        }

        for (Quest q : quests) {
            if (q.getId().equalsIgnoreCase(id)) return q;
        }
        return null;
    }
}