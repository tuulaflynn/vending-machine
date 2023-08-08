import service.Change;
import service.ChangeCoins;
import service.ItemService;
import service.ItemServiceImpl;
import view.ItemView;

public class VendingMachineMain {
    public static void main(String[] args) {
        // Create only one object of the view class to allow the main to call the view, which intern calls service -> dao.
        // Output is returned the opposite way; dao returns to the method which called it in the service layer,
        // which returns to method which called it in the view layer which then outputs info to the user,
        // it is also returned to where it is called in the main method but we have not utilised this in this program.
        ItemView itemView = new ItemView();


        itemView.readItemsFromFile();
        itemView.homeScreen();
    }
}
