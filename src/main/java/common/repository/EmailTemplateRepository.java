package common.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import common.model.EmailTemplate;


@Transactional(rollbackFor=Exception.class)
@Repository
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

	public List<EmailTemplate> findByName(String name);
}
