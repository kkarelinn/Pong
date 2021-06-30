package engine;

import screens.GameConstants;
import screens.PingPongGreenTable;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 *Этот класс – обработчик событий мыши и клавиатуры.
 * Рассчитывает движение мяча и ракеток,изменение их координат.
 */
public class PingPongGameEngine implements Runnable, MouseMotionListener, KeyListener, GameConstants {
    private PingPongGreenTable table; // ссылка на стол!
    private int kidRacket_Y = KID_RACKET_Y_START;
    private int computerRacket_Y = COMPUTER_RACKET_Y_START;

    private int kidScore;
    private int computerScore;
    private int ballX; // координата X мяча!
    private int ballY; // координата Y мяча!
    private boolean movingLeft = true;
    private int verticalSlide;           //Значение вертикального передвижения мяча в пикселях!
   // private volatile boolean setScore = true;

    private volatile boolean ballServed = false;

    // Конструктор. Содержит ссылку на объект стола
    public PingPongGameEngine(PingPongGreenTable greenTable) {
        table = greenTable;
        Thread worker = new Thread(this);
        worker.start();
    }

    // Обязательные методы из интерфейса MouseMotionListener (некоторые из них пустые,но должны быть включены все равно)
    public void mouseDragged(MouseEvent e) {
    }
    public void mouseMoved(MouseEvent e) {
        int mouse_Y = e.getY();
// Если мышь находится выше ракетки ребенка и не выходит за пределы стола – передвинуть ее вверх,
// в противном случае – опустить вниз
        if (mouse_Y < kidRacket_Y && kidRacket_Y > TABLE_TOP) {
            kidRacket_Y -= RACKET_INCREMENT;
        } else if (kidRacket_Y < TABLE_BOTTOM) {
            kidRacket_Y += RACKET_INCREMENT;
        }
// Установить новое положение ракетки
        table.setKidRacket_Y(kidRacket_Y);
    }
    // Обязательные методы из интерфейса KeyListener
    public void keyPressed(KeyEvent e){
        char key = e.getKeyChar();
        if ('n' == key || 'N' == key){
            startNewGame();
        } else if ('q' == key || 'Q' == key){
            endGame();
        } else if ('s' == key || 'S' == key){
            kidServe();
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    // Начать новую игру
    public void startNewGame(){
        computerScore=0;
        kidScore=0;
        table.setMessageText("Score Computer: 0 Kid: 0");
        kidServe();
    }
    // Завершить игру!
    public void endGame(){
        System.exit(0);
    }

    // Обязательный метод run() из интерфейса Runnable
    public void run(){
        boolean canBounce;//=false;
        while (true) {
            if (ballServed) { // если мяч движется
//Шаг 1. Мяч движется влево?
                if (movingLeft && ballX >= BALL_MIN_X) {
                    canBounce = (ballY+BALL_SIZE/2 >= computerRacket_Y && ballY+BALL_SIZE/2 <= (computerRacket_Y + RACKET_LENGTH) ? true : false);
                    ballX -= BALL_INCREMENT;
// Добавить смещение вверх или вниз к любым движениям мяча влево или вправо
                    if (ballY==TABLE_TOP || ballY+BALL_SIZE==TABLE_BOTTOM) verticalSlide = verticalSlide*(-1);

                        ballY -= verticalSlide;
                    table.setBallPosition(ballX, ballY);
// Может отскочить?
                    if (ballX == COMPUTER_RACKET_X+RACKET_WIDTH && canBounce) {
                        movingLeft = false;
                        verticalSlide = verticalSlide*(-1);
                    }
                }
// Шаг 2. Мяч движется вправо?
                if (!movingLeft && ballX <= BALL_MAX_X) {
                    canBounce = (ballY+BALL_SIZE/2 >= kidRacket_Y && ballY+BALL_SIZE/2 <= (kidRacket_Y + RACKET_LENGTH) ? true : false);
                    ballX += BALL_INCREMENT;
                    if (ballY==TABLE_TOP || ballY+BALL_SIZE==TABLE_BOTTOM) verticalSlide = verticalSlide*(-1);

                    ballY += verticalSlide;
                    table.setBallPosition(ballX, ballY);
// Может отскочить?
                    if (ballX+BALL_SIZE == KID_RACKET_X && canBounce) {
                        movingLeft = true;
                        verticalSlide = verticalSlide*(-1);
                    }
                }
// Шаг 3. Перемещать ракетку компьютера вверх или вниз, чтобы блокировать мяч
                if (computerRacket_Y+RACKET_LENGTH/2 < ballY+BALL_SIZE/2 && computerRacket_Y+RACKET_LENGTH < TABLE_BOTTOM){
                    computerRacket_Y +=RACKET_INCREMENT;
                }else if (computerRacket_Y+RACKET_LENGTH/2 > ballY+BALL_SIZE/2 && computerRacket_Y > TABLE_TOP){
                    computerRacket_Y -=RACKET_INCREMENT;
                }
                table.setComputerRacket_Y(computerRacket_Y);
// Шаг 4. Приостановить
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
// Шаг 5. Обновить счет, если мяч в зеленой области, но не движется
                if (isOut()){
                    if (ballX+BALL_SIZE == BALL_MAX_X ){
                        computerScore++;
                        System.out.println("Аут вправо");
                        displayScore();
                     //   setScore = false;

                    }else if (ballX == BALL_MIN_X){
                        kidScore++;
                        System.out.println("Аут влево");
                        displayScore();
                      //  setScore=false;
                    }
                }
            } // Конец if ballServed
        } // Конец while
    }// Конец run()

    // Подать с текущей позиции ракетки ребенка!
    private void kidServe(){
        ballServed = true;
       // setScore=true;
        movingLeft = true;
        ballX = KID_RACKET_X-BALL_SIZE-1;
        ballY=kidRacket_Y+RACKET_LENGTH/2-BALL_SIZE/2;
        if (ballY+BALL_SIZE/2 > TABLE_HEIGHT/2){
            verticalSlide=-1;
        }else{
            verticalSlide=1;
        }
        table.setBallPosition(ballX,ballY);
        table.setKidRacket_Y(kidRacket_Y);
    }
    private void displayScore(){
        ballServed = false;
        table.setMessageText("Computer: "+ computerScore +
                " Kid: " + kidScore);
        if (computerScore ==WINNING_SCORE){
            JOptionPane.showConfirmDialog(null,
                    "Computer won! " + computerScore +
                            ":" + kidScore, "статус",
                    javax.swing.JOptionPane.PLAIN_MESSAGE);
           /* table.setMessageText("Computer won! " + computerScore +
                    ":" + kidScore);*/
            endGame();
        }else if (kidScore ==WINNING_SCORE){
            JOptionPane.showConfirmDialog(null,
                    "You won! "+ kidScore +
                            ":" + computerScore, "статус",
                    javax.swing.JOptionPane.PLAIN_MESSAGE);
            table.setMessageText("You won! "+ kidScore +
                    ":" + computerScore);
            endGame();
        }
    }
    // Проверить, не пересек ли мяч верхнюю или нижнюю границу стола
    private boolean isOut(){
        if (ballX == BALL_MIN_X || ballX+BALL_SIZE == BALL_MAX_X){
            System.out.println("аут");
            return true;
        }else {
            return false;
        }
    }
}
