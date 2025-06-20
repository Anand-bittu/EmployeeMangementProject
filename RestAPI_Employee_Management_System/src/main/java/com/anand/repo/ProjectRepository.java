package com.anand.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anand.entity.Project;

public interface ProjectRepository extends JpaRepository<Project,String> {

}
