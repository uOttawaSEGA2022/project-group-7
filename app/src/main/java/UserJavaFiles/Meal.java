package UserJavaFiles;

public class Meal {
    //Instance variables
    private String name;
    private String description;
    private double price;
    private boolean offered;

    public Meal(String name, String description, double price)
    {
        this.name = name;
        this.description = description;
        this.price = price;

        //auto set meals to not offered and then when they wish to be offered make the on click call
        // method setOffered
        offered = false;
    }

    //getters
    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public double getPrice()
    {
        return price;
    }

    // if cook wishes to offer the meal pass true, if the cook no longer wishes to offer the meal pass false
    public void setOffered(boolean choice)
    {
        offered = choice;
    }

}


