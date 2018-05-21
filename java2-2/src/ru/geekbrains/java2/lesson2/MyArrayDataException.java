package ru.geekbrains.java2.lesson2;

public class MyArrayDataException extends MyArrayException {

    public MyArrayDataException(int row, int col) {
        super(String.format("Не удалось преобразовать данные массива  в [%d][%d]", row, col));
    }
}
