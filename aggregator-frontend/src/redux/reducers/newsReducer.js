import {
    AGG_GET_NEWS,
    AGG_CLEAR_NEWS,
    AGG_SEARCH_NEWS,
    AGG_DOWNLOAD_NEWS
} from '../actions/types';

const initialState = {
    news: [],
    currentPage: 0,
    lastPage: 0
}

export function news(state = initialState, action) {
    switch(action.type)
    {
        case AGG_GET_NEWS:
        case AGG_SEARCH_NEWS:
            return {
                news: [...state.news.concat(action.payload.news)],
                currentPage: action.payload.currentPage,
                lastPage: action.payload.lastPage
            }
        case AGG_DOWNLOAD_NEWS:
            return {
                ...state,
                news: action.payload.concat(state.news)
            }
        case AGG_CLEAR_NEWS:
            return initialState
        default:
            return state;
    }
}
