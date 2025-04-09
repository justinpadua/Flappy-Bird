import java.awt.*;

public class Pipe{
    int x = GamePanel.pipeX;
    int y = GamePanel.pipeY;
    int width = GamePanel.pipeWidth;
    int height = GamePanel.pipeHeight;
    //int velocityX = -4;
    boolean passed = false;
    Image img;

    Pipe(Image img){
        this.img = img;
    }
}