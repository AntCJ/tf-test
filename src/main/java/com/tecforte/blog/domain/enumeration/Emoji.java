package com.tecforte.blog.domain.enumeration;

/**
 * The Emoji enumeration.
 */
public enum Emoji {
    LIKE("POS"), HAHA("POS"), WOW("NEU"), SAD("NEG"), ANGRY("NEG");
	
	private String emotion;
	
	public String getEmotion() {
		return this.emotion;
    }
	
	Emoji(String emotion) {
		this.emotion = emotion;
	}
}
