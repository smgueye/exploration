package io.mgueye.code_analysis.call_tracker;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
public class MethodCall {
  private String caller;
  private String callee;
  private int lineNumber;
  private List<String> parameterTypes;
}

