package com.example.springTrain.util;

import java.util.Arrays;


/***
 * fromSentenceCase(String sentenceCaseString, Class<E> enumClass):
 *  This method takes a sentence-cased string (e.g., "My Example Enum")
 *   and an enum class and attempts to find the corresponding enum constant.
 *  It returns the matching enum constant or null if no match is found.
 *  
 *  Generics <E extends Enum<E>>: This makes the method reusable for different enum types. 
 *  E is a type variable that represents any class that is an enum.
 *  Class<E> enumClass: Represents the class object for the enum type.
 *  
 *  toSentenceCase(String enumName): 
 *  This method takes an enum constant's name (e.g., "MY_EXAMPLE_ENUM")
 *   and converts it to a sentence-cased string (e.g., "My Example Enum").
 */
public class EnumConverter {

  public static <E extends Enum<E>> E fromSentenceCase(
		  String sentenceCaseString, Class<E> enumClass) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(e -> toSentenceCase(e.name()).equalsIgnoreCase(sentenceCaseString))
                .findFirst()
                .orElse(null);
    }

	
    public static String toSentenceCase(String enumName) {
        if (enumName == null || enumName.isEmpty()) {
            return enumName;
        }
        String[] words = enumName.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }
        return result.toString().trim();
    }
}