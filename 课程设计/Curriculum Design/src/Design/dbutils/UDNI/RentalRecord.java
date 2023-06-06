package Design.dbutils.UDNI;

    public class RentalRecord {
        private String id; // 订单ID，由系统生成的唯一标识符
        private String powerId; // 电池ID
        private String createTime; // 创建时间
        private String returnTime; // 归还时间
        private String duration; // 租借时长
        private String amount; // 租赁费用
        private String status; // 订单状态：已完成/待付款/待归还等状态

        public RentalRecord(String id, String powerId, String createTime, String returnTime, String duration, String amount, String status) {
            this.id = id;
            this.powerId = powerId;
            this.createTime = createTime;
            this.returnTime = returnTime;
            this.duration = duration;
            this.amount = amount;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPowerId() {
            return powerId;
        }

        public void setPowerId(String powerId) {
            this.powerId = powerId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(String returnTime) {
            this.returnTime = returnTime;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

