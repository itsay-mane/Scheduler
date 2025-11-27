package scheduler;

public class Score implements Comparable<Score> {
    private int value;
    
    public Score(int value) {
        this.value = value;
    }

    public void increase() {
        this.value += 1;
    }

    public int getValue() {
        return value;
    }
    
    @Override
    public int compareTo(Score other) {
        return this.value - other.value;
    }
}
