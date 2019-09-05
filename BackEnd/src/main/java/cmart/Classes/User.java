package cmart.Classes;

public class User
{
    public String first;
    public String last;
    public int id;
    // Whatever else a user profile might need?

    public User(String first,
                String last,
                int id)
    {
        this.first = first;
        this.last = last;
        this.id = id;
    }

    // Getters
    public int getId()                      { return this.id; }
    public String getName()                 { return this.first + this.last; }
    public String getFirst()                { return this.first; }
    public String getLast()                 { return this.last; }

    // Setters
    public void setId(int id)               { this.id = id; }
    public void setFirst(String first)      { this.first = first; }
    public void setLast(String last)        { this.last = last; }
    public void setName(String first, 
                        String last)
    {
        this.first = first;
        this.last = last;
    }
}