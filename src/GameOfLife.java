public class GameOfLife {
    public static void main(String[] args) {
        int boardSizeX = 1600; //размер окна по горизонтали
        int boardSizeY = 800; //размер окна по вертикали
        double radius = 2; //радиус клеток
        String fileName = "pattern.txt";

        Board b = new Board(boardSizeX, boardSizeY, radius, fileName);

        b.fillRandom(); //заполнение поля случайным образом
//        b.fillPattern(); //заполнение поля готовым паттерном
        b.draw(); //отрисовка клеток на поле
        while(true){
            //отдаление
            if(StdDraw.isKeyPressed(45)){ // -
                StdDraw.setXscale(0, boardSizeX+=2);
                StdDraw.setYscale(0, boardSizeY++);
            }
            //приближение
            if(StdDraw.isKeyPressed(61)) { // +
                StdDraw.setXscale(0, boardSizeX-=2);
                StdDraw.setYscale(0, boardSizeY--);
            }
            //завершение работы программы нажатием кнопки
            if(StdDraw.isKeyPressed(27)){ // esc
                System.exit(0);
            }
            b.nextGen(); //расчёт следующего поколения
            b.draw(); //отрисовка следующего поколения
        }
    }
}
