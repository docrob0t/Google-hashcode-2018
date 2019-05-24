public class Main {

    public static void main(String[] args) {
        String worldAndRidesFileName = args[0];
        String allocationFileName = args[1];
        World world = new World(worldAndRidesFileName, allocationFileName);
        world.initialise();
        if (world.checkVehicleNo() != null) {
            world.printError(world.checkVehicleNo());
        }
        if (world.checkNoOfRides() != null) {
            world.printError(world.checkNoOfRides());
        }
        if (world.checkRideID() != null) {
            world.printError(world.checkRideID());
        }
        System.out.println(world.calculateScore());
    }
}