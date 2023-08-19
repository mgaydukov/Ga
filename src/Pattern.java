import java.lang.*;
import java.util.ArrayList;

//Run-length encoding (Кодирование длин серий)
public class Pattern {
    public int x; //кол-во строк массива
    public int y; //кол-во столбцов массива
    private int cntDigits = 0; //счётчик натуральных числел
    private String encodedString; //закодированная строка
    private final String fileName; //имя файла
    public Cell[][] pattern; //массив клеток
    public Cell[][] turnedPattern; //траспонированный массив клеток
    ArrayList<Integer> digits = new ArrayList<>(); //список натуральных чисел

    public Pattern(String fileName) {
        this.fileName = fileName;
        fillData();
        pattern = new Cell[y][x];
        turnedPattern = new Cell[x][y];
        fillCells();
    }

    //Раскодирование клеток
    public void decode() {
        findDigits();
        int listCnt = 0, cnt = -1;
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x ; ) {
                if (cnt < encodedString.length() - 1) cnt++;

                if (encodedString.charAt(cnt) == '$') { //если след.символ $ - остальные клетки в строке - false
                    if(j != 0){
                        j = x;
                    }
                } else if (encodedString.charAt(cnt) == 'b') { //если след.символ b - статус клетки false
                    pattern[i][j].setStatus(false);
                    j++;
                } else if (encodedString.charAt(cnt) == 'o') { //если след.символ b - статус клетки true
                    pattern[i][j].setStatus(true);
                    j++;
                } else if (isNumber(encodedString, cnt)) { //если след.символ - чило
                    int tmp = digits.get(listCnt);
                    if (listCnt < cntDigits - 1) listCnt++;

                    do {
                        cnt++;
                    } while (!(encodedString.charAt(cnt) == 'o') && !(encodedString.charAt(cnt) == 'b') && !(encodedString.charAt(cnt) == '$'));

                    if (encodedString.charAt(cnt) == 'b') { //если след.символ b - статус клетки false
                        for (int k = 0; k < tmp; k++) {
                            pattern[i][j].setStatus(false);
                            if (j < x) {
                                j++;
                            }
                        }
                    } else if (encodedString.charAt(cnt) == 'o') { //если след.символ b - статус клетки true
                        for (int k = 0; k < tmp; k++) {
                            pattern[i][j].setStatus(true);
                            if (j < x) {
                                j++;
                            }
                        }
                    } else if (encodedString.charAt(cnt) == '$') { //если след.символ $ - остальные клетки в строке - false
                        for (int k = 0; k < tmp - 1 ; k++) {
                            if (i < y) i++;
                        }
                        j = x;
                    }
                } else if (encodedString.charAt(cnt) == '!'){
                    break;
                }
            }
        }
        updateGen(); //обновление поколения
        turn(); //транспонированние массива
        updateGenForTurned(); //обновление поколения для транс.массива
    }

    //Считывание размера паттерна и зашифрованной строки
    private void fillData(){
        String valueString;
        ReadFile rf = new ReadFile(fileName);
        int[] arrOfValues = new int[2];
        encodedString = rf.getData(); //зашифрованная строка
        valueString = rf.getValues(); //размер массива

        String val="";
        String tempStr = valueString + " ";
        int c = 0;
        for (int i = 0; i < tempStr.length(); i++) {
            char ch = tempStr.charAt(i);
            if (Character.isDigit(ch)) //пока след. символ - цифра, запись в строку
                val = val + ch;
            else if (!val.equals("") && c < 2) { //если след. символ - не цифра, преобразование полученной строки в int
                arrOfValues[c] = Integer.parseInt(val);
                val = "";
                c++;
            }
        }

        x = arrOfValues[0];
        y = arrOfValues[1];
    }

    //заполнение поля клетками
    private void fillCells(){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                pattern[i][j] = new Cell();
            }
        }

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                turnedPattern[i][j] = new Cell();
            }
        }
    }

    //Поиск натуральных чисел в зашифрованной строке (необходимы для decode)
    private void findDigits() {
        String nums="";
        for (int i = 0; i < encodedString.length(); i++) {
            char ch = encodedString.charAt(i);
            if (Character.isDigit(ch)) //пока след. символ - цифра, запись в строку
                nums = nums + ch;
            else if (!nums.equals("")) { //если след. символ - не цифра, преобразование полученной строки в int
                cntDigits++;
                digits.add(Integer.parseInt(nums));
                nums = "";
            }
        }
    }

    //проверка на цифру
    private boolean isNumber(String str, int index){
        char[] c = {'0','1','2','3','4','5','6','7','8','9'};
        for (char value : c) {
            if (str.charAt(index) == value) {
                return true;
            }
        }
        return false;
    }

    //преобразование массива в транспонированный
    private void turn(){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                turnedPattern[i][j].setStatus(pattern[j][i].getStatus());
            }
        }
    }

    private void updateGen(){
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                pattern[i][j].updateStatus();
            }
        }
    }

    private void updateGenForTurned(){
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                turnedPattern[i][j].updateStatus();
            }
        }
    }
}