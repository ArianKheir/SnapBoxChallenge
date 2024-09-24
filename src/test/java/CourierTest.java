import com.arvian.Courier;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class CourierTest {

    @Test
    void testDistanceCalculation() {
        double distance = Courier.dist(0, 0, 0, 1);
        assertEquals(111.19, distance, 0.01);
    }

    @Test
    void testSpeedCalculation() {
        Courier a = new Courier("1", 0, 0, 0);
        Courier b = new Courier("1", 0, 1, 3600);
        double speed = Courier.calcSpeed(a, b);
        assertEquals(111.19, speed, 0.01);
    }

    @Test
    void testFareCalculationWithMinimumFare() {
        ArrayList<Courier> couriers = new ArrayList<>();
        couriers.add(new Courier("1", 0, 0, 0));
        couriers.add(new Courier("1", 0, 0.01, 3600));

        double fare = Courier.calcFare(couriers);
        assertTrue(fare >= 3.47);
    }

    @Test
    void testFareCalculationWithHighSpeed() {
        ArrayList<Courier> couriers = new ArrayList<>();
        couriers.add(new Courier("1", 0, 0, 0));
        couriers.add(new Courier("1", 0, 10, 1800));

        double fare = Courier.calcFare(couriers);
        assertEquals(3.47, fare);
    }
}
