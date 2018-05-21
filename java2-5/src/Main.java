public class Main extends Thread{

    public static void main(String[] args) {
        long a; //запишем сюда время
        int size = 10000000;
        float[] arr = new float[size];

        fill(arr); // заполняем массив единицами

        System.out.println("заполняем массив в один поток:");
        a = System.currentTimeMillis();
        changeValues(arr, 0);
        System.out.println(System.currentTimeMillis() - a + " millisec");


        fill(arr); // заполняем массив единицами

        System.out.println("заполняем массив в два потока:");
        a = System.currentTimeMillis();
        separateAndFill(arr);
        System.out.println(System.currentTimeMillis() - a + " millisec");
    }


    private static void fill(float[] arr){
        for (int i = 0; i < arr.length ; i++) {
            arr [i] = 1;
        }
    }

    private static void changeValues(float[] arr, int begin){

        for (int i = 0; i < arr.length ; i++) {
            int j = begin + i;
            arr [i] = (float)(arr[i] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
        }
    }

    private static void separateAndFill(float[] arr){
        int h = arr.length/2;
        float[] a1 = new float[h];
        float[] a2 = new float[h];

        //деления одного массива на два
        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        //меняем их значения новых массивов
        Thread thread1 = new Thread(() -> changeValues(a1, 0));
        Thread thread2 = new Thread(() -> changeValues(a2, h));


        try {
            thread1.start();
            thread2.start();
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //склеиваеи из 2 обратно один
        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);
    }

/*
Примечание:
System.arraycopy() – копирует данные из одного массива в другой:
System.arraycopy(массив-источник, откуда начинаем брать данные из массива-источника, массив-назначение, откуда начинаем
записывать данные в массив-назначение, сколько ячеек копируем)*/

}


