package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem
import com.example.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {
    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(3) {
            add(
                FeedPost(id = it)
            )
        }
    }

    private val initialState = HomeScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val oldPosts = _feedPosts.value?.toMutableList() ?: mutableListOf()
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
        _feedPosts.value = oldPosts.apply {
            replaceAll {
                if (it.id == newFeedPost.id) {
                    newFeedPost
                } else {
                    it
                }
            }
        }


//        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
//        modifiedList.replaceAll { post ->
//            if (post == feedPost) {
//                val updateStatistic = post.statistics.toMutableList().apply {
//                    replaceAll { oldItem ->
//                        if (oldItem.type == item.type) {
//                            oldItem.copy(count = oldItem.count + 1)
//                        } else {
//                            oldItem
//                        }
//                    }
//                }
//                post.copy(statistics = updateStatistic)
//            } else {
//                post
//            }
//        }
//        _feedPosts.value = modifiedList
    }

    fun delete(model: FeedPost) {
        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(model)
        _feedPosts.value = modifiedList
    }
}

