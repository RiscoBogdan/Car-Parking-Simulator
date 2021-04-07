package Parking;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParkingClient {
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");
    private final static Scanner scanner = new Scanner(System.in);
    private final static int spots = 10;

    public static void main(String[] args) {
        List<ParkArea> parking = new ArrayList<>();
        int parkNumber = 0;
        while (parkNumber < spots) {
            ++parkNumber;
            ParkArea eachSpot = new ParkArea(parkNumber);
            parking.add(eachSpot);
        }

        System.out.println("\n=====================================================");
        System.out.println("---->             1. Free Park Spots            <----");
        System.out.println("---->             2. Enter In Park              <----");
        System.out.println("---->             3. Leave & Pay                <----");
        System.out.println("---->             4. View Cars In Park          <----");
        System.out.println("---->             5. Exit Program               <----");
        System.out.println("=====================================================");
        System.out.println(" Park Price: \n Min. --  Lei\n 10   --  0.4\n 60   --  2.4\n 120  --  4.8");

        boolean startLoop = true;

        while (startLoop) {

            System.out.print("\nEnter Choice: ");
            int choise = scanner.nextInt();

            switch (choise) {
                case (1):
                    int numberOfFreeSpots = getNumberOfFreeSpots(parking);
                    System.out.println("\nNumber of total free Park spots are: " + numberOfFreeSpots);
                    break;
                case (2):
                    //check if park is empty
                    if (emptyPark(parking)) {
                        System.out.println("Number Example: CJ|00|AAA / B|000|AAA");
                        System.out.print("Your Car Plate: ");
                        String carNumber = scanner.next().toUpperCase();
                        String[] eachInput = carNumber.split("");
                        boolean plateLength = checkIfIsValidNumber(eachInput);

                        if (!alreadyACarNumber(parking, carNumber)) {//if is true mean i have already a car with that Number
                            if (!plateLength) {
                                System.out.println("------Invalid Car Number------");
                            } else {
                                System.out.print("Car Plate Number Entered: ");
                                printCarNumber(eachInput);

                                System.out.print("\nAvailable park spots: ");
                                //First check if there are free spots to park the car
                                //in listOfFreeSpots i have a list of remaining spots
                                ArrayList<Integer> listOfFreeSpots = getEachFreeSpot(parking);
                                for (Integer listOfFreeSpot : listOfFreeSpots) {
                                    wait(200);
                                    System.out.print(" |");
                                    System.out.print(listOfFreeSpot + "|");
                                }

                                System.out.print("\nEnter Spot: ");
                                int parkHere = scanner.nextInt();

                                if (listOfFreeSpots.contains(parkHere)) {
                                    System.out.print("\nHow many minutes? : ");
                                    double minutes = scanner.nextInt();
                                    parkTheCarAndGetPrice(carNumber, parkHere, parking, minutes);
//
                                } else {
                                    System.out.println("--->     Wrong Park Number     <---");
                                    System.out.println("--->   Might Be Already Used   <---");
                                    System.out.println("--->    Must be in [0 - 10]    <---");
                                }

                            }
                        } else {
                            System.out.println("--This Car Number Is Already In Park--");
                            System.out.println(" --Check If Number Is Correct Typed--");
                        }

                    } else {
                        System.out.println("\nPark is Full - Wait for someone to leave!");
                    }
                    break;
                case (3):
                    System.out.println("You want to leave ? Y/N");
                    String yesOrNo = scanner.next().toUpperCase();
                    switch (yesOrNo) {
                        case ("Y"):
                            System.out.print("Enter Car Number To Leave: ");
                            String carNumberLeave = scanner.next().toUpperCase();
                            boolean deleteCar = deleteCarFromPark(carNumberLeave, parking);
                            if (deleteCar) {
                                System.out.println("Car with number " + carNumberLeave + " left the park!");
                            } else {
                                System.out.println("Wrong Car Number");
                            }
                            break;

                        case ("N"):
                            break;
                    }
                    break;
                case (4):
                    viewAllCarsInPark(parking);
                    break;
                case (5):
                    System.out.println("You Left the Park...\nDrive Safe & Have a nice day!");
                    startLoop = false;
                    break;
            }
            wait(2000);
        }

    }

    public static boolean deleteCarFromPark(String carNumber, List<ParkArea> parking) {
        for (int i = 0; i < parking.size(); i++) {
            if (parking.get(i).getSpotInUse()) {
                if (parking.get(i).getCarPlateNumber().equals(carNumber)) {
                    parking.get(i).setSpotInUse(false);
                    return true;
                }
            }
        }
        return false;

    }

    public static void viewAllCarsInPark(List<ParkArea> parking) {
        List<ParkArea> carsParked = new ArrayList<>();
        for (int i = 0; i < parking.size(); i++) {
            if (parking.get(i).getSpotInUse()) {
                System.out.println("\nCar with Number: " + parking.get(i).getCarPlateNumber() + " ---> park Nr. "
                        + parking.get(i).getParkSpotNumber() + " ---> Ticket price: " + parking.get(i).getPrice() + " Lei " + " ---> Entered at: " + parking.get(i).getDateTime().format(formatter)
                );
                carsParked.add(parking.get(i));
            }
        }
        if (carsParked.size() == 0) {
            System.out.println("\nEmpty Park. Press 2 to enter in park!");
        }
    }

    public static boolean alreadyACarNumber(List<ParkArea> parking, String carNumber) {
        for (int i = 0; i < parking.size(); i++) {
            if (parking.get(i).getSpotInUse()) {
                if (parking.get(i).getCarPlateNumber().equals(carNumber)) {
                    return true;//if is true means i have a car with that number already
                }
            }
        }
        return false;

    }


    public static void printCarNumber(String[] plateNumber) {
        for (int i = 0; i < 7; i++) {
            if (plateNumber[1].matches("[0-9]+")) {
                if (i == 1 || i == 4) {
                    System.out.print(" ");
                    System.out.print(plateNumber[i]);
                } else {
                    System.out.print(plateNumber[i]);
                }
            } else {
                System.out.print(plateNumber[i]);
                if (i % 2 != 0 && i < 5) {
                    System.out.print(" ");
                }
            }
        }
    }

    public static boolean checkIfIsValidNumber(String[] plateNumber) {
        if (plateNumber.length == 7) {
            if (plateNumber[2].matches("[0-9]+") && plateNumber[3].matches("[0-9]+")) {
                return true;
            }
        }
        return false;
    }

    public static void wait(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static int getNumberOfFreeSpots(List<ParkArea> parking) {
        int counter = 0;
        boolean isOccupied;
        for (int i = 0; i < parking.size(); i++) {
            isOccupied = parking.get(i).getSpotInUse();// if spot at 1 is full, don't  count
            if (!isOccupied) {
                counter++;
            }
        }
        return counter;
    }

    public static ArrayList<Integer> getEachFreeSpot(List<ParkArea> parking) {
        ArrayList<Integer> parkSpots = new ArrayList<>();
        for (int i = 0; i < parking.size(); i++) {
            //if i is not occupied it will be put in ArrayList.
            if (!parking.get(i).getSpotInUse()) {
                parkSpots.add(parking.get(i).getParkSpotNumber());
            }
        }
        return parkSpots;
    }

    public static boolean emptyPark(List<ParkArea> parking) {
        for (int i = 0; i < parking.size(); i++) {
            //if there is at least 1 free spot
            if (!parking.get(i).getSpotInUse()) {
                return true;
            }
        }
        return false;
    }

    public static void parkTheCarAndGetPrice(String carNumber, int parkSpot, List<ParkArea> parking, double minutes) {
        if (minutes > 0) {
        ParkArea parkCar = new ParkArea(carNumber, parkSpot);
        parking.set(parkSpot - 1, parkCar);
            double price = minutes * 0.04;
            parkCar.setPrice(price);
            System.out.println(parkCar.getCarPlateNumber() + " is at: " + parkCar.getParkSpotNumber() + " --> On: " + parkCar.getDateTime().format(formatter) + " --> Ticket price is: " + parkCar.getPrice() + " Lei.");
        }else{
            System.out.println("You can't park for 0 or less minutes!");
        }
    }

}