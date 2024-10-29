package com.example.springTrain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springTrain.table.SavedJobs;

public interface SavedJobsRepository extends JpaRepository<SavedJobs,Integer> {

	SavedJobs findBySavedIdAndJobSeeker_JobSeekerId(Integer jobId, Integer jobSeekerId);

}
