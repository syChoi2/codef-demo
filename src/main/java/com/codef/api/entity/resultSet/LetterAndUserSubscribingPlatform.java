package com.codef.api.entity.resultSet;

import java.util.Date;

public interface LetterAndUserSubscribingPlatform{
	
	long getLetterId();
	
	long getPlatformId();
	String getPlatformName();
	String getPlatformImageUrl();
	int getIsSubscribing();
	long getBookmarkId();	//0 if not 
	
	String getTitle();
	String getThumbnailImageUrl();
	int getBookmarkCount();
	
	Date getCreatedAt();
	Date getModifiedAt();
	
	
}
