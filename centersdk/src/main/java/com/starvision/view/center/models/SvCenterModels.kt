package com.starvision.view.center.models

data class SvCenterModels(val code:String,
                          val message:String,
                          val data:CenterData){
    data class CenterData(val PageCenter:ArrayList<PageData>){
        data class PageData(val MenuType:String,
                            val MenuTitle:String,
                            val LoadAPI:String,
                            val LoadPageDefault:String,
                            val BannerApp:ArrayList<BannerData>,
                            val IconApp:ArrayList<IconData>,
                            val NewsApp:NewsData,
                            val LuckgameData:ArrayList<LuckyData>?,
                            val PlayPlusData:ArrayList<PlayData>?){
            data class BannerData(val bannerappId:String,
                                  val bannerappTitle:String,
                                  val bannerappImgicon:String,
                                  val bannerappImgbackground:String,
                                  val bannerappImgintroduce:String,
                                  val bannerappLinkstoreapp:String,
                                  val bannerappLinkstoregoogle:String,
                                  val bannerappLinkkeyopenapp:String,
                                  val bannerappdataintroduce:ArrayList<IntroduceData>,
                                  var boo:Boolean = false){
                data class IntroduceData(val DataBank:BankContData,
                                         val DataGb:GoldContData,
                                         val DataGo:GoldContData,
                                         val DataPump:OilContData,
                                         val DatarLottoStatic:LottoContData,
                                         val DatarCheckLotto:CheckLottoContData,
                                         val DatarZodice:ZodiacContData){
                    data class BankContData(val Bank:String,
                                            val Buy:String,
                                            val Sell:String,
                                            val Rate:String)
                    data class GoldContData(val Date:String,
                                            val Time:String,
                                            val Buy:String,
                                            val Sell:String)
                    data class OilContData(val Date:String,
                                           val priceOil:String,
                                           val Oil:String)
                    data class LottoContData(val suggest_date:String,
                                             val suggest_name:String,
                                             val top_second:String,
                                             val bottom_second:String,
                                             val top_third:String)
                    data class CheckLottoContData(val result_date:String,
                                                  val last_two:String,
                                                  val first:String)
                    data class ZodiacContData(val result_date:String)
                }
            }
            data class IconData(val iconappgroupId:String,
                                val iconappgroupname:String,
                                val iconappdatarow:ArrayList<IconDatarow>){
                data class IconDatarow(val iconappId:String,
                                       val iconappTitle:String,
                                       val iconappImgicon:String,
                                       val iconappLinkstoreapp:String,
                                       val iconappLinkstoregoogle:String,
                                       val iconappLinkkeyopenapp:String)
            }
            data class NewsData(val news:ArrayList<News>,
                                val pin:ArrayList<Pin>){
                data class News(val newsId:Int,
                                val newsappId:String,
                                val newsappTitle:String,
                                val newsappImgNews:String,
                                val newsappUrlNews:String,
                                val newsappLinkstoreapp:String,
                                val newsappLinkstoregoogle:String,
                                val newsappLinkkeyopenapp:String)
                data class Pin(val newsId:Int,
                               val newsappId:String,
                               val newsappTitle:String,
                               val newsappImgNews:String,
                               val newsappUrlNews:String,
                               val newsappLinkstoreapp:String,
                               val newsappLinkstoregoogle:String,
                               val newsappLinkkeyopenapp:String)
            }
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

