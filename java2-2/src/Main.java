import ru.geekbrains.java2.lesson2.*;

import static java.lang.Integer.parseInt;

public class Main {

    public static void main(String[] args) {

	    String[][] normal = {{"1", "2", "3", "4"},
                            {"5", "6", "7", "8"},
                            {"9", "10","11","12"},
                            {"13","14","15","16"}
        };

        String[][] norow = {{"1", "2", "3", "4"},
                             {"5", "6", "7", "8"},
                             {"9", "10","11","12"},
                             {"13","14","15"}
        };

        String[][] noint = {{"1", "2", "fff", "4"},
                             {"5", "6", "7", "8"},
                             {"9", "10","11","12"},
                             {"13","14","15","16"}
        };


	    try {
	        System.out.println(sumArray(normal));
        }catch (MyArrayException e){
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(sumArray(norow));
        }catch (MyArrayException e){
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(sumArray(noint));
        }catch (MyArrayException e){
            System.out.println(e.getMessage());
        }
    }




    public static int sumArray(String[][] arr) throws MyArraySizeException, MyArrayDataException{
        int sum = 0;

        for (int i = 0; i < 4; ++i)
            if (arr.length != 4 || arr[i].length != 4) {
                 throw new MyArraySizeException();
            }

        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j){

                try {
                    sum += parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }

            }
        }
        return sum;
    }
}
