package screens;

public interface GameConstants {
    // Размеры стола
    //коммит
    public final int TABLE_WIDTH = 330;		//ширина - по оис Х (влево-право)
    public final int TABLE_HEIGHT = 230;	//высота - по оси У (вверх-вниз)

    public final int TABLE_TOP = 15;		//непонятно на что влияет высота по У (вверх вниз) граница поля игры
    public final int TABLE_BOTTOM = 215;

    // Шаг перемещения мяча в пикселях!
    public final int BALL_INCREMENT = 1;		//скорость мяча
    public final int RACKET_INCREMENT = 4;		//скроость ракетки

    public final int RACKET_LENGTH = 30;		//размеры ракетки
    public final int RACKET_WIDTH = 4;

    //Размеры, расположения и шаг перемещения ракеток
    public final int KID_RACKET_X = TABLE_WIDTH-RACKET_WIDTH-11;
    public final int KID_RACKET_Y_START = TABLE_HEIGHT/2-RACKET_LENGTH/2;


    public final int COMPUTER_RACKET_X = 11;
    public final int COMPUTER_RACKET_Y_START = TABLE_HEIGHT/2-RACKET_LENGTH/2;

    public final int WINNING_SCORE = 2;		//количество очков до выигрыша

    // Максимальные и минимальные координаты мяча полета мяча по полю
    public final int BALL_MIN_X = COMPUTER_RACKET_X;					//см.TABLE_TOP
    public final int BALL_MAX_X = KID_RACKET_X+RACKET_WIDTH;							//см.TABLE_TOP

    public final int BALL_MIN_Y = TABLE_TOP;
    public final int BALL_MAX_Y = TABLE_BOTTOM-10;				// 10 - диаметр мяча


    // Начальные координаты мяча
    public final int BALL_START_X = TABLE_WIDTH/2-5;		// -5 - смещение начальной точки мяча на половину ширины мяча
    public final int BALL_START_Y = TABLE_HEIGHT/2;


    // Замедлить быстрые компьютеры – измените это значение, если понадобится
    public final int SLEEP_TIME = 10;
}//время в миллисекундах
