public class StoryQuest extends Quest {
    public StoryQuest(String id, String title, int difficulty) {
        super(id, title, difficulty);
    }

    public StoryQuest(String id, String name, int difficulty,
        String requiredItem, int requiredQty) {
        super(id, name, difficulty);
        this.requiredItem = requiredItem;
        this.requiredQty = requiredQty;
    }

    @Override
    public int rewardPoints(int key) {
        return (difficulty * 10 + key * 2) % 60 + 20;
    }

    @Override
    public boolean canComplete(int energy, int logic, int luck) {
        return (logic > 60) || (luck > 70) || (energy > 75);
    }
}
