import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    private void setupRestaurant() {
        String name = "Amelie's cafe";
        String location = "Chennai";
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");

        restaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant = spy(restaurant);
        LocalTime mock_time = LocalTime.parse("17:28:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(mock_time);
        boolean open_status  =restaurant.isRestaurantOpen();
        assertEquals(true, open_status);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        //WRITE UNIT TEST CASE HERE
        restaurant = spy(restaurant);
        LocalTime mock_time = LocalTime.parse("08:00:00");
        Mockito.when(restaurant.getCurrentTime()).thenReturn(mock_time);
        boolean open_status  =restaurant.isRestaurantOpen();
        assertEquals(false,open_status );
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Order<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void return_order_value_for_choosen_items(){

        List<String> chosenItems = new ArrayList<String>();
        chosenItems.add("Sweet corn soup");
        chosenItems.add("Vegetable lasagne");
        assertEquals(388,restaurant.get_total_order_price(chosenItems));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<Order>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>getDetails<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void checking_getDetails(){

        restaurant.displayDetails();

        assertEquals("Restaurant:Amelie's cafe\n" +
                "Location:Chennai\n" +
                "Opening time:10:30\n" +
                "Closing time:22:00\n" +
                "Menu:\n" +
                "[Sweet corn soup:119\n" +
                ", Vegetable lasagne:269\n" +
                "]", outputStreamCaptor.toString().trim());
    }
    //<<<<<<<<<<<<<<<<<<<<<<<getDetails>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}