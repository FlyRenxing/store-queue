package top.imzdx.storequeue.pojo;

public class SeckillOrderList {
     private String name;
     private String phone;
     private double discount;

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getPhone() {
          return phone;
     }

     public void setPhone(String phone) {
          this.phone = phone;
     }

     public double getDiscount() {
          return discount;
     }

     public void setDiscount(double discount) {
          this.discount = discount;
     }

     @Override
     public String toString() {
          return "SckillPublicList{" +
                  "name='" + name + '\'' +
                  ", phone='" + phone + '\'' +
                  ", discount=" + discount +
                  '}';
     }

     public SeckillOrderList(String name, String phone, double discount) {
          this.name = name;
          this.phone = phone;
          this.discount = discount;
     }

     public SeckillOrderList() {

     }
}
