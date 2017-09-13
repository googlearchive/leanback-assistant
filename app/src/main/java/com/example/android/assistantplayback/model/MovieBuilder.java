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

/** Builds a {@link Movie} instance. */
public class MovieBuilder {
    private int id;
    private String title;
    private String description;
    private String cardImage;
    private String backgroundImage;
    private String contentType;
    private boolean live = false;
    private int width;
    private int height;
    private String audioChannelConfig;
    private String purchasePrice;
    private String rentalPrice;
    private int ratingStyle;
    private double ratingScore;
    private int productionYear;
    private int duration;
    private String videoUrl;

    public MovieBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public MovieBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public MovieBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public MovieBuilder setCardImage(String cardImage) {
        this.cardImage = cardImage;
        return this;
    }

    public MovieBuilder setBackgroundImage(String backgroundImage) {
        this.backgroundImage = backgroundImage;
        return this;
    }

    public MovieBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public MovieBuilder setLive(boolean live) {
        this.live = live;
        return this;
    }

    public MovieBuilder setWidth(int width) {
        this.width = width;
        return this;
    }

    public MovieBuilder setHeight(int height) {
        this.height = height;
        return this;
    }

    public MovieBuilder setAudioChannelConfig(String audioChannelConfig) {
        this.audioChannelConfig = audioChannelConfig;
        return this;
    }

    public MovieBuilder setPurchasePrice(String purchasePrice) {
        this.purchasePrice = purchasePrice;
        return this;
    }

    public MovieBuilder setRentalPrice(String rentalPrice) {
        this.rentalPrice = rentalPrice;
        return this;
    }

    public MovieBuilder setRatingStyle(int ratingStyle) {
        this.ratingStyle = ratingStyle;
        return this;
    }

    public MovieBuilder setRatingScore(double ratingScore) {
        this.ratingScore = ratingScore;
        return this;
    }

    public MovieBuilder setProductionYear(int productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public MovieBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public MovieBuilder setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public Movie createMovie() {
        return new Movie(
                id,
                title,
                description,
                cardImage,
                backgroundImage,
                videoUrl,
                contentType,
                live,
                width,
                height,
                audioChannelConfig,
                purchasePrice,
                rentalPrice,
                ratingStyle,
                ratingScore,
                productionYear,
                duration);
    }
}
