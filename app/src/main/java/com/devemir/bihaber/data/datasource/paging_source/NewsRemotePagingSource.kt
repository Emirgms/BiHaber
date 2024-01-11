package com.devemir.bihaber.data.datasource.paging_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devemir.bihaber.data.datasource.RemoteNewsDataSource
import com.devemir.bihaber.data.model.Articles
import com.devemir.bihaber.util.Constants.DEFAULT_COUNTRY
import javax.inject.Inject

class NewsRemotePagingSource @Inject constructor(
    private val remoteNewsDataSource: RemoteNewsDataSource

):PagingSource<Int,Articles>(){
    override fun getRefreshKey(state: PagingState<Int, Articles>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
         }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Articles> {
        val page=params.key ?:STARTING_PAGE_INDEX
        return  try {
            val response=remoteNewsDataSource.getNewsHeadlines(DEFAULT_COUNTRY,page)
            val articles=response.body()?.articles ?: emptyList()
            LoadResult.Page(
                data = articles,
                prevKey = if (page== STARTING_PAGE_INDEX) null else page-1,
                nextKey = if (articles.isEmpty()) null else page+1
            )
        } catch (e:Exception){
            LoadResult.Error(e)
        }
    }

    companion object{
        const val STARTING_PAGE_INDEX=1
    }
}