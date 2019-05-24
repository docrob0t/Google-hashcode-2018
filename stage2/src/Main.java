public class Main
{
    public static void main(String[] args) {
        String worldAndRidesFileName = args[0];
        World world = new World(worldAndRidesFileName);
        world.initialise();
        world.solve();
        world.printSolution();
    }
}