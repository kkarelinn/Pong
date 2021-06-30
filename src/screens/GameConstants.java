package screens;

public interface GameConstants {
    // Размеры стола

    int TABLE_WIDTH = 330;		//ширина - по оис Х (влево-право)
    int TABLE_HEIGHT = 230;	//высота - по оси У (вверх-вниз)

    int TABLE_TOP = 10;		//непонятно на что влияет высота по У (вверх вниз) граница поля игры
    int TABLE_BOTTOM = 220;
    int BALL_SIZE = 10;


    // Шаг перемещения мяча в пикселях!
    int BALL_INCREMENT = 1;		//скорость мяча
    int RACKET_INCREMENT = 4;		//скроость ракетки

    int RACKET_LENGTH = 30;		//размеры ракетки
    int RACKET_WIDTH = 10;

    //Размеры, расположения и шаг перемещения ракеток
    int KID_RACKET_X = TABLE_WIDTH-RACKET_WIDTH-11;
    int KID_RACKET_Y_START = TABLE_HEIGHT/2-RACKET_LENGTH/2;


    int COMPUTER_RACKET_X = 11;
    int COMPUTER_RACKET_Y_START = TABLE_HEIGHT/2-RACKET_LENGTH/2;

    int WINNING_SCORE = 2;		//количество очков до выигрыша

    // Максимальные и минимальные координаты мяча полета мяча по полю
    int BALL_MIN_X = COMPUTER_RACKET_X;					//см.TABLE_TOP
    int BALL_MAX_X = KID_RACKET_X+RACKET_WIDTH;							//см.TABLE_TOP

    int BALL_MIN_Y = TABLE_TOP;
    int BALL_MAX_Y = TABLE_BOTTOM-BALL_SIZE;				// 10 - диаметр мяча

    // Начальные координаты мяча
    int BALL_START_X = TABLE_WIDTH/2-5;		// -5 - смещение начальной точки мяча на половину ширины мяча
    int BALL_START_Y = TABLE_HEIGHT/2;

    // Замедлить быстрые компьютеры – измените это значение, если понадобится
    int SLEEP_TIME = 10;
}//время в миллисекундах
