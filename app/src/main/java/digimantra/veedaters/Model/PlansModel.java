package digimantra.veedaters.Model;

/**
 * Created by dmlabs on 4/1/18.
 */

public class PlansModel {
    private String price;
    private String duration;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public PlansModel(String price, String duration) {
        this.price = price;
        this.duration = duration;
    }
}
