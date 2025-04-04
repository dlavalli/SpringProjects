package com.lavalliere.daniel.spring;

public class Phone {
    private int id;
    private String phoneNumber;


    public Phone() { }

    public Phone(String PhoneNumber) {
        this.phoneNumber = PhoneNumber;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!this.getClass().equals(obj.getClass())) return false;

        Phone obj2 = (Phone)obj;
        if((this.id == obj2.getId()) && (this.phoneNumber.equals(obj2.getPhoneNumber())))
        {
            return true;
        }
        return false;
    }
    public int hashCode() {
        int tmp = 0;
        tmp = ( id + phoneNumber ).hashCode();
        return tmp;
    }
}
