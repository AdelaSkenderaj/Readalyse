package com.readalyse.entities;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "JOB_EXECUTION_HISTORY")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class JobExecutionHistory extends AuditedEntity{

  @Id
  @Column(name = "JOB_ID")
  private String jobId;
}
