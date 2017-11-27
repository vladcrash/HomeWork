package ru.tinkoff.school.homework;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Влад on 27.11.2017.
 */

public class Payload {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("publicationDate")
    @Expose
    private PublicationDate publicationDate;
    @SerializedName("bankInfoTypeId")
    @Expose
    private int bankInfoTypeId;

    public Payload(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PublicationDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(PublicationDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getBankInfoTypeId() {
        return bankInfoTypeId;
    }

    public void setBankInfoTypeId(int bankInfoTypeId) {
        this.bankInfoTypeId = bankInfoTypeId;
    }
}
