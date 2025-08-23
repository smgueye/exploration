package io.mgueye.oop.code_smell.extract_interface.pb_2;

import java.util.Map;

public interface Analytics {
  Map<String, Object> getCohortMetrics();

  String exportAudit();
}
