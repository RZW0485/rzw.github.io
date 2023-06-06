package Design.dbutils.UDNI;

public class PowerBank {
    private String id; // 电池ID，由系统生成的唯一标识符
    private String power; // 电量大小
    private String status; // 电池状态：已借出/未借出
    private String price; // 电池租借单价

    public PowerBank(String id, String power, String status, String price) {
        this.id = id;
        this.power = power;
        this.status = status;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

}


