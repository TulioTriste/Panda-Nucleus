package me.panda.nucleus.util;

/**
 * Project: Nucleus
 * Date: 13/04/2021 @ 18:36
 * Class: Profile
 */
public class Profile {

    private boolean staff;
    private boolean headStaff;
    private boolean highStaff;
    private boolean vip;

    public Profile() {
        this.staff = false;
        this.headStaff = false;
        this.highStaff = false;
        this.vip = false;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public boolean isHeadStaff() {
        return headStaff;
    }

    public void setHeadStaff(boolean headStaff) {
        this.headStaff = headStaff;
    }
    public boolean isHighStaff() {
        return highStaff;
    }

    public void setHighStaff(boolean highStaff) {
        this.highStaff = highStaff;
    }
    public boolean isvip() {
        return vip;
    }

    public void setvip(boolean vip) {
        this.vip = vip;
    }
}
