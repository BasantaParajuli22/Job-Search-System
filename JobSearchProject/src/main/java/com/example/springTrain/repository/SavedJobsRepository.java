package com.example.springTrain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.table.JobSeeker;
import com.example.springTrain.table.SavedJobs;

public interface SavedJobsRepository extends JpaRepository<SavedJobs,Integer> {

	SavedJobs findBySavedIdAndJobSeeker_JobSeekerId(Integer jobId, Integer jobSeekerId);

	List<SavedJobs> findAllSavedJobsByJobSeeker(JobSeeker loggedinJobSeeker);

}
