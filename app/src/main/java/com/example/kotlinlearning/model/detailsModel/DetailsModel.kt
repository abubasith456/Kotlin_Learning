package com.example.kotlinlearning.model.detailsModel

class DetailsModel {
    var title: String?
    var description: String?
    var author: String?
    var dateAndTime: String?
    var urlToImage: String?
    var url: String?
    var content: String?

    constructor(
        title: String?,
        description: String?,
        author: String?,
        dateAndTime: String?,
        urlToImage: String?,
        url: String?,
        content: String?
    ) {
        this.title = title
        this.description = description
        this.author = author
        this.dateAndTime = dateAndTime
        this.urlToImage = urlToImage
        this.url = url
        this.content = content

    }
}