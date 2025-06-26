package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.ui.theme.NewsFeedScreenState

class NewsFeedViewModel : ViewModel() {
    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(3) {
            add(
                FeedPost(id = it)
            )
        }
    }

//    private val comments = mutableListOf<PostComment>().apply {
//        repeat(10) {
//            add(PostComment(id = it))
//        }
//    }

    private val initialState = NewsFeedScreenState.Posts(posts = sourceList)
//    private var savedState: HomeScreenState? = initialState
    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

//    fun showComments(feedPost: FeedPost) {
//        savedState = _screenState.value
//        _screenState.value = HomeScreenState.Comments(comments = comments, feedPost = feedPost)
//    }
//
//    fun closeComments() {
//        _screenState.value = savedState ?: initialState
//    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        val oldStatistic = feedPost.statistics

        val newStatistic = oldStatistic.toMutableList().apply{
            replaceAll {oldItem ->
                if(oldItem.type == item.type) {
                    item.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistic)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(posts = newPosts)
    }

    fun remove(model: FeedPost) {
        val currentState = screenState.value
        if(currentState !is NewsFeedScreenState.Posts) return

        val oldPosts = currentState.posts.toMutableList()
        oldPosts.remove(model)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }
}

