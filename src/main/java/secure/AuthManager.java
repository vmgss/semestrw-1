package secure;

public interface AuthManager {
    boolean authenticate(String email, String password);
}