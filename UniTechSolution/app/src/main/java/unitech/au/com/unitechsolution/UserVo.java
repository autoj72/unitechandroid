package unitech.au.com.unitechsolution;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by autoj on 2017-03-15.
 */
public class UserVo implements Parcelable {
    public String ID = null;
    public String PASS = null;
    public String NAME  = null;
    public String ADDRESS = null;
    public String DOB = null;
    public String MOBILE = null;
    public String GENDER = null;
    public String EMOBILE = null;
    public String REG_DATE = null;

    public String getREG_DATE() {
        return REG_DATE;
    }

    public void setREG_DATE(String REG_DATE) {
        this.REG_DATE = REG_DATE;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPASS() {
        return PASS;
    }

    public void setPASS(String PASS) {
        this.PASS = PASS;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getMOBILE() {
        return MOBILE;
    }

    public void setMOBILE(String MOBILE) {
        this.MOBILE = MOBILE;
    }

    public String getGENDER() {
        return GENDER;
    }

    public void setGENDER(String GENDER) {
        this.GENDER = GENDER;
    }

    public String getEMOBILE() {
        return EMOBILE;
    }

    public void setEMOBILE(String EMOBILE) {
        this.EMOBILE = EMOBILE;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.ID);
        dest.writeString(this.PASS);
        dest.writeString(this.NAME);
        dest.writeString(this.ADDRESS);
        dest.writeString(this.REG_DATE);
        dest.writeString(this.DOB);
        dest.writeString(this.MOBILE);
        dest.writeString(this.EMOBILE);
        dest.writeString(this.GENDER);
    }
    public UserVo() {super();}
    public UserVo(Parcel in){
        this.ID = in.readString();
        this.PASS = in.readString();
        this.NAME = in.readString();
        this.ADDRESS = in.readString();
        this.REG_DATE = in.readString();
        this.DOB = in.readString();
        this.MOBILE = in.readString();
        this.EMOBILE = in.readString();
        this.GENDER = in.readString();
    }
    public static final Parcelable.Creator<UserVo> CREATOR
            = new Parcelable.Creator<UserVo>() {
        public UserVo createFromParcel(Parcel in) {
            return new UserVo(in);
        }
        public UserVo[] newArray(int size) {
            return new UserVo[size];
        }
    };

}
