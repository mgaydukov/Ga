import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadFile {
    private String values; //размер массива
    private String data; //данные массива

    public ReadFile(String fileName){
        readFile(fileName);
    }

    //чтение данных из файла
    public void readFile(String fileName) {
        System.getProperty("line.separator");
        File file = new File(fileName);
        Scanner sc;
        try {
            sc = new Scanner(file); // считывание файла
            values = sc.nextLine(); // считывание размера массива
            //считывание закодированных данных
            while (sc.hasNextLine()) {
                if(data == null){
                    data = sc.nextLine();
                }
                else{
                    data += sc.nextLine();
                }
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Необходим для получения размера массива
    public String getValues(){
        return values;
    }
    //Считывание зашифрованной строки без переноса строк, пробелов и т.д.
    public String getData(){
        data = data.replaceAll("\\n|\\r\\n", "");
        return data;
    }

}