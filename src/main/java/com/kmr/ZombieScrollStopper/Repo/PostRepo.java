package com.kmr.ZombieScrollStopper.Repo;

import com.kmr.ZombieScrollStopper.Model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
}
