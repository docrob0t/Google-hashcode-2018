/**
 * Model a location in a city.
 */
public class Location
{
    private int x;
    private int y;

    /**
     * Model a location in the city.
     * @param x The x coordinate. Must be positive.
     * @param y The y coordinate. Must be positive.
     * @throws IllegalArgumentException If a coordinate is negative.
     */
    public Location(int x, int y)
    {
        if(x < 0) {
            throw new IllegalArgumentException("Negative x-coordinate: " + x);
        }

        if(y < 0) {
            throw new IllegalArgumentException("Negative y-coordinate: " + y);
        }

        this.x = x;
        this.y = y;
    }

    /**
     * Determine the number of movements required to get from here to the destination.
     * @param destination The required destination.
     * @return The number of movement steps.
     */
    public int distanceTo(Location destination)
    {
        return Math.abs(destination.getX() - x) + Math.abs(destination.getY() - y);
    }

    /**
     * Implement content equality for locations.
     * @return true if this location matches the other,
     *         false otherwise.
     */
    public boolean equals(Object other)
    {
        if(other instanceof Location) {
            Location otherLocation = (Location) other;
            return x == otherLocation.getX() && y == otherLocation.getY();
        }
        else {
            return false;
        }
    }

    /**
     * Returns a string representation of the location.
     * @return A representation of the location.
     */
    public String toString()
    {
        return "[" + x + "," + y + "]" ;
    }

    /**
     * Use the top 16 bits for the y value and the bottom for the x.
     * Except for very big grids, this should give a unique hash code
     * for each (x, y) pair.
     *
     * Shifts binary y by 16 times to the left, then add x
     * @return A hashcode for the location.
     */
    public int hashCode()
    {
        return (y << 16) + x;
    }

    /**
     * Return the x coordinate
     * @return The x coordinate.
     */
    public int getX()
    {
        return x;
    }

    /**
     * Return the y coordinate.
     * @return The y coordinate.
     */
    public int getY()
    {
        return y;
    }
}