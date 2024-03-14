package com.starvision.view.center.sub.models

data class SubSmileLottoRewardModels(
    val Status : String,
    val lotto_id : Int,
    val result_date : String,

    val last_two : String,
    val last_three : String,
    val first : String,
    val near_one : String,
    val second : String,
    val third : String,
    val forth : String,
    val fifth : String,

    val link_list_lotto_result : String,
    val first_three : String,
    val form_new : Boolean,
    val qrcode_active : Boolean
)


//{
//    "Status": "True",
//    "lotto_id": 382,
//    "result_date": "2024-03-01",
//    "period": "",
//    "last_two": "79",
//    "last_three": "382 703",
//    "first": "253603",
//    "near_one": "253602 253604",
//    "second": "286578 329008 629052 883154 916831",
//    "third": "056295 079194 125604 224148 336549 544094 587666 619022 696224 741595",
//    "forth": "015041 021044 063059 080910 109194 132070 136841 158994 160137 229788 236305 267511 276157 289497 307823 325196 412827 429032 429117 492492 494061 502624 593091 596850 613975 664413 680314 685438 689167 697629 699980 700897 701004 712724 732775 738961 771338 773783 787548 794689 823052 824219 853578 854476 866816 887183 914093 961273 981305 992232",
//    "fifth": "001973 022732 026305 038241 039430 041914 071425 071820 077730 080391 095780 101722 133450 140673 142461 159677 161258 175354 181003 181477 187645 193138 204601 206636 210027 217436 222183 237690 240531 251627 262372 279682 283585 310154 320599 323297 328516 354731 375111 383414 388991 403364 411380 414872 421588 427431 438533 439241 452634 484976 488751 499583 515618 516340 524889 562203 571973 576070 579820 581380 606961 680988 689385 700340 708321 712999 713966 731758 734180 737691 739921 740687 756780 765909 770249 772218 776597 784599 786791 787456 794350 796763 800658 804122 862026 864583 898832 929510 936195 938033 943345 947024 950089 960799 961854 976534 985179 985962 986196 998450",
//    "creationdate": "2024-03-01 10:01:39.457325",
//    "modificationdate": "2024-03-01 17:05:07.229229",
//    "create_by": "Bae",
//    "user_id_create": 121,
//    "modify_by": "Bae",
//    "user_id_modify": 121,
//    "link_list_lotto_result": "https://starvision.in.th/mobileweb/img/lotto/c23133f210cedbc643e6688228cd7fac.pdf",
//    "first_three": "975 900",
//    "form_new": true,
//    "qrcode_active": true
//}
