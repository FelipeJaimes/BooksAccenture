package com.example.android.booksaccenture.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book implements Parcelable {

    @SerializedName("ID")
    @Expose
    private Long iD;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("SubTitle")
    @Expose
    private String subTitle;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Image")
    @Expose
    private String image;
    @SerializedName("isbn")
    @Expose
    private String isbn;

    public Book(Long iD, String title, String subTitle, String description, String image, String isbn) {
        this.iD = iD;
        this.title = title;
        this.subTitle = subTitle;
        this.description = description;
        this.image = image;
        this.isbn = isbn;
    }

    public Long getID() {
        return iD;
    }

    public void setID(Long iD) {
        this.iD = iD;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.iD);
        dest.writeString(this.title);
        dest.writeString(this.subTitle);
        dest.writeString(this.description);
        dest.writeString(this.image);
        dest.writeString(this.isbn);
    }

    public Book() {
    }

    protected Book(Parcel in) {
        this.iD = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.subTitle = in.readString();
        this.description = in.readString();
        this.image = in.readString();
        this.isbn = in.readString();
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}