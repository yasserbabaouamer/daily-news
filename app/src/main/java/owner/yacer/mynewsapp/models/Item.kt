package owner.yacer.mynewsapp.models

data class Item (
    val article: Article,
    var liked:Boolean =false
        )