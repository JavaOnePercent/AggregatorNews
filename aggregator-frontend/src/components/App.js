import React, { Component } from "react";
import { Provider, connect } from "react-redux";
import store from "../redux/store";
import { FaSearch, FaArrowLeft, FaDownload, FaCog } from "react-icons/fa";
import "../styles/app.css";
import { getNews, searchNews, downloadNews, clearNews } from "../redux/actions/news";
import moment from "moment";

class App extends Component {

    constructor(props) {
        super(props);
        this.state = {
            isShowSettingsTab: false,
            isSearched: false,
            inputValue: '',
            source: 0,
            format: '',
        }
        this.setRootRef = this.setRootRef.bind(this);
        this.handleScroll = this.handleScroll.bind(this);
        this.updateInputValue = this.updateInputValue.bind(this);
        this.handleKeyDown = this.handleKeyDown.bind(this);
        this.search = this.search.bind(this);
    }

    componentDidMount()
    {
        const { news, onGetNews } = this.props;
        onGetNews(news.currentPage);
        window.addEventListener('scroll', this.handleScroll, false);
    }

    componentWillUnmount() {
        window.removeEventListener('scroll', this.handleScroll, false);
    }

    showOrHiddenSettingsTab() {
        this.setState({isShowSettingsTab: !this.state.isShowSettingsTab});
        if(!this.state.isShowSettingsTab)
        {
            this.setState({source: 0, format: ''});
        }
    }

    setRootRef(element) {
        this.rootRef = element;
    }

    handleScroll() {
        const { rootRef } = this;
        const { innerHeight, scrollY } = window;
        const { offsetTop, scrollHeight } = rootRef;
        const { isSearched, inputValue } = this.state;
        const { news, onGetNews, onSearchNews } = this.props;
        if (innerHeight + scrollY > (offsetTop + scrollHeight) - 1 && news.currentPage !== news.lastPage)
        {
            if(isSearched)
            {
                onSearchNews(inputValue, news.currentPage + 1);
            }
            else
            {
                onGetNews(news.currentPage + 1);
            }
        }
    }

    back()
    {
        const { onGetNews, onClearNews } = this.props;
        onClearNews();
        this.setState({
            isSearched: false
        });
        onGetNews(0);
    }

    updateInputValue(event) {
        this.setState({
            inputValue: event.target.value
        });
    }

    handleKeyDown(e)
    {
        if (e.key === 'Enter')
        {
            this.search();
        }
    }

    search()
    {
        const { onSearchNews, onClearNews } = this.props;
        const { inputValue } = this.state;
        if(inputValue.trim() === '')
        {
            return;
        }
        onClearNews();
        this.setState({
            isSearched: true,
        });
        onSearchNews(inputValue.trim(), 0);
    }

    setFormat(format)
    {
        this.setState({
            format: format
        });
    }

    setSource(source)
    {
        this.setState({
            source: source
        });
    }

    downloadNews()
    {
        const { format, source } = this.state;
        const { onDownloadNews } = this.props;
        if(format === '' || source === 0)
        {
            alert('Сначала задайте параметры загрузки новостей');
            return;
        }
        this.back();
        onDownloadNews(format, source);
    }

    render() {
        const {isShowSettingsTab, isSearched, inputValue} = this.state;
        const { news } = this.props;
        const { setRootRef } = this;
        return (
            <Provider store={store}>
                <div className="header-container">
                    <div className="header">
                        <div className="logo">Aggregator</div>
                        <div className="search-box">
                            {
                                isSearched &&
                                <div className="search-button" onClick={() => this.back()}><FaArrowLeft/></div>
                            }
                            <input type="text" placeholder="Начните вводить заголовок новости..." value={inputValue} onKeyDown={this.handleKeyDown} onChange={this.updateInputValue}/>
                            <div className="search-button" onClick={this.search}><FaSearch/></div>
                        </div>
                        <div className="settings-button" onClick={() => this.showOrHiddenSettingsTab()}><FaCog size={24}/></div>
                    </div>
                    {
                        isShowSettingsTab &&
                        <div className="settings-tab">
                            <label className="checkbox-header">Издания:</label>
                            <div className="checkbox">
                                <input className="checkbox-input" name="edition" type="radio" id="checkbox-meduza" onChange={() => this.setSource(1)}/>
                                <label className="checkbox-label" htmlFor="checkbox-meduza">Meduza</label>
                            </div>
                            <div className="checkbox">
                                <input className="checkbox-input" name="edition" type="radio" id="checkbox-lenta" onChange={() => this.setSource(2)}/>
                                <label className="checkbox-label" htmlFor="checkbox-lenta">Lenta.ru</label>
                            </div>
                            <label className="radio-header">Формат:</label>
                            <div className="radio">
                                <input className="radio-input" name="format" type="radio" id="radio-short" onChange={() => this.setFormat('short')}/>
                                <label className="radio-label" htmlFor="radio-short">Кратко</label>
                            </div>
                            <div className="radio">
                                <input className="radio-input" name="format" type="radio" id="radio-detail" onChange={() => this.setFormat('detail')}/>
                                <label className="radio-label" htmlFor="radio-detail">Подробно</label>
                            </div>
                            <div className="download-button" onClick={() => this.downloadNews()}><FaDownload /></div>
                        </div>
                    }
                </div>
                <div className="news" ref={setRootRef}>
                    {
                        news.news.map((post, index) => (
                            <div className="post-detail" key={index}>
                                {
                                    post.description !== null && post.image !== null &&
                                        <div>
                                            <div className="post-description">{post.description}</div>
                                            <div className="post-img">
                                                <img src={post.image} width="600"/>
                                            </div>
                                        </div>
                                }
                                <a className="post-title" href={post.link}>{post.title}</a>
                                <div className="post-footer">
                                    <div>{moment(post.timestamp).format('MM-DD-YYYY HH:mm')}&nbsp;</div>
                                    {
                                        post.source === 1 &&
                                        <div>Meduza</div>
                                    }
                                    {
                                        post.source === 2 &&
                                        <div>Lenta.ru</div>
                                    }
                                </div>
                                <hr/>
                            </div>
                        ))
                    }
                </div>
            </Provider>
        )
    }
}

export default connect(
    state => ({news: state.news}),
    dispatch => ({
        onGetNews: (itemPage) => {
            dispatch(getNews(itemPage));
        },
        onSearchNews: (title, itemPage) => {
            dispatch(searchNews(title, itemPage));
        },
        onDownloadNews: (rule, source) => {
            dispatch(downloadNews(rule, source));
        },
        onClearNews: () => {
            dispatch(clearNews());
        }
    })
)(App)