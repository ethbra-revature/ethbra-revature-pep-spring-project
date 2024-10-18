package com.example.controller;

import lombok.Data;

/**
 * MessageDto
 */
 @Data
 public class MessageDto {

    private int postedBy;

    private String messageText;

    private long timePostedEpoch;

    private long accountId;
}