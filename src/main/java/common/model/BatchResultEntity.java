package common.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import common.utility.BatchJobStepStatus;

@Entity
@Table(name = "BATCH_RESULTS")
public class BatchResultEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private long id;

  @Column(nullable = false, name = "BATCH_JOB_SERVICE_TYPE")
  @JsonProperty("job_service_type")
  private String jobSvcType;

  // Step Statuses
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "READER")
  @JsonProperty("reader")
  private BatchJobStepStatus readerStepStatus;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "PROCESSOR")
  @JsonProperty("processor")
  private BatchJobStepStatus processorStepStatus;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, name = "WRITER")
  @JsonProperty("writer")
  private BatchJobStepStatus writerStepStatus;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getJobSvcType() {
    return jobSvcType;
  }

  public void setJobSvcType(String jobSvcType) {
    this.jobSvcType = jobSvcType;
  }

  public BatchJobStepStatus getReaderStepStatus() {
    return readerStepStatus;
  }

  public void setReaderStepStatus(BatchJobStepStatus readerStepStatus) {
    this.readerStepStatus = readerStepStatus;
  }

  public BatchJobStepStatus getProcessorStepStatus() {
    return processorStepStatus;
  }

  public void setProcessorStepStatus(BatchJobStepStatus processorStepStatus) {
    this.processorStepStatus = processorStepStatus;
  }

  public BatchJobStepStatus getWriterStepStatus() {
    return writerStepStatus;
  }

  public void setWriterStepStatus(BatchJobStepStatus writerStepStatus) {
    this.writerStepStatus = writerStepStatus;
  }

}
