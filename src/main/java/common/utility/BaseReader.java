package common.utility;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamSupport;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import common.validation.BaseValidatorSvc;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public abstract class BaseReader<T extends Object> extends ItemStreamSupport
    implements ItemReader<T> {
  
  protected abstract void validate(Object data) throws Exception;
  //get
  protected abstract Class<T> getTargetType();

  private String[] header;
  
  public void setHeader(String[]header){
    this.header = header;
  }


  public BaseReader(String[] header) {
//    this.setLinesToSkip(1);
//    this.setName("CSV-ReaderXXXXXX");
//
//    // String[] header = new String[] { "FirstName", "LastName", "dept" };
//    String[] header = new String[] { "id", "start_date",
//        "award_holder_id", "award_type_id" };
//    t
  }

  public LineMapper<T> createLineMapper(String[] header) {
    DefaultLineMapper<T> lineMapper = new DefaultLineMapper<>();
    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames(header);
    BeanWrapperFieldSetMapper<T> beanMapper = new BeanWrapperFieldSetMapper<>();
    beanMapper.setTargetType(this.getTargetType());
    lineMapper.setFieldSetMapper(beanMapper);
    lineMapper.setLineTokenizer(lineTokenizer);
    return lineMapper;
  }

  @Qualifier("repositoryItemReader")
  public RepositoryItemReader<T> repositoryItemReader(
      PagingAndSortingRepository<T, ?> repo, String methodName) {

    RepositoryItemReader<T> reader = new RepositoryItemReader<>();
    reader.setRepository(repo);
    reader.setMethodName(methodName);
    // reader.setPageSize(1);
    Map<String, Sort.Direction> sorts = new HashMap<>();
    sorts.put("id", Direction.ASC);
    reader.setSort(sorts);
    return reader;
  }

  @Override
  public T read() throws Exception, UnexpectedInputException, ParseException,
      NonTransientResourceException {
    
    T dataSet = null;
    //validate input
    this.validate(dataSet);
    return dataSet;
  }
  

  
  

  public BaseReader() {
    
  }

}
