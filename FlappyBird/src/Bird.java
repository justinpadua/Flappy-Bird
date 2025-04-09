import java.awt.*;

public class Bird {
    int x = GamePanel.birdX;
    int y = GamePanel.birdY;
    int width = GamePanel.birdWidth;
    int height = GamePanel.birdHeight;
    int velocityY = GamePanel.velocityY;
    int gravity = GamePanel.gravity;

    Image img;
    Bird(Image img){
        this.img = img;
    }
}
