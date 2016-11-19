package com.botscrew.bothack.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.botscrew.bothack.converter.LocalDateTimeConverter;
import com.botscrew.bothack.messages.Style;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private Integer id;

	@Column(name = "chat_id")
	private String chatId;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(name = "timezone")
	private Integer timezone;

	@Column(name = "registered_at")
	@Convert(converter = LocalDateTimeConverter.class)
	private LocalDateTime registeredAt;

	@Column(name = "style")
	@Enumerated(EnumType.STRING)
	private Style style;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getChatId() {
		return chatId;
	}

	public void setChatId(String chatId) {
		this.chatId = chatId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}

	public LocalDateTime getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", chatId=" + chatId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", gender=" + gender + ", timezone=" + timezone + ", registeredAt=" + registeredAt + "]";
	}

}
