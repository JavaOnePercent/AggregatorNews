package com.news.aggregator.database.repository;

import com.news.aggregator.database.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<NewsEntity, Integer>
{
    Page<NewsEntity> findAllByOrderByTimestampDesc(Pageable pageable);
    Page<NewsEntity> findByTitleContainingOrderByTimestampDesc(String title, Pageable pageable);
    NewsEntity findTop1BySourceOrderByTimestampDesc(Integer source);
}
