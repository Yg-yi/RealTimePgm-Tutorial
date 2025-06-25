import java.util.concurrent.*;

public class FlightBookingSystem {
    // 1. Simulate three booking agencies (using AGENCY_COUNT = 3)
    private static final int AGENCY_COUNT = 3;

    // 3. Use CyclicBarrier to synchronize agencies with a barrier action
    private static final CyclicBarrier barrier = new CyclicBarrier(AGENCY_COUNT, () -> {
        // 4. Barrier action: Runs when all agencies reach the barrier
        System.out.println("All agencies have selected seats. Confirming bookings...");
        try {
            // Simulate confirmation by sleeping for 1 second
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
        System.out.println("Bookings confirmed!\n");
    });

    public static void main(String[] args) {
        System.out.println("Starting booking process...\n");
        // 1. Create threads for each booking agency
        for (int i = 1; i <= AGENCY_COUNT; i++) {
            new Thread(new BookingAgency("Agency-" + i)).start();
        }
    }

    static class BookingAgency implements Runnable {
        private final String agencyName;

        public BookingAgency(String agencyName) {
            this.agencyName = agencyName;
        }

        @Override
        public void run() {
            try {
                // 2a. Print message indicating seat selection
                System.out.println(agencyName + " is selecting seats...");

                // 2b. Simulate seat selection with random sleep (0-3 seconds)
                Thread.sleep((int) (Math.random() * 3000));

                // 2c. Print message after selection, waiting for others
                System.out.println(agencyName + " finished selecting seats. Waiting for others...");

                // 3. Wait at the barrier for all agencies
                barrier.await();

                // 5. Print message after passing the barrier
                System.out.println(agencyName + " proceeds after confirmation.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
