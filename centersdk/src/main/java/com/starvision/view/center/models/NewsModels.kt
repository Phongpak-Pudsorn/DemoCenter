package com.starvision.view.center.models

data class NewsModels(val code:String,
                      val message:String,
                      val data: ArrayList<NewsInfo>){
    data class NewsInfo(val newsId:Int,
                        val newsappId:String,
                        val newsappTitle:String,
                        val newsappImgNews:String,
                        val newsappUrlNews:String,
                        val newsappLinkstoreapp:String,
                        val newsappLinkstoregoogle:String,
                        val newsappLinkkeyopenapp:String){

    }

}
