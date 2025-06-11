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

    private val _models = MutableLiveData<List<FeedPost>>(initialList)
    val models: LiveData<List<FeedPost>> = _models

    fun updateCount(model: FeedPost, item: StatisticItem) {
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.replaceAll { post ->
            if (post == model) {
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
        _models.value = modifiedList
    }

    fun delete(model: FeedPost) {
        val modifiedList = _models.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(model)
        _models.value = modifiedList
    }
}

