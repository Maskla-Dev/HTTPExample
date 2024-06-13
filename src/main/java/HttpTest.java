public class HttpTest {
    public static void main(String[] args) {
        try {
            Server server = new Server(8080);
            server.start();
        } catch (Exception e) {
            System.out.println("Something goes wrong at server initialization...");
        }
    }
}
