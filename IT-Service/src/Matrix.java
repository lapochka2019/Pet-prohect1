import java.util.ArrayList;
import java.util.List;

public class Matrix {
    private  static final int SIZE = 3;
    private  static final int CONST = 15;
    private int[][]arr;// = new int [3][3];
    private int count;

    public Matrix(String [] strings) {
        arr = new int [SIZE][SIZE];
        /**преобразование строки в матрицу 3х3**/
        for (int i=0;i<SIZE;i++){
            String [] substring = strings[i].split(" ");
            for (int j = 0; j< SIZE; j++){
                arr[i][j] = Integer.parseInt(substring[j]);
            }
        }
        count=0;

    }

    /** основной метод **/
    public String Result(){
        //матрица "полу-магическая"
        if (isMagicMatrix()==true)
            return printArr() + "\n Стоимость матрицы:" + count;
        else {
            //находим дешевое решение для девятки
            forNine();
            //матрица "полу-магическая"?
            if (isMagicMatrix()==true) return printArr() + "\n Стоимость матрицы:" + count;
            else {
                while (isMagicMatrix()!=true){
                    List <Integer> temp_Mcount = new ArrayList<>();
                    List <Integer> temp_index = new ArrayList<>();
                    List <Integer> temp_a = new ArrayList<>();
                    List <Integer> to_change = new ArrayList<>();
                    int [][] temp_arr = new int[2][2];
                    int d;

                    //обрабатываем строки
                    for (int i=0;i<SIZE;i++){
                        if(i!=getIndex(9)[0]){
                            temp_Mcount.add(countMagicString(i));//получаем список сумм строк
                            temp_index.add(i);//получаем список номеров "открытых" строк
                        }
                        //получаем список элементов, котоыре можно менять местами
                        for (int j=0;j<SIZE; j++){
                            if (arr[i][j]!=1&&arr[i][j]!=2&&arr[i][j]!=4&&arr[i][j]!=5&&arr[i][j]!=9)
                                temp_a.add(arr[i][j]);
                        }
                    }
                    //преобразуем лист в массив
                    for (int i=0;i<2;i++){
                        for (int j=0;j<2;j++){
                            temp_arr[i][j]=temp_a.get(0);
                            temp_a.remove(0);

                        }
                    }
                    d = Delta(temp_Mcount);//получаем дельту строк
                    to_change = twoToChange(temp_arr,d);//получаем два элемента, которые нужно менять местами
                    if (to_change.isEmpty()!=true){
                        Change(getIndex(to_change.get(0)),getIndex(to_change.get(1)));//переставляем элементы
                        count+=d;
                    }else{
                        if (getIndex(1)[1]==getIndex(5)[1]) {
                            Change(getIndex(1), getIndex(5));
                            count += 4;
                        }
                        else if (getIndex(2)[1]==getIndex(4)[1]) {
                            Change(getIndex(2), getIndex(4));
                            count += 2;
                        }
                    }

                    temp_Mcount.clear();
                    temp_index.clear();
                    to_change.clear();
                    //обрабатываем столбцы
                    for (int i=0;i<SIZE;i++){
                        if(i!=getIndex(9)[1]){
                            temp_Mcount.add(countMagicColumn(i));//получаем список сумм столбцов
                            temp_index.add(i);//получаем список номеров "открытых" столбцов
                        }
                        //получаем список элементов, котоыре можно менять местами
                        for (int j=0;j<SIZE; j++){
                            if (arr[i][j]!=1&&arr[i][j]!=2&&arr[i][j]!=4&&arr[i][j]!=5&&arr[i][j]!=9)
                                temp_a.add(arr[i][j]);
                        }
                    }
                    //преобразуем лист в массив
                    for (int i=0;i<2;i++){
                        for (int j=0;j<2;j++){
                            temp_arr[i][j]=temp_a.get(temp_a.size()-1);
                            temp_a.remove(temp_a.size()-1);

                        }
                    }
                    d = Delta(temp_Mcount);//получаем дельту столбцов
                    to_change = twoToChange(temp_arr,d);//получаем два элемента, которые нужно менять местами
                    if (to_change.isEmpty()!=true){
                        Change(getIndex(to_change.get(0)),getIndex(to_change.get(1)));//переставляем элементы
                        count+=d;
                    }else{
                        if (getIndex(1)[0]==getIndex(5)[0]) {
                            Change(getIndex(1), getIndex(5));
                            count += 4;
                        }
                        else if (getIndex(2)[0]==getIndex(4)[0]) {
                            Change(getIndex(2), getIndex(4));
                            count += 2;
                        }
                    }
                }
            }
        }return printArr() + "\n Стоимость матрицы:" + count;
    }

