public abstract class Quest {
    protected String id;
    protected String title;
    protected int difficulty; // 1-5

    public Quest(String id, String title, int difficulty) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public int getDifficulty() { return difficulty; }

    public abstract int rewardPoints(int key);
    public abstract boolean canComplete(int energy, int logic, int luck);

    @Override
    public String toString() {
        return id + " | " + title + " | diff=" + difficulty;
    }
}
