package io.mgueye.oop.code_smell.extract_interface.pb_1;

public interface Persistence {

  void openDbConnection();
  void closeDbConnection();
  void saveToDb(OrderManager.Order o);
}
