package com.readalyse.repositories;

import com.readalyse.entities.JobExecutionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionHistoryRepository extends JpaRepository<JobExecutionHistory, String> {}
