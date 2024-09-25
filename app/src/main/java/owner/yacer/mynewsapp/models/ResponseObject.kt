package owner.yacer.mynewsapp.models

data class ResponseObject(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)