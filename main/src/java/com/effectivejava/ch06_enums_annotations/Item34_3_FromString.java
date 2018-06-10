package com.effectivejava.ch06_enums_annotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * If you override the `toString` method in an enum type,
 * consider writing a `fromString` method to translate the custom string representation
 * back to the corresponding enum.
 */
public enum Item34_3_FromString {
	PLUS("+"),
	MINUS("-"),
	TIMES("*"),
	DIVIDE("/");

	private final String symbol;

	Item34_3_FromString(String symbol) {
		this.symbol = symbol;

		// Q: have each constant put itself into a map from its own constructor?
		// A: Enum constructors aren’t permitted to access the enum’s static fields
		// stringToEnum2.put(this.toString(), this);
	}

	@Override
	public String toString() {
		return symbol;
	}

	public Optional<Item34_3_FromString> fromString(String symbol) {
		return Optional.ofNullable(stringToEnum.get(symbol));
	}

	// EffectiveJava 3rd Edition (>= Java 8)
	private static final Map<String, Item34_3_FromString> stringToEnum =
		Stream.of(values()).collect(toMap(Object::toString, e -> e));

	// EffectiveJava 2nd Edition (< Java 8)
	private static final Map<String, Item34_3_FromString> stringToEnum2 = new HashMap<>();
	static {
		for (Item34_3_FromString item : values()) {
			stringToEnum2.put(item.toString(), item);
		}
	}



}


