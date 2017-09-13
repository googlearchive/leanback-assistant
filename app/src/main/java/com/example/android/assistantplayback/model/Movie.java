/*
 * Copyright (c) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.assistantplayback.model;

import android.os.Parcel;
import android.os.Parcelable;

/** Captures metadata about a movie. */
public class Movie implements Parcelable {

    private int id;
    private String title;
    private String description;
    private String cardImage;
    private String backgroundImage;
    private String videoUrl;
    private String contentType;
    private boolean live;
    private int width;
    private int height;
    private String audioChannelConfig;
    private String purchasePrice;
    private String rentalPrice;
    private int ratingStyle;
    private double ratingScore;
    private int productionYear;
    private int duration;

    /**
     * Use {@link MovieBuilder} to construct a movie.
     *
     * @param id
     * @param title
     * @param description
     * @param cardImage
     * @param backgroundImage
     * @param contentType
     * @param live
     * @param width
     * @param height
     * @param audioChannelConfig
     * @param purchasePrice
     * @param rentalPrice
     * @param ratingStyle
     * @param ratingScore
     * @param productionYear
     * @param duration
     */
    Movie(
            int id,
            String title,
            String description,
            String cardImage,
            String backgroundImage,
            String videoUrl,
            String contentType,
            boolean live,
            int width,
            int height,
            String audioChannelConfig,
            String purchasePrice,
            String rentalPrice,
            int ratingStyle,
            double ratingScore,
            int productionYear,
            int duration) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cardImage = cardImage;
        this.backgroundImage = backgroundImage;
        this.videoUrl = videoUrl;
        this.contentType = contentType;
        this.live = live;
        this.width = width;
        this.height = height;
        this.audioChannelConfig = audioChannelConfig;
        this.purchasePrice = purchasePrice;
        this.rentalPrice = rentalPrice;
        this.ratingStyle = ratingStyle;
        this.ratingScore = ratingScore;
        this.productionYear = productionYear;
        this.duration = duration;
    }

    private Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        cardImage = in.readString();
        backgroundImage = in.readString();
        videoUrl = in.readString();
        contentType = in.readString();
        live = in.readInt() == 1;
        width = in.readInt();
        height = in.readInt();
        audioChannelConfig = in.readString();
        purchasePrice = in.readString();
        rentalPrice = in.readString();
        ratingStyle = in.readInt();
        ratingScore = in.readDouble();
        productionYear = in.readInt();
        duration = in.readInt();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getAudioChannelConfig() {
        return audioChannelConfig;
    }

    public void setAudioChannelConfig(String audioChannelConfig) {
        this.audioChannelConfig = audioChannelConfig;
    }

    public String getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public String getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public int getRatingStyle() {
        return ratingStyle;
    }

    public void setRatingStyle(int ratingStyle) {
        this.ratingStyle = ratingStyle;
    }

    public double getRatingScore() {
        return ratingScore;
    }

    public void setRatingScore(double ratingScore) {
        this.ratingScore = ratingScore;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(cardImage);
        dest.writeString(backgroundImage);
        dest.writeString(videoUrl);
        dest.writeString(contentType);
        dest.writeInt(live ? 1 : 0);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(audioChannelConfig);
        dest.writeString(purchasePrice);
        dest.writeString(rentalPrice);
        dest.writeInt(ratingStyle);
        dest.writeDouble(ratingScore);
        dest.writeInt(productionYear);
        dest.writeInt(duration);
    }

    public static final Creator<Movie> CREATOR =
            new Creator<Movie>() {
                @Override
                public Movie createFromParcel(Parcel in) {
                    return new Movie(in);
                }

                @Override
                public Movie[] newArray(int size) {
                    return new Movie[size];
                }
            };
}
