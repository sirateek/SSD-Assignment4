import java.util.ArrayList;
import java.util.List;

public class BulletPool {
    List<Bullet> bullets = new ArrayList<Bullet>();
    int defaultSize = 30;

    public BulletPool() {
        for (int i = 0; i < defaultSize; i++) {
            bullets.add(new Bullet(-999, -999, 0, 0));
        }
        checkBulletThread();
    }

    private void checkBulletThread() {
        Thread checkBulletThread = new Thread() {
            int count = 0;

            @Override
            public void run() {
                while (true) {

                    if (bullets.size() > defaultSize) {
                        count++;
                    } else {
                        count = 0;
                    }

                    if (count == 5) {
                        System.out.println("Reduce size to defaultSize");

                        // Destroy bullet to defaultSize
                        int numToDestroy = bullets.size() - defaultSize;
                        for (int i = 0; i < numToDestroy; i++) {
                            bullets.remove(0);
                        }
                        System.out.println(bullets.size());
                        count = 0;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        checkBulletThread.start();
    }

    public Bullet requestBullet(int x, int y, int dx, int dy) {
        if (bullets.size() < 1) {
            bullets.add(new Bullet(-999, -999, 0, 0));
        }
        Bullet bullet = bullets.remove(0);
        bullet.refreshState(x, y, dx, dy);
        return bullet;
    }

    public void returnBullet(Bullet bullet) {
        bullets.add(bullet);
    }

}
