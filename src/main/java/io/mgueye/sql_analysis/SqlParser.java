package io.mgueye.sql_analysis;

import com.github.vertical_blank.sqlformatter.SqlFormatter;
import net.sf.jsqlparser.JSQLParserException;

public class SqlParser {

  public static void main(String[] args) throws JSQLParserException {
    String sqlStr = "select 1 from dual where a=b";
    // PlainSelect select = (PlainSelect) CCJSqlParserUtil.parse(sqlStr);
    String formattedSql = SqlFormatter.format("select * from dual;");
    System.out.println(formattedSql);
  }
}
