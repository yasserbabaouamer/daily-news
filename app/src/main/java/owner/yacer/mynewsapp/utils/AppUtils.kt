package owner.yacer.mynewsapp.utils

import owner.yacer.mynewsapp.models.Article

class AppUtils {
    companion object {
        fun mapperToArticle(map: Map<String, Any>): Article {
            return Article(
                map["author"] as? String,
                map["content"] as String,
                map["description"] as String,
                map["publishedAt"] as String,
                null,
                map["title"] as String,
                map["url"] as String,
                map["urlToImage"] as String
            )
        }
    }

}