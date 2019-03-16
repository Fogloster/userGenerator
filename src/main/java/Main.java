public class Main {

    public static <string> void main(String[]args) throws Exception {
        Builder builder = new Builder();
        builder.generateData();

        RequestHTTP requestHTTP = new RequestHTTP();
        requestHTTP.callMe();

    }
}
