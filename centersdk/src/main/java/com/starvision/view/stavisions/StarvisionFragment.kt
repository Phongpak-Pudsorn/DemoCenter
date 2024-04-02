package com.starvision.view.stavisions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starvision.luckygamesdk.R
import com.starvision.luckygamesdk.databinding.PageStarvisionBinding
import com.starvision.view.stavisions.adapter.AdapterStarvision
import com.starvision.view.stavisions.info.BannerInfo
import com.starvision.view.stavisions.info.IconInfo
import com.starvision.view.stavisions.info.NewsInfo

class StarvisionFragment:Fragment() {
    val binding : PageStarvisionBinding by lazy { PageStarvisionBinding.inflate(layoutInflater) }
    var newsList = ArrayList<NewsInfo>()
    var bannerList = ArrayList<BannerInfo>()
    var appList = ArrayList<IconInfo>()
//    var imageAdapter :AdapterImageSlide?=null
//    var appAdapter :AdapterApps?=null
//    var dotAdapter :AdapterDots?=null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsList = setNews()
        bannerList =setBanner()
        appList = setIcon()
        if (bannerList.size>=2) {
            bannerList.add(0, bannerList[bannerList.size - 1])
            bannerList.add(bannerList[1])
        }
//        dotAdapter = AdapterDots(bannerList)
//        imageAdapter = AdapterImageSlide(bannerList)
//        appAdapter = AdapterApps(bannerList)
        Log.e("newsList",newsList.size.toString())
        binding.rvMain.apply {
            adapter = AdapterStarvision(requireContext(),newsList, bannerList,appList)
            layoutManager = LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }
    private fun setNews(): ArrayList<NewsInfo> {
        newsList.clear()
        newsList.add(NewsInfo("banner","","","","",""))
        newsList.add(NewsInfo("header","","","","",""))
        newsList.add(NewsInfo("1","","News 1","","",""))
        newsList.add(NewsInfo("2","","News 2","","",""))
        newsList.add(NewsInfo("3","","News 3","","",""))
        newsList.add(NewsInfo("4","","News 4","","",""))
        newsList.add(NewsInfo("5","","News 5","","",""))
        newsList.add(NewsInfo("6","","News 6","","",""))
        newsList.add(NewsInfo("7","","News 7","","",""))
        newsList.add(NewsInfo("8","","News 8","","",""))
        newsList.add(NewsInfo("9","","News 9","","",""))
        newsList.add(NewsInfo("10","","News 10","","",""))
        return newsList
    }
    private fun setBanner(): ArrayList<BannerInfo> {
        bannerList.clear()
        bannerList.add(BannerInfo("1","The color of animals is by no means a matter of chance; it depends on many considerations, but in the majority of cases tends to protect the animal from danger by rendering it less conspicuous. Perhaps it may be said that if coloring is mainly protective, there ought to be but few brightly colored animals. There are, however, not a few cases in which vivid colors are themselves protective. The kingfisher itself","",getString(R.string.oil_package),""))
        bannerList.add(BannerInfo("2",", though so brightly colored, is by no means easy to see. The blue harmonizes with the water, and the bird as it darts along the stream looks almost like a flash of sunlight.\n" +
                "\n" +
                "Desert animals are generally the color of the desert. Thus, for instance, the lion, the antelope, and the wild donkey are all sand-colored. “Indeed,” says Canon Tristram,","",getString(R.string.exchange_package),""))
        bannerList.add(BannerInfo("3","“in the desert, where neither trees, brushwood, nor even undulation of the surface afford the slightest protection to its foes, a modification of color assimilated to that of the surrounding country is absolutely necessary. Hence, without exception, the upper plumage of every bird, and also the fur of all the smaller mammals and the skin of all the snakes and lizards, is of one uniform sand color.”","",getString(R.string.gold_package),""))
        bannerList.add(BannerInfo("4","The next point is the color of the mature caterpillars, some of which are brown. This probably makes the caterpillar even more conspicuous among the green leaves than would otherwise be the case. Let us see, then, whether the habits of the insect will throw any light upon the riddle. What would you do if you were a big caterpillar? Why, like most other defenseless creatures, you would feed by night, and lie concealed by day.","",getString(R.string.zodiac_package),""))
        bannerList.add(BannerInfo("5","So do these caterpillars. When the morning light comes, they creep down the stem of the food plant, and lie concealed among the thick herbage and dry sticks and leaves, near the ground, and it is obvious that under such circumstances the brown color really becomes a protection. It might indeed be argued that the caterpillars, having become brown, concealed themselves on the ground, and that we were reversing the state of things. ","",getString(R.string.lucky_package),""))
        bannerList.add(BannerInfo("6","But this is not so, because, while we may say as a general rule that large caterpillars feed by night and lie concealed by day, it is by no means always the case that they are brown; some of them still retaining the green color. We may then conclude that the habit of concealing themselves by day came first, and that the brown color is a later adaptation.","",getString(R.string.lottery_package),""))
        return bannerList
    }
    private fun setIcon():ArrayList<IconInfo>{
        appList.clear()
        appList.add(IconInfo("1","อัตราแลกเปลี่ยน","","",getString(R.string.exchange_package)))
        appList.add(IconInfo("2","ดูดวง 12 ราศี","","",getString(R.string.zodiac_package)))
        appList.add(IconInfo("3","ราคาทองวันนี้","","",getString(R.string.gold_package)))
        appList.add(IconInfo("4","ราคาน้ำมันวันนี้","","",getString(R.string.oil_package)))
        appList.add(IconInfo("5","เลขเด็ด หวยดัง","","",getString(R.string.lucky_package)))
        appList.add(IconInfo("6","ตรวจหวย","","",getString(R.string.lottery_package)))
        return appList
    }
}