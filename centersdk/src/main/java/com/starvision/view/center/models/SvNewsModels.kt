package com.starvision.view.center.models

data class SvNewsModels(val code:String,
                        val message:String,
                        val data: DataNews){
    data class DataNews(val news : ArrayList<NewsInfo>,
                        val pin : ArrayList<PinInfo>) {
        data class NewsInfo(
            val newsId: Int,
            val newsappId: String,
            val newsappTitle: String,
            val newsappImgNews: String,
            val newsappUrlNews: String,
            val newsappLinkstoreapp: String,
            val newsappLinkstoregoogle: String,
            val newsappLinkkeyopenapp: String)

        data class PinInfo(
            val newsId: Int,
            val newsappId: String,
            val newsappTitle: String,
            val newsappImgNews: String,
            val newsappUrlNews: String,
            val newsappLinkstoreapp: String,
            val newsappLinkstoregoogle: String,
            val newsappLinkkeyopenapp: String)
    }
}
