import java.awt.*;
import java.util.Random;

public class Board {
    private final int width; //ширина окна
    private final int height; //высота окна
    private final int sizeW; //ширина поля
    private final int sizeH; //высота поля
    private final double radius; //радиус клетки
    private final Cell[][] meat; //массив клеток
    private Pattern ptr;
    private int genCounter = 0; //счётчик поколений

    //Создание окна и поля
    public Board(int width, int height, double radius) {
        this.width = width;
        this.height = height;
        this.radius = radius;
        double tmp = radius * 2;
        sizeW = width / (int) tmp;
        sizeH = height / (int) tmp;
        meat = new Cell[sizeW][sizeH];
        fillCell();
        drawBoard();
    }

    //Создание окна и поля
    public Board(int width, int height, double radius, String fileName) {
        ptr = new Pattern(fileName);
        this.width = width;
        this.height = height;
        this.radius = radius;
        double tmp = radius * 2;
        sizeW = (width / (int) tmp);
        sizeH = (height / (int) tmp);
        meat = new Cell[sizeW][sizeH];
        fillCell();
        drawBoard();
    }

    //Создание поля
    public void drawBoard (){
        StdDraw.setCanvasSize(width, height); //создание поля
        StdDraw.setXscale(0, width); //ширина поля
        StdDraw.setYscale(0, height); //высота поля
        StdDraw.clear(Color.black); //цвет поля
    }
    //Заполнение поля клетками
    public void fillCell(){
        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                meat[i][j] = new Cell();
            }
        }
    }

    //Возвращает статус клетки (жива / не жива)
    public boolean isAlive(int row, int col){
        return meat[row][col].getStatus();
    }

    //Расчёт кол-ва соседей
    public int neighbours(int row, int col){
        int count = 0;
        if(row == 0){
            if(col == 0){
                if(isAlive(row+1, col)) count++;
                if(isAlive(row+1, col+1)) count++;
                if(isAlive(row, col+1)) count++;
            }
            else if(col == sizeH-1){
                if(isAlive(row, col-1)) count++;
                if(isAlive(row+1, col-1)) count++;
                if(isAlive(row+1, col)) count++;
            }
            else {
                if(isAlive(row, col-1)) count++;
                if(isAlive(row+1, col-1)) count++;
                if(isAlive(row+1, col)) count++;
                if(isAlive(row+1, col+1)) count++;
                if(isAlive(row, col+1)) count++;
            }
        }
        else if(row == sizeW-1){
            if(col == 0){
                if(isAlive(row-1, col)) count++;
                if(isAlive(row-1, col+1)) count++;
                if(isAlive(row, col+1)) count++;
            }
            else if(col == sizeH-1){
                if(isAlive(row, col-1)) count++;
                if(isAlive(row-1, col-1)) count++;
                if(isAlive(row-1, col)) count++;
            }
            else {
                if(isAlive(row, col-1)) count++;
                if(isAlive(row-1, col-1)) count++;
                if(isAlive(row-1, col)) count++;
                if(isAlive(row-1, col+1)) count++;
                if(isAlive(row, col+1)) count++;
            }
        }
        else{
            if (col == 0){
                if(isAlive(row-1, col)) count++;
                if(isAlive(row-1, col+1)) count++;
                if(isAlive(row, col+1)) count++;
                if(isAlive(row+1, col+1)) count++;
                if(isAlive(row+1, col)) count++;
            }
            else if (col == sizeH-1){
                if(isAlive(row-1, col)) count++;
                if(isAlive(row-1, col-1)) count++;
                if(isAlive(row, col-1)) count++;
                if(isAlive(row+1, col-1)) count++;
                if(isAlive(row+1, col)) count++;
            }
            else{
                if(isAlive(row-1, col-1)) count++;
                if(isAlive(row-1, col)) count++;
                if(isAlive(row-1, col+1)) count++;
                if(isAlive(row, col+1)) count++;
                if(isAlive(row+1, col+1)) count++;
                if(isAlive(row+1, col)) count++;
                if(isAlive(row+1, col-1)) count++;
                if(isAlive(row, col-1)) count++;
            }
        }
        return count;
    }

    //Запись следующего поколения
    public void nextGenBuffer(){
        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                int nc = neighbours(i, j);
                if(nc == 3 || isAlive(i,j) && nc == 2){
                    meat[i][j].setStatus(true);
                }
                else {
                    meat[i][j].setStatus(false);
                }
            }
        }
    }

    //Обновление поколения
    public void updateGen(){
        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                meat[i][j].updateStatus();
            }
        }
    }

    public void nextGen(){
        nextGenBuffer();
        updateGen();
    }

    //Отрисовка клеток
    public void draw(){
        StdDraw.clear(Color.black); //Заполнение поля белым цветом
        StdDraw.setPenColor(Color.white);
        StdDraw.enableDoubleBuffering(); //необходимо для быстрой отрисовки
        double r = radius - 0.5;
        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                if (isAlive(i, j)) {
                    double tmpOX = i * 2 * radius + r;
                    double tmpOY = height - (j * 2 * radius + r);
                    StdDraw.filledSquare(tmpOX, tmpOY, radius);
                }
            }
        }
        genCounter++;
        String gens = "Поколений: " + genCounter;
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.textLeft(5, 10, gens);

        StdDraw.show();
    }

    //Отрисовка клеток с задержкой
    public void draw(int delay){
        StdDraw.clear(Color.black);
        StdDraw.setPenColor(Color.white);
        StdDraw.enableDoubleBuffering();
        double r = radius - 0.5;
        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                if (isAlive(i, j)) {
                    double tmpOX = i * 2 * radius + r;
                    double tmpOY = height - (j * 2 * radius + r);
                    StdDraw.filledSquare(tmpOX, tmpOY, radius);
                }
            }
        }
        long start = System.currentTimeMillis();
        while (start >= System.currentTimeMillis() - delay) {;}

        genCounter++;
        String gens = "Поколений: " + genCounter;
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.textLeft(5, 10, gens);

        StdDraw.show();
    }

    //Заполнение поля случайным образом
    public void fillRandom() {
        Random rnd = new Random();
        for (int i = 0; i < sizeW; i++) {
            for (int j = 0; j < sizeH; j++) {
                int tmp = rnd.nextInt(2);
                meat[i][j] = new Cell();
                if (tmp == 1) {
                    meat[i][j].setStatus(true);
                } else {
                    meat[i][j].setStatus(false);
                }
            }
        }
        updateGen();
    }

    //Заполнение поля паттерном
    public void fillPattern() {
        ptr.decode(); //получение массива раскодированных клеток
        for (int i = 0; i < ptr.x; i++) {
            for (int j = 0; j < ptr.y; j++) {
                if (ptr.turnedPattern[i][j].getStatus()) {
                    meat[i][j].setStatus(true);
                }
            }
        }
        updateGen();
    }
}