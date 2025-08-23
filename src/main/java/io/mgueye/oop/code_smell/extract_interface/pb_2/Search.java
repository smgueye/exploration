package io.mgueye.oop.code_smell.extract_interface.pb_2;

import java.util.List;
import java.util.Optional;

public interface Search {
  Optional<String> findByEmail(String email);

  List<String> suggestByPrefix(String emailPrefix);
}
