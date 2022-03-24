package ru.iu3.fclient;

interface TransactionEvents {
    //Получает номер попытки ввода PIN и сумму транзакции в String
    String enterPIN(int ptc, String amount);
    void transactionResult(boolean result);
}
