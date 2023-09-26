package com.akapps.simsimi.Models;

import java.io.Serializable;
import java.lang.String;

public class ResponseMessage implements Serializable {
  private String contact;

  private String ip;

  private String language;

  private Donate donate;

  private String time;

  private String text;

  private String id;

  private String message;

  private String status;

  public String getContact() {
    return this.contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getIp() {
    return this.ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public String getLanguage() {
    return this.language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Donate getDonate() {
    return this.donate;
  }

  public void setDonate(Donate donate) {
    this.donate = donate;
  }

  public String getTime() {
    return this.time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public static class Donate implements Serializable {
    private String paypal;

    public String getPaypal() {
      return this.paypal;
    }

    public void setPaypal(String paypal) {
      this.paypal = paypal;
    }
  }
}