package com.news.aggregator.controller.news;

import com.news.aggregator.database.entity.NewsEntity;
import com.news.aggregator.database.repository.NewsRepository;
import com.news.aggregator.parser.ParserRSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService
{
    private final NewsRepository newsRepository;
    private final ParserRSS parserRSS;

    @Autowired
    public NewsService(NewsRepository newsRepository, ParserRSS parserRSS)
    {
        this.newsRepository = newsRepository;
        this.parserRSS = parserRSS;
    }

    public List<NewsEntity> addNews(String rule, Integer source)
    {
        NewsEntity lastNews = newsRepository.findTop1BySourceOrderByTimestampDesc(source);
        String lastLink = null;
        if(lastNews != null)
        {
            lastLink = lastNews.getLink();
        }
        List<NewsEntity> newsEntity = parserRSS.parseNews(rule, source, lastLink);

        newsRepository.saveAll(newsEntity);

        return newsEntity;
    }

    public Page<NewsEntity> getNews(Pageable pageable)
    {
        return newsRepository.findAllByOrderByTimestampDesc(pageable);
    }

    public Page<NewsEntity> getNewsByTitle(String title, Pageable pageable)
    {
        return newsRepository.findByTitleContainingOrderByTimestampDesc(title, pageable);
    }
}
