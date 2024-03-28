package com.starvision.view.center.models

data class CenterModels(val code:String,
                        val message:String,
                        val data:CenterData){
    data class CenterData(val PageCenter:ArrayList<PageData>){
        data class PageData(val MenuType:String,
                            val MenuTitle:String,
                            val LoadAPI:String,
                            val LoadPageDefault:String,
                            val BannerApp:ArrayList<BannerData>,
                            val IconApp:ArrayList<IconData>,
                            val NewsApp:ArrayList<NewsData>,
                            val LuckgameData:ArrayList<LuckyData>,
                            val PlayPlusData:ArrayList<PlayData>){
            data class BannerData(val bannerId:String,
                                  val bannerTitle:String,
                                  val bannerImage:String,
                                  val bannerLinkstoreapp:String,
                                  val bannerLinkkeyopenapp:String)
            data class IconData(val iconappId:String,
                                val iconappTitle:String,
                                val iconappImgicon:String,
                                val iconappLinkstoreapp:String,
                                val iconappLinkkeyopenapp:String)
            data class NewsData(val newsappId:String,
                                val newsappTitle:String,
                                val newsappImgNews:String,
                                val newsappUrlNews:String,
                                val newsappLinkstoreapp:String,
                                val newsappLinkkeyopenapp:String)
            data class LuckyData(val luckgameId:String,
                                 val luckgameTitle:String,
                                 val luckgameUrl:String)
            data class PlayData(val playplayId:String,
                                val playplayTitleHeader:String,
                                val playplayTitle:String,
                                val playplayUrl:String,
                                val playplayLinkstoreapp:String,
                                val playplayLinkkeyopenapp:String)
        }
    }

}

