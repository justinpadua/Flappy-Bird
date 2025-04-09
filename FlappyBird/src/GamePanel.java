import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    final static int boardWidth = 360;
    final static int boardHeight = 640;

//    birdie
    static int birdX = boardWidth/8;
    static int birdY = boardHeight/3;
    static int birdWidth = 34;
    static int birdHeight = 24;
    static int velocityY = -4;
    static int gravity = 1;
//    pipie
    static int pipeX = boardWidth;
    static int pipeY = 0;
    static int pipeWidth = 64;
    static int pipeHeight = 512;
    static int velocityX = -4;

    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    //Random random = new Random();
    ArrayList<Pipe> pipes;
    Bird bird;
    Timer gameLoop;
    Timer placePipesTimer;

    boolean gameOver = false;
    boolean notPlaying = true;
    double score = 0;

    GamePanel(){
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        //setBackground(Color.blue);
        setFocusable(true);
        addKeyListener(this);

        backgroundImg = new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

        bird = new Bird(birdImg);
        pipes = new ArrayList<Pipe>();

        gameLoop = new Timer(1000/50, this);
        placePipesTimer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placePipes();
            }
        });
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void placePipes(){
        int randomPipeY = (int)(pipeY - pipeHeight/4 - Math.random()*(pipeHeight/2));
        int openingSpace = boardHeight/4;

        Pipe topPipe = new Pipe(topPipeImg);
        topPipe.y = randomPipeY;
        pipes.add(topPipe);

        Pipe bottomPipe = new Pipe(bottomPipeImg);
        bottomPipe.y = topPipe.y + pipeHeight + openingSpace;
        pipes.add(bottomPipe);
    }
    public void draw(Graphics g){
//        draw background img
        g.drawImage(backgroundImg,0, 0, boardWidth, boardHeight, null);
//        draw bird
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);


//        draw pipe
        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }
//        score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 32));
//        score
        if(gameOver){
            g.drawString("Game Over: "+ String.valueOf((int)score), 10, 35);
        }
        else{
            g.drawString("Score: "+ String.valueOf((int) score), 10, 35);
        }
        //        menu

        if(notPlaying){
            g.setFont(new Font("Arial", Font.BOLD, 50));

            // Center "Flappy Bird"
            String title = "Flappy Bird";
            FontMetrics fm = g.getFontMetrics();
            int titleWidth = fm.stringWidth(title);
            int titleX = (boardWidth - titleWidth) / 2;
            int titleY = boardHeight / 4; // Adjust for better positioning
            g.drawString(title, titleX, titleY);
            // Display "made by master Justin" at the bottom-left

            g.setFont(new Font("Arial", Font.PLAIN, 25));
            g.drawString("made by master Justin", 10, 50);
        }
    }
    public void move(){
        bird.velocityY += bird.gravity;
        bird.y += bird.velocityY;
        bird.y = Math.max(bird.y, 0);

        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.x += velocityX;
            if(!pipe.passed && bird.x > pipe.x + pipe.width) {
                pipe.passed = true;
                score += 0.5;
            }
            if(checkCollision(bird, pipe)){
                gameOver = true;
            }
        }

        if(bird.y > boardHeight){
            gameOver = true;
        }
    }
    public boolean checkCollision(Bird a, Pipe b){
        return  a.x < b.x + b.width &&
                a.x + a.width > b.x &&
                a.y < b.y + b.height &&
                a.y + a.height > b.y;
    }
    public void play(){
        bird.y = boardHeight/3;
        score = 0;
        bird.velocityY = 0;
        pipes.clear();
        placePipesTimer.start();
        gameLoop.start();
        notPlaying = false;
        gameOver = false;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver){
            notPlaying = true;
            placePipesTimer.stop();
            gameLoop.stop();
        }
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            bird.velocityY = -10;
            if(gameOver){
                play();
            }
            if(notPlaying){
                play();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}
