package common.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import common.model.Award;
import common.model.Email;

@Component
public class EmailRepository implements BaseRepository {

  @Override
  public String getActualTbl() {
    return "email";
  }


}
