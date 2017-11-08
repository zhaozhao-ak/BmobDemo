package com.zz.ak.demo.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

public class Person extends BmobObject {

	private String Name;  //姓名
	private Integer age;   //年龄
	private Boolean gender; //性别
	private String address; //地址
	private String email;   //邮箱
	private BmobFile pic;   //头像
	private BmobDate uploadTime; //更新的最新时间

	private String firstLetter;
	private String state;



	public BmobDate getUploadTime() {
		return uploadTime;
	}
	public void setUploadTime(BmobDate uploadTime) {
		this.uploadTime = uploadTime;
	}
	public Boolean getGender() {
		return gender;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Boolean isGender() {
		return gender;
	}
	public void setGender(Boolean gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public BmobFile getPic() {
		return pic;
	}
	public void setPic(BmobFile pic) {
		this.pic = pic;
	}

	public String getFirstLetter() {
		return firstLetter;
	}
	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}




//	private BankCard bankCard;		// 对应服务端的Object类型
//	private List<String> hobby;		// 对应服务端Array类型：String类型的集合
//private List<BankCard> cards;	// 对应服务端Array类型:Object类型的集合


//	public BankCard getBankCard() {
//		return bankCard;
//	}
//	public void setBankCard(BankCard bankCard) {
//		this.bankCard = bankCard;
//	}
//public List<String> getHobby() {
//	return hobby;
//}
//	public void setHobby(List<String> hobby) {
//		this.hobby = hobby;
//	}
//public List<BankCard> getCards() {
//	return cards;
//}
//	public void setCards(List<BankCard> cards) {
//		this.cards = cards;
//	}
}
