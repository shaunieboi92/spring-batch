package common.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "EMAIL")
@NamedQueries({
    @NamedQuery(name = "Email.findAll", query = "SELECT e FROM Email e") })
public class Email {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(name = "ID")
  private long id;

  @Column(nullable = false, name = "ADDRESS")
  @JsonProperty("address")
  private String address;

  @Column(nullable = false, name = "NAME")
  @JsonProperty("name")
  private String name;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Email() {

  }
}
