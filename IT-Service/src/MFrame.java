import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

import static javax.swing.JOptionPane.showMessageDialog;

public class MFrame extends JFrame{

    /** Элементы окна **/
    private JPanel frame;
    private JTextArea input_txt;
    private JTextArea output_txt;
    private JComboBox choose;
    private JButton res_btn;
    private JButton save_btn;
    private JButton dawn_brn;
    private JButton imp_btn;
    private JButton exp_btn;
    private JLabel inputL;
    private JLabel outputL;
    /** Поля класса **/
    private String s;
    private String[] input_str;
    private  JFileChooser fileChooser;

    public MFrame(){
        /** Окно **/
        setContentPane(frame);
        setTitle("Test Project");
        setSize(400,300);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        /** Комбо бокс **/
        choose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s = (String)choose.getSelectedItem();
                if (s.equals("Matrix")) output_txt.setText("Введите 9 цифр\nв следующем формате:\nx x x\nx x x\nx x x");
                else if (s.equals("Substring"))output_txt.setText("Введите две строки\nв следующем формате:\nслово слово слово\nслово слово слово\n*количество слов\nможет быть любым");
                else output_txt.setText("Выберите тип задачи");
            }
        });

        /**Текстовое окно**/
        output_txt.setText("Выберите тип задачи!");

        /** Кнопки **/
        // кнопка Посчитать
        res_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                s = (String)choose.getSelectedItem();
                input_str = input_txt.getText().split("\n");
                if (s.equals("Matrix")){
                    /**Матрица**/
                    //проверка на корректные данные
                    if (input_str.length==3){
                        int c = 0;
                        for (int i=0;i<3;i++){
                            String[] strings = input_str[i].split(" ");
                            if (strings.length==3) c++;
                        }
                        //если данные корректные, то выполняем расчет
                        if (c==3){
                            Matrix m = new Matrix(input_str);
                            output_txt.setText(m.Result());
                        } else output_txt.setText("Некорректные данные");
                    } else output_txt.setText("Некорректные данные");

                }/**Строка**/
                else if (s.equals("Substring")){
                    Substring substring = new Substring(input_str);
                    String[] strings = Substring.Cost.sort(substring.solver());
                    String out_string = "";
                    for (String str: strings){
                        out_string= out_string + str +" ";
                    }
                    output_txt.setText(out_string + " ");
                }
                else output_txt.setText("Выберите тип задачи");
            }
        });
        //кнопка Сахронить
        save_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
                fileChooser.setDialogTitle("Save a file");
                int res = fileChooser.showSaveDialog(null);
                if (res == JFileChooser.FILES_ONLY) {
                    String inText = input_txt.getText();
                    String outText = output_txt.getText();
                    File file = fileChooser.getSelectedFile();
                    try {
                        FileWriter fw = new FileWriter(file.getPath(), true);
                        fw.write("\n            ***            \n");
                        fw.write("Входные данные:\n");
                        fw.write(inText);
                        fw.write("\nРезультат:\n");
                        fw.write(outText);
                        fw.write("\nТип задачи:\n");
                        fw.write(s);
                        fw.flush();
                        fw.close();
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,"Error!");
                    }
                }
            }
        });
        //кнопка Экспортировать (Выгрузит результаты в файл)
        exp_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
                fileChooser.setDialogTitle("Get from file");
                String string="";
                int res = fileChooser.showSaveDialog(null);
                if (res == JFileChooser.FILES_ONLY) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        Scanner scan = new Scanner(file);
                        /**по-хорошему нужно написать класс ил хотя бы метод для проверки данных на корректный воод:
                         * наличие нескольких пробелов подряд
                         * наличие посторнних символов, наприер точек и прочего
                         * наличие лишник строк
                         * и т.д. **/
                        while (scan.hasNextLine()){
                            string=string+= scan.nextLine()+"\n";
                        }
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(null,"Error!");
                    }
                    input_txt.setText(string);
                }
            }
        });
    }

    public static void main (String[] arg){
        // Локализация компонентов окна JFileChooser
        UIManager.put(
                "FileChooser.saveButtonText", "Сохранить");
        UIManager.put(
                "FileChooser.cancelButtonText", "Отмена");
        UIManager.put(
                "FileChooser.fileNameLabelText", "Наименование файла");
        UIManager.put(
                "FileChooser.filesOfTypeLabelText", "Типы файлов");
        UIManager.put(
                "FileChooser.lookInLabelText", "Директория");
        UIManager.put(
                "FileChooser.saveInLabelText", "Сохранить в директории");
        UIManager.put(
                "FileChooser.folderNameLabelText", "Путь директории");

        MFrame Mframe = new MFrame();
        Mframe.setResizable(false);
    }
}
