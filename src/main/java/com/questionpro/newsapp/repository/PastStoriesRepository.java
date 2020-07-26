package com.questionpro.newsapp.repository;

import com.questionpro.newsapp.entity.PastStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PastStoriesRepository extends JpaRepository<PastStory, UUID> {
}
