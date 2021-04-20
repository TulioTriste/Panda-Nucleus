package me.panda.nucleus.util;

/**
 * Project: Nucleus
 * Date: 13/04/2021 @ 18:36
 * Class: Profile
 */
public class Profile {

    private boolean staff;
    private boolean vip;

    public Profile() {
        this.staff = false;
        this.vip = false;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public boolean isvip() { return vip; }

    public void setvip(boolean vip) {
        this.vip = vip;
    }
}
