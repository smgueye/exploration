package io.mgueye.oop.code_smell.extract_interface.pb_1;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

public class ReportingJob {
  private final Reporting reporting;

  public ReportingJob(Reporting reporting) {
    this.reporting = reporting;
  }

  public File nightlyExport(File dir) throws IOException, IOException {
    File out = new File(dir, "orders-" + LocalDate.now() + ".csv");
    return reporting.exportOrdersCsv(out);
  }
}
