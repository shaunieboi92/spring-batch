package common.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import common.model.Email;

public class CustomRepoReader implements ItemReader<Email> {

  private List<Email> studentData;
  private int nextStudentIndex;

  // public CustomRepoReader(PagingAndSortingRepository<T,?> repository, String
  // methodName) throws Exception{
  // this.setRepository(repository);
  // this.setMethodName(methodName);
  // Map<String, Sort.Direction> sorts = new HashMap<>();
  // sorts.put("id", Direction.ASC);
  // this.setSort(sorts);
  // super.afterPropertiesSet();
  // T object = super.read();
  // System.out.println(object.toString() + "is the object");
  //
  //
  // }

  public CustomRepoReader() {
    initialize();
  }

  private void initialize() {
    Email tony = new Email();
    tony.setAddress("test");
    tony.setName("shaun");

    Email g = new Email();
    g.setAddress("test");
    g.setName("shaunsda");

    Email b = new Email();
    b.setAddress("teseadat");
    b.setName("shausdan");

    studentData = Collections.unmodifiableList(Arrays.asList(tony, g, b));
    nextStudentIndex = 0;
  }

  @Override
  public Email read() throws Exception {
    Email nextStudent = null;

    if (nextStudentIndex < studentData.size()) {
      nextStudent = studentData.get(nextStudentIndex);
      nextStudentIndex++;
    }

    return nextStudent;
  }

}
