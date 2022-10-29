import java.util.ArrayList;
import java.util.List;

public class Substring {

    private String[] sub_str;
    private String[] original_str;

    public Substring(String[] string){
        sub_str = string[0].split(" ");
        original_str = string[1].split(" ");
    }

    public List <Cost> solver(){
        List <Cost> list = new ArrayList<>();
        int count = 0;
        for (String sub: sub_str) {
            for (String s: original_str) {
                if (s.contains(sub)==true) count++;
            }
            if (count!=0)list.add(new Cost(sub,count));
            count=0;
        }
        return list;
    }

    /** Класс, объединяюзий слово (из подстрок) и количество его вхождений в массив строк **/
    static class Cost {
    String word;
    int count;

        public Cost(String word, int count) {
            this.word = word;
            this.count = count;
        }

        public String getWord() {
            return word;
        }

        public int getCount() {
            return count;
        }

        /** По сути главный решающий метод **/
        public static String[] sort (List <Cost> list){
            if (list.isEmpty()==true){
                String[] ret = {"no substrings"};
                return ret;
            }
            //сортируем нам список по убыванию
            List <Cost> sorted = new ArrayList<>();
            sorted.add(list.get(0));
            for (int i=1;i<list.size();i++){
                if (list.get(i).count>=sorted.get(i-1).count) sorted.add(i-1,list.get(i));
                else sorted.add(list.get(i));
            }
            //вытаскиеваем из отсортированного списка слова
            String[] ret = new String[list.size()];
            for (int i=0;i<list.size();i++){
                ret[i]=list.get(i).word;
            }
            return ret;
        }

    }

}
