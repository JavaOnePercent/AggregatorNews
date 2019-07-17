package com.news.aggregator.controller.news;

import com.news.aggregator.database.entity.NewsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Controller
public class NewsController
{
    private final NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService)
    {
        this.newsService = newsService;
    }

    @RequestMapping(value="/", method = RequestMethod.GET)
    public String transactionMain()
    {
        return "index";
    }

    @RequestMapping(value="/downloadNews", method = RequestMethod.GET)
    @ResponseBody
    public List<NewsEntity> downloadNews(@RequestParam("rule") String rule, @RequestParam("source") Integer source)
    {
        return newsService.addNews(rule, source);
    }

    @RequestMapping(value="/getNews", method = RequestMethod.GET)
    @ResponseBody
    public Page<NewsEntity> getNews(@PageableDefault Pageable pageable)
    {
        return newsService.getNews(pageable);
    }

    @RequestMapping(value="/searchNews", method = RequestMethod.GET)
    @ResponseBody
    public Page<NewsEntity> searchByTitle(@RequestParam("title") String title, @PageableDefault Pageable pageable)
    {
        return newsService.getNewsByTitle(title, pageable);
    }
}
