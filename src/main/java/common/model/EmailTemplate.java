package common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "EMAIL_TEMPLATE")
public class EmailTemplate extends DateAudit {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", unique = true, nullable = false)
  private Long id;

  @Column(name = "NAME", nullable = false, length = 200)
  private String name;

  @Column(name = "PATH", nullable = false, length = 200)
  private String path;

  @Column(name = "DELETED", nullable = false)
  private short deleted;

  // new
  @Column(name = "EMAIL_SUBJECT", nullable = false)
  private String subject;
  
  //new
  @Column(name = "RECIPIENT", nullable = false, length = 200)
  private String recipient;
  
  //new, comma separated emails
  @Column(name = "CC", length = 1200)
  private String cc;

  //new, comma separated emails
  @Column(name = "BCC", length = 1200)
  private String bcc;

  public String getRecipient() {
    return recipient;
  }

  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public short getDeleted() {
    return deleted;
  }

  public void setDeleted(short deleted) {
    this.deleted = deleted;
  }
  
  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }
  
  public String getCc() {
    return cc;
  }

  public void setCc(String cc) {
    this.cc = cc;
  }

  public String getBcc() {
    return bcc;
  }

  public void setBcc(String bcc) {
    this.bcc = bcc;
  }


}