    /** метод, расставляет 1 и 5, 2 и 4 для девятки с наименьшей стоимостью **/
    public void forNine(){
        // положение чисел в матрице
        //int [] nine_pos=new int[2];
        int [] one_pos=new int[2];
        int [] five_pos=new int[2];
        int [] two_pos=new int[2];
        int [] four_pos=new int[2];

        //переменные для перемещения элементов
        int temp_cost=0;
        int[] temp_f1 = new int [2];
        int[] temp_t1 = new int [2];
        int[] temp_f2 = new int [2];
        int[] temp_t2 = new int [2];
        boolean isString = false;
        int[] temp_number=new int[2];

        //находим положение 9, 1 5, 2 4 [i][j]
        //nine_pos=getIndex(9);
        one_pos=getIndex(1);
        five_pos=getIndex(5);
        two_pos=getIndex(2);
        four_pos=getIndex(4);

        /**сколько стоит подвинуть 1 и 5 в одну строку с 9?**/
        //выбираем, куда выгоднее двигать: в строку или в столбец
        if (Cost(getIndex(twoFromString(9)[0]),one_pos)+Cost(getIndex(twoFromString(9)[1]), five_pos)<Cost(getIndex(twoFromColumn(9)[0]),one_pos)+ Cost(getIndex(twoFromColumn(9)[1]),five_pos)
                ||Cost(getIndex(twoFromString(9)[0]),one_pos)+Cost(getIndex(twoFromString(9)[1]), five_pos)<Cost(getIndex(twoFromColumn (9)[0]),five_pos)+Cost(getIndex(twoFromColumn(9)[1]),one_pos)
                ||Cost(getIndex(twoFromString(9)[0]),five_pos)+Cost(getIndex(twoFromString(9)[1]),one_pos)< Cost(getIndex(twoFromColumn (9)[0]),five_pos)+Cost(getIndex(twoFromColumn(9)[1]),one_pos)
                ||Cost(getIndex(twoFromString(9)[0]),five_pos)+Cost(getIndex(twoFromString(9)[1]),one_pos)< Cost(getIndex(twoFromColumn(9)[0]),one_pos)+ Cost(getIndex(twoFromColumn(9)[1]),five_pos)) {
            temp_number=twoFromString(9);
            isString=true;//указывает, что перемещение в стркоу уже было
        }
        else
            temp_number = twoFromColumn(9);
        if (Cost(getIndex(temp_number[0]),one_pos)+Cost(getIndex(temp_number[1]),five_pos)<Cost(getIndex(temp_number[0]),five_pos)+Cost(getIndex(temp_number[1]),one_pos)){
            temp_f1 =getIndex(temp_number[0]);
            temp_t1 =one_pos;
            temp_f2 =getIndex(temp_number[1]);
            temp_t2 =five_pos;
            temp_cost = Cost(getIndex(temp_number[0]),one_pos)+Cost(getIndex(temp_number[1]),five_pos);
        }else{
            temp_f1 =getIndex(temp_number[0]);
            temp_t1 =five_pos;
            temp_f2 =getIndex(temp_number[1]);
            temp_t2 =one_pos;
            temp_cost = Cost(getIndex(temp_number[0]),five_pos)+Cost(getIndex(temp_number[1]),one_pos);
        }
        //двигаем элементы и считаем стоимость передвижения
        Change(temp_f1,temp_t1);
        Change(temp_f2,temp_t2);
        count+=temp_cost;

        if(isString==false){//если перемещение 1 и 5 было в столбик
            temp_number=twoFromString(9);
        }else {//если перемещение 1 и 5 было в строку
            temp_number = twoFromColumn(9);
        }
        if (Cost(getIndex(temp_number[0]),two_pos)+Cost(getIndex(temp_number[1]),four_pos)<Cost(getIndex(temp_number[0]),four_pos)+Cost(getIndex(temp_number[1]),two_pos)){
            temp_f1 =getIndex(temp_number[0]);
            temp_t1 =two_pos;
            temp_f2 =getIndex(temp_number[1]);
            temp_t2 =four_pos;
            temp_cost = Cost(getIndex(temp_number[0]),two_pos)+Cost(getIndex(temp_number[1]),four_pos);
        }else{
            temp_f1 =getIndex(temp_number[0]);
            temp_t1 =four_pos;
            temp_f2 =getIndex(temp_number[1]);
            temp_t2 =two_pos;
            temp_cost = Cost(getIndex(temp_number[0]),four_pos)+Cost(getIndex(temp_number[1]),two_pos);
        }
        //двигаем элементы и считаем стоимость передвижения
        Change(temp_f1,temp_t1);
        Change(temp_f2,temp_t2);
        count+=temp_cost;
    }

