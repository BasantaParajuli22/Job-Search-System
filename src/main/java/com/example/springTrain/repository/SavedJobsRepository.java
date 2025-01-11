package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springTrain.entity.JobSeeker;
import com.example.springTrain.entity.SavedJobs;

@Repository
public interface SavedJobsRepository extends JpaRepository<SavedJobs,Long> {

	List<SavedJobs> findAllSavedJobsByJobSeeker(JobSeeker loggedinJobSeeker);

	SavedJobs findByJobPosting_JobIdAndJobSeeker_JobSeekerId(Long jobId, Long jobSeekerId);


}
