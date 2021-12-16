package bot;

import datasource.RobotDatasource;
import model.User;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Robot implements Runnable {

    private final User user;
    private final RobotDatasource robotDatasource = new RobotDatasource();
    private final Random random = new Random();
    private final int posts = random.nextInt(10);

    public Robot(User user) {
        this.user = user;
        robotDatasource.initializePreparedStatement();
    }

    @Override
    public void run() {
        int posted = 0;
        while (posted < posts) {
            if (random.nextInt(100) > 90)
                createPost();
            else {
                int id = robotDatasource.getRandomPostId();
                if (id != -1)
                    robotDatasource.postComment(id, user.getUserId());
                else
                    createPost();
            }
            posted++;
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000,5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(user.getUserName() + " is done posting.");
        robotDatasource.closePreparedStatement();
    }

    private void createPost() {
        robotDatasource.createPost(user.getUserId());
    }

}
