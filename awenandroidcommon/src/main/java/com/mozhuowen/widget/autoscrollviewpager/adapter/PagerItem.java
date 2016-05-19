package com.mozhuowen.widget.autoscrollviewpager.adapter;


import java.io.Serializable;

/**
 * Created by RxRead on 2015/9/24.
 */
public class PagerItem implements Serializable {
	private int position;
	private String name;
	private String image;
	private String desc;
	private String value;
	private int type;

	public void setPosition(int position) {
		this.position = position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getPosition() {
		return position;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
