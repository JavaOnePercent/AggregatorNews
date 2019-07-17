package com.news.aggregator.database.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "news")
public class NewsEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;

    @Nullable
    @Column(name = "image")
    private String image;

    @Nullable
    @Lob
    @Column(name = "description", length=2000)
    private String description;

    @Column(name = "timestamp")
    private Long timestamp;

    @Column(name = "source")
    private Integer source;

    public NewsEntity()
    {

    }

    public NewsEntity(String title, String link, Long timestamp, Integer source)
    {
        this.title = title;
        this.link = link;
        this.timestamp = timestamp;
        this.source = source;
    }

    public NewsEntity(String title, String link, @Nullable String image, @Nullable String description, Long timestamp, Integer source)
    {
        this.title = title;
        this.link = link;
        this.image = image;
        this.description = description;
        this.timestamp = timestamp;
        this.source = source;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(Long timestamp)
    {
        this.timestamp = timestamp;
    }

    public Integer getSource()
    {
        return source;
    }

    public void setSource(Integer source)
    {
        this.source = source;
    }
}
