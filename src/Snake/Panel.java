/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import java.util.Random;
import javax.swing.Timer;

/**
 *
 * @author joellmacias
 */
public class Panel extends JPanel implements KeyListener, ActionListener {
boolean running = false;
    Random random;
    Timer timer;
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int DELAY = 100;
    int letterx;
    int lettery;
    int[] numberx = new int[10];
    int[] numbers = new int[10];
    int[] numbery = new int[10];
    String letter;
    Snake snake;

    public Panel() {
        random = new Random();
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setBackground(Color.black);
        snake = new Snake("@", 250, 250);
        startGame();
    }

    public void startGame() {
        newLetter();
        generateNumbers();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paint(Graphics g) {
        if (running) {
            paintComponent(g);

            //put your rendering code here
            g.setColor(Color.white);
            g.setFont(new Font("Arial", Font.PLAIN, 25));
            g.drawString(letter, letterx, lettery);  //Draw letter
            g.drawLine(800, 0, 800, 800);
            g.drawLine(0, 800, 800, 800);

            for (int i = 0; i < 10; i++) {
                g.drawString(String.valueOf(numbers[i]), numberx[i], numbery[i]);
            }
            Node<String> currentNode = snake.head;
            // draw snake
            for (int i = 0; i < snake.length; i++) 
            {
                if (i == 0) {
                    g.drawString(snake.head.data, snake.x[i], snake.y[i]);
                } else {
                    g.drawString(String.valueOf(currentNode.next.data), snake.x[i], snake.y[i]);
                    currentNode = currentNode.next;
                }
            }

        } else {
            gameOver(g);
        }
    }

      //generate a new letter
    public void newLetter() {
        letterx = random.nextInt(750 / 25) * 25 + 25;
        lettery = random.nextInt(750 / 25) * 25 + 25;
        char randomLetterChar;

        if (random.nextBoolean()) {
            randomLetterChar = (char) (random.nextInt(26) + 'A');
        } else {
            randomLetterChar = (char) (random.nextInt(26) + 'a');
        }
        letter = String.valueOf(randomLetterChar);
    }
    //generate numbers
    public void generateNumbers() {
        for (int i = 0; i < 10; i++) {
            numberx[i] = random.nextInt(800 / 25) * 25;
            numbery[i] = random.nextInt(800 / 25) * 25;
            numbers[i] = random.nextInt(10);
        }
    }
    //check if letter has been eaten
    public void checkLetter() {
        if ((snake.x[0] == letterx) && (snake.y[0] == lettery)) {
            snake.addInOrder(letter);
            newLetter();
        }
    }
    
    //check if a number has been eaten
    public void checkNumber() {

        for (int i = 0; i < 10; i++) {
            if ((snake.x[0] == numberx[i]) && (snake.y[0] == numbery[i])) {
                if (snake.head.next == null) {
                    running = false;
                    timer.stop();
                } else {
                    snake.remove(numbers[i]);
                    newNumber(i);
                }
            }
        }
    }
    //generate a new number
    public void newNumber(int i) {

        numberx[i] = random.nextInt(800 / 25) * 25;
        numbery[i] = random.nextInt(800 / 25) * 25;
        numbers[i] = random.nextInt(10);

    }
    //check the length of the snake
    public void checkLength() {
        if (snake.length == 0) {
            running = false;
            timer.stop();
        }
    }
    //check if there are any bordre collisions
    public void checkCollision() {
        if (snake.x[0] < 25) {
            running = false;
        } else if (snake.x[0] > 750) {
            running = false;
        } else if (snake.y[0] < 50) {
            running = false;
        } else if (snake.y[0] > 750) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }
    //draw game over 
    public void gameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 100));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Game over!", (800 - metrics.stringWidth("Game over!")) / 2, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            snake.move();
            checkLetter();
            checkCollision();
            checkNumber();
            checkLength();
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (snake.direction != 'R') {
                    snake.direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (snake.direction != 'L') {
                    snake.direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
                if (snake.direction != 'D') {
                    snake.direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
                if (snake.direction != 'U') {
                    snake.direction = 'D';
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }
}

    
