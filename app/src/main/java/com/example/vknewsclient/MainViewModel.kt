package com.example.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vknewsclient.domain.FeedPost
import com.example.vknewsclient.domain.StatisticItem

class MainViewModel : ViewModel() {
    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(3) {
            add(
                FeedPost(id = it)
            )
        }
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

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


        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll { post ->
            if (post == feedPost) {
                val updateStatistic = post.statistics.toMutableList().apply {
                    replaceAll { oldItem ->
                        if (oldItem.type == item.type) {
                            oldItem.copy(count = oldItem.count + 1)
                        } else {
                            oldItem
                        }
                    }
                }
                post.copy(statistics = updateStatistic)
            } else {
                post
            }
        }
        _feedPosts.value = modifiedList
    }

    fun delete(model: FeedPost) {
        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(model)
        _feedPosts.value = modifiedList
    }
}

