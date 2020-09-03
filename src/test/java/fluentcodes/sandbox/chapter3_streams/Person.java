package fluentcodes.sandbox.chapter3_streams;

/**
 * Diese Klasse modelliert eine person auf simple Weise. Dient f�r viele
 * Beispiele als Grundlage.
 *
 * @author Michael Inden
 *
 * Copyright 2014 by Michael Inden
 */
public class Person {

    private final String name;

    private final int age;

    private final Gender gender;

    private String favoriteColor = null;

    public Person(final String name, final int age) {
        this(name, age, Gender.MALE);
    }

    public Person(final String name, final int age, final Gender gender) {
        this(name, age, gender, null);
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(final String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public Person(final String name, final int age, final Gender gender, final String favoriteColor) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.favoriteColor = favoriteColor;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public boolean isAdult() {
        return age >= 18;
    }

    @Override
    public String toString() {
        return "Person [name = " + name + " / age = " + age + " / favoriteColor = " + favoriteColor + "]";
    }
}
