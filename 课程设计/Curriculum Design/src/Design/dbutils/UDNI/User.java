package Design.dbutils.UDNI;

public class User {
    private String name = "";
    private String id = "";
    private String password = "";
    private double balance = 0;
    private String email = "";

    public User() {
        super();
    }

    public User(String name, String id, String password, double balance) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.balance = balance;
    }

    public User(String name, String id, String password, String email) {
        this.name = name;
        this.id = id;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
