package ra.config;

public class Message {
    public static String getStatusByCode(byte code){
        switch (code){
            case 0 :
                return "Waiting to confirm";
            case 1:
                return "Accepted";
            case 2:
                return "Canceled";
            default:
                return  "Invalid";
        }
    }
}