    /** найти элементы, котоырй нужно поменять местами **/
    public List <Integer> twoToChange(int [][] a, int d){
        List <Integer> t = new ArrayList<>();
        if (Math.abs(a[0][0]-a[1][0])==d){t.add(a[0][0]); t.add(a[1][0]);}
        if (Math.abs(a[0][0]-a[1][1])==d){t.add(a[0][0]); t.add(a[1][1]);}
        if (Math.abs(a[0][1]-a[1][0])==d){t.add(a[0][1]); t.add(a[1][0]);}
        if (Math.abs(a[0][1]-a[1][1])==d){t.add(a[0][1]); t.add(a[1][1]);}
        return t;
    }

    /** получить разницу между жвух строк **/
    public int Delta(List <Integer> list){
        return Math.abs(list.get(0)-list.get(1))/2;
    }

    /** вытащить два эелемента строки **/
    public int[] twoFromString(int num){
        int[] t = {0,0};
        for (int j=0;j<SIZE;j++){
            int temp = 0;
            if (arr[getIndex(num)[0]][j]!=num){
                temp=arr[getIndex(num)[0]][j];
                if (t[0]==0)t[0]=temp;
                else t[1]=temp;
            }
        }
        return t;
    }

    /** вытащить два элемента столбца **/
    public int[] twoFromColumn(int num){
        int[] t = {0,0};
        for (int i = 0; i < SIZE; i++) {
            int temp = 0;
            if (arr[i][getIndex(num)[1]] != num) {
                temp = arr[i][getIndex(num)[1]];
                if (t[0] == 0) t[0] = temp;
                else t[1] = temp;
            }
        }
        return t;
    }

    /** возращает стоимость перемещения однго элемента **/
    public int Cost( int [] false1, int [] true1){
        return Math.abs(getNumber(false1)-getNumber(true1));
    }

    /** ставит на место один эелемент мтарицы **/
    public void Change ( int [] false1,int [] true1){
        int t = arr[false1[0]][false1[1]];
        arr[false1[0]][false1[1]]=arr[true1[0]][true1[1]];
        arr[true1[0]][true1[1]] = t;
    }

    /** возвращает индекс эелемента матрицы **/
    public int[] getIndex(int number){
        int [] a = new int [2];
        for (int i =0;i<SIZE;i++) {
            for (int j = 0; j < SIZE; j++) {
                if (arr[i][j]==number){
                    a[0]=i;
                    a[1]=j;
                    break;
                }
            }
        }
        return a;
    }

    /**возвращает эелемент массива по индексу **/
    public int getNumber(int[] index){
        return arr[index[0]][index[1]];
    }

    /** на вход: номер строки, на выход: его сумму**/
    public int countMagicString(int number){
        int c = 0;
        for (int j=0;j<SIZE;j++){
            c+=arr[number][j];
        }
        return c;
    }

    /** на вход: номер стобца, на выход: его сумму **/
    public int countMagicColumn(int number){
        int c = 0;
        for (int i=0;i<SIZE;i++){
            c+=arr[i][number];
        }
        return c;
    }

    /** на вход: номер строки, на выход: равна ли сумма 15 **/
    public boolean isMagicString(int number){
        return countMagicString(number)==CONST;
    }

    /** на вход: номер стобца, на выход: равна ли сумма 15 **/
    public boolean isMagicColumn(int number){
        return countMagicColumn(number)==CONST;
    }

    /** вся матрица пол-магическая?**/
    public boolean isMagicMatrix(){
        return (isMagicString(0)==true&&isMagicString(1)==true&&isMagicString(2)==true
                &&isMagicColumn(0)==true&&isMagicColumn(1)==true&&isMagicColumn(2)==true);
    }

    public String printArr(){
        String str = "";
        for (int i =0;i<SIZE;i++){
            for (int j =0;j<SIZE;j++){
                str=str+arr[i][j] + " ";
            }
            str=str+"\n";
        }
        return str;
    }

}
