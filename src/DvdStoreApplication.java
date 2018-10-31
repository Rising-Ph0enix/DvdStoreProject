import java.util.Scanner;

import com.ideas2it.dvdstore.controller.CustomerController;
import com.ideas2it.dvdstore.controller.DvdCollectionController;
import com.ideas2it.dvdstore.controller.GenreCatalogueController;
import com.ideas2it.dvdstore.controller.UserController;
import com.ideas2it.dvdstore.controller.PurchaseOrderController;
import com.ideas2it.dvdstore.filters.DvdFilter;

import static com.ideas2it.dvdstore.common.Constants.*;

/** 
 * Launches the Dvd Store Application and displays available operations menu
 * Gets the user's choice of operation and calls the relevant controller method 
 */
public class DvdStoreApplication {
    public static void main(String[] args) {
        int userChoice = 0;
        Scanner userInput = new Scanner(System.in);
        DvdCollectionController dvdCollectionController =
            new DvdCollectionController();
        GenreCatalogueController genreCatalogueController =
            new GenreCatalogueController();
        PurchaseOrderController purchaseOrderController = 
            new PurchaseOrderController();
        CustomerController customerController = 
            new CustomerController();

        do {
            System.out.println(DVD_STORE_CONSOLE);

            System.out.println("1. Customers");
            // This doesn't appear in the business logic - remove 
            System.out.println("2. Orders");
            System.out.println("3. Dvds");
            System.out.println("4. Genres");

            System.out.println(MENU_EXIT);

            try {
                userChoice = Integer.parseInt(userInput.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Choose a valid option from the menu");
            } 
            switch (userChoice) {
                case 1:
                    // customerController.displayMenu();
                    break;
                case 2:
                    purchaseOrderController.displayMenu();
                    break;
                case 3:
                    // dvdCollectionController.displayMenu();
                    break;
                case 4:
                    // genreCatalogueController.displayMenu();
                    break;
                case 5:
                    // loginController.
                default:
                    System.out.println(MENU_VALID_CHOICE);
            }
        } while (userChoice != 5);
    }
}


