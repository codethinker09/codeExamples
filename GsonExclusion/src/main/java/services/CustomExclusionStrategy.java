package services;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

import annotation.CustomExclude;

public class CustomExclusionStrategy implements ExclusionStrategy {

	public boolean shouldSkipClass(Class<?> clazz) {
		return false;
	}

	public boolean shouldSkipField(FieldAttributes f) {
		return f.getAnnotation(CustomExclude.class) == null;
	}

}
