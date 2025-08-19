package com.example.lectureroomdatabase.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProductModel(

    @SerializedName("products") var products: ArrayList<Product> = arrayListOf(),
    @SerializedName("total") var total: Int = 0,
    @SerializedName("skip") var skip: Int = 0,
    @SerializedName("limit") var limit: Int = 0

) : Serializable

data class Product(

    @SerializedName("id") var id: Int = 0,
    @SerializedName("title") var title: String = "",
    @SerializedName("description") var description: String = "",
    @SerializedName("category") var category: String = "",
    @SerializedName("price") var price: Double? = null,
    @SerializedName("discountPercentage") var discountPercentage: Double? = null,
    @SerializedName("rating") var rating: Double? = null,
    @SerializedName("stock") var stock: Int = 0,
    @SerializedName("tags") var tags: ArrayList<String> = arrayListOf(),
    @SerializedName("brand") var brand: String = "",
    @SerializedName("sku") var sku: String = "",
    @SerializedName("weight") var weight: Int = 0,
    @SerializedName("warrantyInformation") var warrantyInformation: String = "",
    @SerializedName("shippingInformation") var shippingInformation: String = "",
    @SerializedName("availabilityStatus") var availabilityStatus: String = "",
    @SerializedName("reviews") var reviews: ArrayList<Reviews> = arrayListOf(),
    @SerializedName("returnPolicy") var returnPolicy: String = "",
    @SerializedName("minimumOrderQuantity") var minimumOrderQuantity: Int = 0,
    @SerializedName("meta") var meta: Meta? = Meta(),
    @SerializedName("images") var images: ArrayList<String> = arrayListOf(),
    @SerializedName("thumbnail") var thumbnail: String = ""

) : Serializable


data class Meta(

    @SerializedName("createdAt") var createdAt: String = "",
    @SerializedName("updatedAt") var updatedAt: String = "",
    @SerializedName("barcode") var barcode: String = "",
    @SerializedName("qrCode") var qrCode: String = ""

) : Serializable

data class Reviews(

    @SerializedName("rating") var rating: Int = 0,
    @SerializedName("comment") var comment: String = "",
    @SerializedName("date") var date: String = "",
    @SerializedName("reviewerName") var reviewerName: String = "",
    @SerializedName("reviewerEmail") var reviewerEmail: String = ""

) : Serializable


