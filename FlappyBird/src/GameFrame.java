import javax.swing.*;

public class GameFrame extends JFrame {
    int boardHeight = 640;
    int boardWidth = 360;
    GamePanel panel;

    GameFrame(){
        panel = new GamePanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(boardWidth, boardHeight);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Flappy Bird");
        this.add(panel);
        this.pack();
        panel.requestFocus();
        this.setVisible(true);
    }
}
