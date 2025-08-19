package io.mgueye.code_analysis.call_tracker;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;

@Data
public class CallGraphNode {

  private String methodName;
  private NodeList<Parameter> parameters;

  public CallGraphNode(String methodName, NodeList<Parameter> parameters) {
    this.methodName = methodName;
    this.parameters = parameters;
  }

  /*
  {
    id: "1",
    name: "Node 1",
    group: 1,
    width: 150,
    height: 80,
    attributes: [
      { key: "Type", value: "Server" },
      { key: "Status", value: "Active" },
      { key: "Load", value: "42%" },
    ],
  },
  */

  public String toJSON() throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    Map<String, Object> node = new HashMap<>();
    node.put("id", methodName);
    node.put("name", methodName);
    node.put("width", 200);
    node.put("height", 10);
    node.put("group", 1);
    node.put("attributes", parameters
      .stream()
      .map(parameter -> new HashMap<String, Object>() {{
        put("key", parameter.getNameAsString());
        put("value", parameter.getTypeAsString());
      }})
      .toList());
    return objectMapper.writeValueAsString(node);
  }

  @SneakyThrows
  @Override
  public String toString() {
    return toJSON();
  }
}
