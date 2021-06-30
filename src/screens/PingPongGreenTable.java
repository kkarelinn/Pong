package screens;

import engine.*;
import javax.swing.*;
import java.awt.*;

/**
 *Этот класс рисует зеленый стол для пинг-понга,
 шар, ракетки, отображает счет
 */
public class PingPongGreenTable extends JPanel implements GameConstants{

    private JLabel label;
    private int computerRacket_Y = COMPUTER_RACKET_Y_START;	//начальные координнаты ракетки компа
    private int kidRacket_Y = KID_RACKET_Y_START;			//начальные координнаты ракетки игрока
    private int ballX = BALL_START_X;
    private int ballY = BALL_START_Y;

    Dimension preferredSize = new Dimension(TABLE_WIDTH,TABLE_HEIGHT);

    // Устанавливаем размеры окна.Вызывается виртуальной машиной
    public Dimension getPreferredSize() {
        return preferredSize;
    }

    // Конструктор. Создает обработчик событий мыши.
    PingPongGreenTable(){
        PingPongGameEngine gameEngine = new PingPongGameEngine(this);
        addMouseMotionListener(gameEngine);		// Обрабатываем движения мыши для передвижения ракеток
        addKeyListener(gameEngine);				// Обрабатываем события клавиатуры
    }

    // Добавим в окно панель с JLabel
    void addPaneltoFrame(Container container) {
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));
        container.add(this);
        label = new JLabel(	"N-new game, S-serve, Q-quit");
        container.add(label);
    }

    // Перерисовать окно. Этот метод вызывается виртуальной машиной, когда нужно обновить экран или
    // вызывается метод repaint() из PingPointGameEngine

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Нарисовать зеленый стол
        g.setColor(Color.GREEN);
        g.fillRect(0,0,TABLE_WIDTH,TABLE_HEIGHT);

        // Нарисовать правую ракетку
        g.setColor(Color.yellow);
        g.fillRect(KID_RACKET_X, kidRacket_Y, RACKET_WIDTH, RACKET_LENGTH);

        // Нарисовать левую ракетку
        g.setColor(Color.blue);
        g.fillRect(COMPUTER_RACKET_X, computerRacket_Y,	RACKET_WIDTH,RACKET_LENGTH);

        // Нарисовать мяч
        g.setColor(Color.red);
        g.fillOval(ballX,ballY,BALL_SIZE,BALL_SIZE);	//заданы размеры мяча 10*10

        // Нарисовать белые линии!
        g.setColor(Color.white);
        g.drawRect(10,10,310,210);
        g.drawLine(165,10,165,220);

        // Установить фокус на стол, чтобы обработчик клавиатуры мог посылать команды столу
        requestFocus();
    }


    // Установить текущее положение ракетки ребенка
    public void setKidRacket_Y(int yCoordinate){
        if (yCoordinate>=TABLE_TOP && yCoordinate<TABLE_BOTTOM-RACKET_LENGTH) {
            this.kidRacket_Y = yCoordinate;
            repaint();
        }
       // else return;

    }

    // Вернуть текущее положение ракетки ребенка
    public int getKidRacket_Y(){
        return kidRacket_Y;
    }

    // Установить текущее положение ракетки компьютера!
    public void setComputerRacket_Y(int yCoordinate){this.computerRacket_Y = yCoordinate;
        repaint();
    }

    // Установить игровое сообщение
    public void setMessageText(String text){
        label.setText(text);
        label.setVisible(true);
        repaint();
    }

    // Установить позицию мяча!
    public void setBallPosition(int xPos, int yPos){
        ballX=xPos;
        ballY=yPos;
        repaint();
    }

    public static void main(String[] args) {
        // Создать экземпляр окна
        JFrame frame = new JFrame("Ping Pong");

        // Убедиться, что окно может быть закрыто по нажатию на крестик в углу!
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        PingPongGreenTable table = new PingPongGreenTable();
        table.addPaneltoFrame(frame.getContentPane());

        // Установить размер окна и сделать его видимым
        frame.pack();
        frame.setVisible(true);
    }

}

