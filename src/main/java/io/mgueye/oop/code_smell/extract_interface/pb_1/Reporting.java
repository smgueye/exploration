package io.mgueye.oop.code_smell.extract_interface.pb_1;

import java.io.File;
import java.io.IOException;

public interface Reporting {

  File exportOrdersCsv(File out) throws IOException;
}
