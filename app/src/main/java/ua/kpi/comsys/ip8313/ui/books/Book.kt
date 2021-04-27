package ua.kpi.comsys.ip8313.ui.books

class Book (val title: String,
            val subtitle: String,
            val isbn13: String,
            val price: String,
            val image: String,
            val authors: String? = null,
            val publisher: String? = null,
            val pages: String? = null,
            val year: String? = null,
            val rating: String? = null,
            val desc: String? = null) {
}