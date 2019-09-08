package common.utility;

import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.core.io.FileSystemResource;

import common.service.impl.StringHeaderWriter;

public class BaseWriter<T extends Object> extends FlatFileItemWriter<T> {

  public BaseWriter(String outputFilePath) {
    String exportFileHeader = "author,isbn,title";
    StringHeaderWriter headerWriter = new StringHeaderWriter(exportFileHeader);
    this.setHeaderCallback(headerWriter);
    this.setResource(new FileSystemResource(outputFilePath));
    LineAggregator<T> lineAggregator = createPersonLineAggregator();
    this.setLineAggregator(lineAggregator);
  }

  private LineAggregator<T> createPersonLineAggregator() {
    DelimitedLineAggregator<T> lineAggregator = new DelimitedLineAggregator<>();
    lineAggregator.setDelimiter(",");// default delimiter is comma
    lineAggregator.setFieldExtractor(createStudentFieldExtractor());
    return lineAggregator;
  }

  private FieldExtractor<T> createStudentFieldExtractor() {
    BeanWrapperFieldExtractor<T> extractor = new BeanWrapperFieldExtractor<>();
    extractor.setNames(new String[] {"author", "isbn",
        "title" });
    return extractor;
  }
}
