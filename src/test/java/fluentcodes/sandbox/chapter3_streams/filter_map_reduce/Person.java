package fluentcodes.sandbox.chapter3_streams.filter_map_reduce;

import java.time.LocalDate;

/**
 * Beispielklasse zur Modellierung einer Person.
* 
 * @author Michael Inden
 * 
 * Copyright 2014 by Michael Inden
 */
public class Person
{
    final String         name;

    final LocalDate      birthday;

    private final Gender gender;

    private String       favoriteColor = null;

    public Person(String name, LocalDate birthday)
    {
        this(name, birthday, Gender.MALE);
    }

    public Person(final String name, final LocalDate birthday, final Gender gender)
    {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    public String getFavoriteColor()
    {
        return favoriteColor;
    }

    public void setFavoriteColor(final String favoriteColor)
    {
        this.favoriteColor = favoriteColor;
    }

    public String getName()
    {
        return name;
    }

    public LocalDate getBirthday()
    {
        return birthday;
    }

    public Gender getGender()
    {
        return gender;
    }

    @Override
    public String toString()
    {
        return "Person [name = " + name + " / birthday = " + birthday + " / favoriteColor = " + favoriteColor + "]";
    }
}
