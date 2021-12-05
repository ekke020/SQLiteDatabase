package loading;


public class ProgressBar implements Runnable {

    private final int taskGoal;
    private final int taskTick;
    private int nextTick;
    private int progress = 0;
    private int ticks = 1;

    public ProgressBar(int taskGoal) {
        this.taskGoal = taskGoal;
        this.taskTick = taskGoal / 10;
        nextTick = taskTick;
    }

    public void incrementProgress() {
        progress++;
        if (progress >= nextTick) {
            nextTick += taskTick;
            ticks++;
            synchronized (this) {
                notify();
            }
        }
    }

    @Override
    public void run() {
        while (progress < taskGoal) {
            System.out.print(getProgressBar());
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String getProgressBar() {
        String progressBar = "";
        switch (ticks) {
            case 1 -> progressBar = "|-         |\r";
            case 2 -> progressBar = "|--        |\r";
            case 3 -> progressBar = "|---       |\r";
            case 4 -> progressBar = "|----      |\r";
            case 5 -> progressBar = "|-----     |\r";
            case 6 -> progressBar = "|------    |\r";
            case 7 -> progressBar = "|-------   |\r";
            case 8 -> progressBar = "|--------  |\r";
            case 9 -> progressBar = "|--------- |\r";
            case 10 -> progressBar = "|----------|\r";
        }
        return progressBar;
    }

}
