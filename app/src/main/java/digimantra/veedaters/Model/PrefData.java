package digimantra.veedaters.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dmlabs on 9/1/18.
 */

public class PrefData implements Parcelable {
    private String name;
    private boolean isSelected;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public PrefData(String name, boolean isSelected) {

        this.name = name;
        this.isSelected = isSelected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeByte(this.isSelected ? (byte) 1 : (byte) 0);
    }

    protected PrefData(Parcel in) {
        this.name = in.readString();
        this.isSelected = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PrefData> CREATOR = new Parcelable.Creator<PrefData>() {
        @Override
        public PrefData createFromParcel(Parcel source) {
            return new PrefData(source);
        }

        @Override
        public PrefData[] newArray(int size) {
            return new PrefData[size];
        }
    };
}
