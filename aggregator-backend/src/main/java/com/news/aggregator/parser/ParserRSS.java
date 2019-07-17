package com.news.aggregator.parser;

import com.news.aggregator.database.entity.NewsEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import static com.news.aggregator.parser.ParserRSSConstant.*;

@Component
public class ParserRSS
{
    @SuppressWarnings("unchecked")
    public List<NewsEntity> parseNews(String rule, Integer source, String lastLink)
    {
        List<NewsEntity> news = new ArrayList<>();
        try
        {
            String title = "";
            String link = "";
            String image = "";
            String description = "";
            Long timestamp = 0L;

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            inputFactory.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);

            InputStream in = getInputStream(source);
            XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

            while (eventReader.hasNext())
            {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement())
                {
                    String localPart = event.asStartElement().getName().getLocalPart();
                    switch (localPart)
                    {
                        case TITLE:
                            title = getDataFromTag(eventReader);
                            break;
                        case LINK:
                            link = getDataFromTag(eventReader);
                            if(link.equals(lastLink))
                            {
                                return news;
                            }
                            break;
                        case ENCLOSURE:
                            Iterator<Attribute> attribute = event.asStartElement().getAttributes();
                            while(attribute.hasNext())
                            {
                                Attribute urlAttribute = attribute.next();
                                if(urlAttribute.getName().toString().equals("url"))
                                {
                                    image = urlAttribute.getValue();
                                }
                            }
                            break;
                        case DESCRIPTION:
                            description = getDataFromTag(eventReader);
                            break;
                        case PUB_DATE:
                            String pubDate = getDataFromTag(eventReader);
                            timestamp = convertStringDateToTimestamp(pubDate);
                            break;
                    }
                }
                else if (event.isEndElement())
                {
                    if (event.asEndElement().getName().getLocalPart().equals(ITEM))
                    {

                        NewsEntity newsEntity;
                        if(rule.equals("detail"))
                        {
                            newsEntity = new NewsEntity(title, link, image, description, timestamp, source);
                        }
                        else
                        {
                            newsEntity = new NewsEntity(title, link, timestamp, source);
                        }

                        news.add(newsEntity);
                    }
                }
            }
        }
        catch (XMLStreamException e)
        {
            e.printStackTrace();
        }
        return news;
    }

    private String getDataFromTag(XMLEventReader eventReader)
    {
        String data = "";
        XMLEvent event = null;
        try
        {
            event = eventReader.nextEvent();
        }
        catch (XMLStreamException e)
        {
            e.printStackTrace();
        }
        if (event instanceof Characters)
        {
            data = event.asCharacters().getData();
            data = parseStringFromTag(data);
        }
        return data;
    }

    private InputStream getInputStream(Integer source)
    {
        try
        {
            return getUrl(source).openStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private URL getUrl(Integer source)
    {
        String link = "";

        switch (source)
        {
            case 1:
                link = "https://meduza.io/rss/all";
                break;
            case 2:
                link = "https://lenta.ru/rss/news";
                break;
            default:
                break;

        }

        URL url = null;
        try
        {
            url = new URL(link);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        return url;
    }

    private long convertStringDateToTimestamp(String stringDate)
    {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss ZZZ", Locale.ENGLISH);
        long timestamp = 0L;

        try
        {
            Date date = dateFormat.parse(stringDate);
            timestamp = date.getTime();
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return timestamp;
    }

    private String parseStringFromTag(String data)
    {
        String regex = "[^\\p{L}\\p{N}\\p{P}\\p{Blank}+]";
        Pattern pattern = Pattern.compile(regex, Pattern.UNICODE_CHARACTER_CLASS);
        Matcher matcher = pattern.matcher(data);
        data = matcher.replaceAll("");

        data = data.replace("br /", "");

        data = data.trim();

        return data;
    }
}