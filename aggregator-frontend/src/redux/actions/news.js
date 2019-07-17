import axios from 'axios';
import {
    AGG_GET_NEWS,
    AGG_CLEAR_NEWS,
    AGG_SEARCH_NEWS,
    AGG_DOWNLOAD_NEWS
} from './types';

export const getNews = (itemPage) => dispatch => {
    axios.get('http://localhost:8080/getNews', {
        params: {
            page: itemPage
        }
    }).then(function (response) {
        let news = response.data.content;
        let pageNumber = response.data.number;
        let lastPage = response.data.totalPages;
        let newsList = {news: news, currentPage: pageNumber, lastPage: lastPage};
        dispatch({type: AGG_GET_NEWS, payload: newsList});
    })
        .catch(function (error) {
            console.log(error)
        });
};

export const searchNews = (title, itemPage) => dispatch => {
    axios.get('http://localhost:8080/searchNews', {
        params: {
            title: title,
            page: itemPage
        }
    }).then((response) => {
        let news = response.data.content;
        let pageNumber = response.data.number;
        let lastPage = response.data.totalPages;
        let newsList = {news: news, currentPage: pageNumber, lastPage: lastPage};
        dispatch({type: AGG_SEARCH_NEWS, payload: newsList});
    })
        .catch(function (error) {
            console.log(error)
        });
};

export const downloadNews = (rule, source) => dispatch => {
    axios.get('http://localhost:8080/downloadNews', {
        params: {
            rule: rule,
            source: source
        }
    }).then((response) => {
        let news = response.data;
        if(news.length === 0)
        {
            return;
        }
        dispatch({type: AGG_DOWNLOAD_NEWS, payload: news});
    })
        .catch(function (error) {
            console.log(error)
        });
};


export const clearNews = () => dispatch => {
    dispatch({type: AGG_CLEAR_NEWS});
};